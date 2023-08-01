package com.example.a23__project_1.fragmentFirst.recommend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataPlaceWithS3;
import com.example.a23__project_1.fragmentFifth.InquiryInfoActivity;
import com.example.a23__project_1.fragmentFifth.InquiryListActivity;
import com.example.a23__project_1.fragmentFifth.InquiryListAdapter;
import com.example.a23__project_1.fragmentFirst.RecyclerFragFirstPlacesSpecific;
import com.example.a23__project_1.fragmentFirst.RecyclerFragFirstPlacesSpecificAdapter;
import com.example.a23__project_1.request.InquiryRequest;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.request.RecommendRequest;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.response.InquiryResponse;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.response.RecommendResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyRecommendResult extends AppCompatActivity {

    private static final String TAG = "MyRecommendResult";
    private Intent intent;
    private String messege;
    private AlertDialog alertDialog;

    private TextView testText;
    private LinearLayout layout_recommend_result_main;

    private RecyclerView recyclerView;
    RecyclerMyRecommendAdapter myRecommendAdapter;

    private Adapter adapter;
    private ArrayList<DataPlaceWithS3> list_place;
    private RetrofitAPI apiService;
    private Call<PositionResponse> allPlaceCall;
    private List<Long> positionIdList;
    private com.example.a23__project_1.fragmentSecond.categoryAdapter categoryAdapter;
    private com.example.a23__project_1.fragmentSecond.PlaceListAdapter placeListAdapter;
    private List<RecommendResponse.Result> resultList = new ArrayList<>();

    /** 좋아요 기능 구현을 위한 전역변수 선언 **/
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "";
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트
    private Call<LikeResponse> likeCall;
    RecyclerMyRecommendAdapter recommendAdapter;
    private Double cur_lat;
    private Double cur_lon;
    private String mem_id;
    FusedLocationProviderClient fusedLocationProviderClient;

    private Call<RecommendResponse> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_first_my_recommend_result);

        // apiService 초기화
        apiService = RetrofitClient.getApiService();
        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        intent = getIntent();
        if (intent.hasExtra("elements")) {
            messege = intent.getStringExtra("elements");
            cur_lat = intent.getDoubleExtra("latitude",0);
            cur_lon = intent.getDoubleExtra("longitude",0);
            mem_id = intent.getStringExtra("member_id");
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false); // if you want the user to be forced to wait

        alertDialog = builder.create();

        alertDialog.show(); // show loading dialog

        layout_recommend_result_main = (LinearLayout) findViewById(R.id.layout_recommend_result_main);
        layout_recommend_result_main.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_activity_first_fragment_menu);
        positionIdList = new ArrayList<>();
        list_place = new ArrayList<>();
        getRecommend(list_place);

        SnapHelper snapHelper = new PagerSnapHelper();
        if (recyclerView.getOnFlingListener() == null)
            snapHelper.attachToRecyclerView(recyclerView);




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
                    cur_lat = intent.getDoubleExtra("latitude",0);
                    cur_lon = intent.getDoubleExtra("longitude",0);
                    mem_id = intent.getStringExtra("member_id");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return messege;
        }

        @Override
        protected void onPostExecute(String result) {
            layout_recommend_result_main = (LinearLayout) findViewById(R.id.layout_recommend_result_main);
            alertDialog.dismiss(); // hide loading dialog

            layout_recommend_result_main.setVisibility(View.VISIBLE);
            recyclerView = (RecyclerView)findViewById(R.id.recycler_activity_first_fragment_menu);
            list_place = new ArrayList<>();
            getRecommend(list_place);

            SnapHelper snapHelper = new PagerSnapHelper();
            if (recyclerView.getOnFlingListener() == null)
                snapHelper.attachToRecyclerView(recyclerView);
        }
    }

//    public ArrayList<DataPlaceWithS3> getPositionList(String theme_id, ArrayList<DataPlaceWithS3> list_place) {
//
//        apiService = RetrofitClient.getApiService();
//        allPlaceCall = apiService.getAllPosition();
//        allPlaceCall.enqueue(new Callback<RecommendResponse>() {
//            @Override
//            public void onResponse(Call<RecommendResponse> call, Response<RecommendResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "성공");
//                    List<RecommendResponse.Result> positionList = response.body().getResult();
//
//
//                    for (RecommendResponse.Result result : positionList) {
//                        positionIdList.add(result.getPlaceId());
//                    }
//                    settingPage(positionList);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            recommendAdapter = new RecyclerMyRecommendAdapter(MyRecommendResult.this, list_place);
//                            /** 찜 버튼 구현 **/
//                            recommendAdapter.setOnLikeClickListener(new RecyclerMyRecommendAdapter.likeClickListener() {
//                                @Override
//                                public void likeButtonClick(int pos) {
//                                    postLike(pos);
//                                }
//                            });
//                            recyclerView.setAdapter(recommendAdapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MyRecommendResult.this, RecyclerView.VERTICAL, false));
//                        }
//                    });
//                } else {
//                    Log.d(TAG, "에러발생 .." + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecommendResponse> call, Throwable t) {
//                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
//            }
//
//        });
//        return list_place;
//    }

    private void postLike(int pos) {
        apiService = RetrofitClient.getApiService();
        if (email.equals("null")) {
            Toast.makeText(getApplicationContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
            return;
        }
        String accessToken = sharedPreferences.getString("accessToken", "null");
        LikeRequest.Member member = new LikeRequest.Member(email);
        LikeRequest.Place place = new LikeRequest.Place(positionIdList.get(pos));
        LikeRequest request = new LikeRequest(member, place);
        likeCall = apiService.doLike(accessToken, request);
        likeCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                /** 요청에 성공했을 때 **/
                if (response.body().getCode() == 200 && response.body().getMessage().contains("성공")) {
                    Object result = response.body().getResult();
                    // 문자열로 온 경우
                    if (result instanceof String) {
                        String resultString = (String) result;
                        // 찜리스트 취소하는 경우
                        if (resultString.equals("delete")) {
                            Toast.makeText(getApplicationContext(), "찜 리스트에 정상적으로 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            list_place.get(pos).setBoolean_cart(false);
                        }
                    } else {
                        //찜 리스트 추가하는 경우
                        Toast.makeText(getApplicationContext(), "찜 리스트에 정상적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        list_place.get(pos).setBoolean_cart(true);
                    }
                    /** 변경 감지 **/
                    // recommendAdapter.notifyItemChanged(pos);
                    recommendAdapter.notifyDataSetChanged();

                } else
                    Log.d(TAG, "찜리스트 연동 오류 1.." + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.d(TAG, "찜리스트 연동 오류 2.." + t.getMessage());
            }
        });
    }

    /** 장소추천 API **/
    private void getRecommend(ArrayList<DataPlaceWithS3> list_place) {

        // apiService 초기화
        apiService = RetrofitClient.getApiService();
        RecommendRequest request = new RecommendRequest(mem_id,cur_lat, cur_lon,messege);
        String accesstoken = sharedPreferences.getString("accessToken", "null");
        call = apiService.userRecommend(accesstoken, request);
        call.enqueue(new Callback<RecommendResponse>() {
            @Override
            public void onResponse(Call<RecommendResponse> call, Response<RecommendResponse> response) {
                if(response.isSuccessful() && response.body().getMessage().contains("성공")) {
                    List<RecommendResponse.Result> positionList = response.body().getResult();

                    resultList = response.body().getResult();
                    for (RecommendResponse.Result result : resultList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    settingPage(resultList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myRecommendAdapter = new RecyclerMyRecommendAdapter(MyRecommendResult.this, list_place);
                            /** 찜 버튼 구현 **/
                            myRecommendAdapter.setOnLikeClickListener(new RecyclerMyRecommendAdapter.likeClickListener() {
                                @Override
                                public void likeButtonClick(int pos) {
                                    postLike(pos);
                                }
                            });
                            recyclerView.setAdapter( myRecommendAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MyRecommendResult.this,RecyclerView.VERTICAL,false));
                        }
                    });
                    alertDialog.dismiss();
                }
                else
                    Log.d(TAG, "RecommendAPI 연동 오류" + response.errorBody().toString());

            }

            @Override
            public void onFailure(Call<RecommendResponse> call, Throwable t) {
                Log.d(TAG, "RecommendAPI 연동 오류" + t.getMessage());
            }
        });
    }
    public ArrayList<DataPlaceWithS3> settingPage(List<RecommendResponse.Result> resultList){
        int index;
        String name;
        long placeid;
        Integer popular;
        String imageUrl;
        RecommendResponse.Result resultListIndex;
        long placeid_before = -1;

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);
            name = resultListIndex.getName();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();
            imageUrl = resultListIndex.getImageURL();

            list_place.add(new DataPlaceWithS3(name, "아이템", imageUrl, false, popular, placeid));

        }
        return list_place;
    }
}
