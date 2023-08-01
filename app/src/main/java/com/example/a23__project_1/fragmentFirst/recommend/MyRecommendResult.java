package com.example.a23__project_1.fragmentFirst.recommend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataPlaceWithS3;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecommendResult extends AppCompatActivity {

    private static final String TAG = "MyRecommendResult";
    private Intent intent;
    private String messege;
    private AlertDialog alertDialog;

    private TextView testText;
    private LinearLayout layout_recommend_result_main;

    private RecyclerView recyclerView;
    //    private final Context mainContext;

    private Adapter adapter;
    private ArrayList<DataPlaceWithS3> list_place;
    private RetrofitAPI apiService;
    private Call<PositionResponse> allPlaceCall;
    private List<Long> positionIdList;
    private com.example.a23__project_1.fragmentSecond.categoryAdapter categoryAdapter;
    private com.example.a23__project_1.fragmentSecond.PlaceListAdapter placeListAdapter;
    private List<PositionResponse.Result> resultList = new ArrayList<>();

    /** 좋아요 기능 구현을 위한 전역변수 선언 **/
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "";
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트
    private Call<LikeResponse> likeCall;
    RecyclerMyRecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_first_my_recommend_result);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false); // if you want the user to be forced to wait

        alertDialog = builder.create();

        new MyAsyncTask().execute();

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            alertDialog.show(); // show loading dialog
        }

        @Override
        protected String doInBackground(Void... params) {
            // simulated delay for server communication
            String message = null;
            try {
                Thread.sleep(2000);
                intent = getIntent();
                if (intent.hasExtra("elements")) {
                    messege = intent.getStringExtra("elements");
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return messege;
        }

        @Override
        protected void onPostExecute(String result){
            layout_recommend_result_main = (LinearLayout) findViewById(R.id.layout_recommend_result_main);
            alertDialog.dismiss(); // hide loading dialog
            layout_recommend_result_main.setVisibility(View.VISIBLE);
            if (result != null) {
                switch (messege) {
                    case "tour":
                        Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                        break;
                   case "cafe":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "cook":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "shopping":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "park":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "street":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "activity":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   default:
                       try {

                           Integer.parseInt(messege);
                           Log.d(TAG, messege);

                       } catch (NumberFormatException e) {
                           Log.d(TAG, "error");
                       }
                }
            }
        }
    }

    public ArrayList<DataPlaceWithS3> getPositionList(String theme_id, ArrayList<DataPlaceWithS3> list_place) {

        apiService = RetrofitClient.getApiService();
        allPlaceCall = apiService.getAllPosition();
        allPlaceCall.enqueue(new Callback<PositionResponse>() {


            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "성공");
                    List<PositionResponse.Result> positionList = response.body().getResult();


                    for (PositionResponse.Result result : positionList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    settingPage(positionList, theme_id);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recommendAdapter = new RecyclerMyRecommendAdapter(MyRecommendResult.this, list_place);
                            /** 찜 버튼 구현 **/
                            recommendAdapter.setOnLikeClickListener(new RecyclerMyRecommendAdapter.likeClickListener() {
                                @Override
                                public void likeButtonClick(int pos) {
                                    postLike(pos);
                                }
                            });
                            recyclerView.setAdapter(recommendAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MyRecommendResult.this, RecyclerView.VERTICAL,false));
                        }
                    });
                }
                else {
                    Log.d(TAG, "에러발생 .." + response.message());
                }
            }

            @Override
            public void onFailure(Call<PositionResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
            }

        });
        return list_place;
    }

    private void postLike(int pos) {
        apiService = RetrofitClient.getApiService();
        if(email.equals("null")) {
            Toast.makeText(getApplicationContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
            return;
        }

        LikeRequest.Member member = new LikeRequest.Member(email);
        LikeRequest.Place place = new LikeRequest.Place(positionIdList.get(pos));
        LikeRequest request = new LikeRequest(member, place);
        likeCall = apiService.doLike(request);
        likeCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                /** 요청에 성공했을 때 **/
                if(response.body().getCode() == 200 && response.body().getMessage().contains("성공")) {
                    Object result = response.body().getResult();
                    // 문자열로 온 경우
                    if (result instanceof String) {
                        String resultString = (String) result;
                        // 찜리스트 취소하는 경우
                        if(resultString.equals("delete")) {
                            Toast.makeText(getApplicationContext(), "찜 리스트에 정상적으로 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            list_place.get(pos).setBoolean_cart(false);
                        }
                    }
                    else {
                        //찜 리스트 추가하는 경우
                        Toast.makeText(getApplicationContext(), "찜 리스트에 정상적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        list_place.get(pos).setBoolean_cart(true);
                    }
                    /** 변경 감지 **/
                    recommendAdapter.notifyItemChanged(pos);
                }
                else
                    Log.d(TAG, "찜리스트 연동 오류 1.." + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.d(TAG, "찜리스트 연동 오류 2.." + t.getMessage());
            }
        });
    }
    public ArrayList<DataPlaceWithS3> settingPage(List<PositionResponse.Result> resultList, String theme_id){
        int index;
        String name;
        String themaName;
        long placeid;
        Integer popular;
        PositionResponse.Result resultListIndex;
        long placeid_before = -1;
        String imageURL;

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);
            name = resultListIndex.getName();
            themaName = resultListIndex.getThema();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();
            imageURL = resultListIndex.getImageURL();

            if(theme_id.equals("카테고리더보기")){
                if(placeid!=placeid_before){
                    //list_place.add(new DataPlaceWithS3(name,"아이템",R.drawable.yeouido ,false,popular,placeid));
                }
            }else if(theme_id.equals(themaName)){
                //list_place.add(new DataPlaceWithS3(name,"아이템",R.drawable.yeouido ,false,popular,placeid));
            }
            placeid_before = placeid;

        }

        return list_place;
    }
}
