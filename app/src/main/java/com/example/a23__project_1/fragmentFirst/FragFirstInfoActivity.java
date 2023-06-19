package com.example.a23__project_1.fragmentFirst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.R;
import com.example.a23__project_1.leftMenuBar.SettingActivity;
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


public class FragFirstInfoActivity extends AppCompatActivity {
    private static final String TAG = "FragmentFirstInfo";
    private Intent intent;
    private String messege;
    private RecyclerView recyclerView;
//    private final Context mainContext;
    private ImageButton backbtn;
    private Adapter adapter;
    private ArrayList<DataMoreInfo> list_place;
    private RetrofitAPI apiService;
    private Call<PositionResponse> allPlaceCall;
    private List<Long> positionIdList;
    private com.example.a23__project_1.fragmentSecond.categoryAdapter categoryAdapter;
    private com.example.a23__project_1.fragmentSecond.PlaceListAdapter placeListAdapter;
    private List<PositionResponse.Result> resultList;

    /** 좋아요 기능 구현을 위한 전역변수 선언 **/
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "";
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트
    List<PositionResponse.Result> positionList;
    private Call<LikeResponse> likeCall;
    RecyclerFragFirstThemeAdapter themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_frag_info);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "null");


        //리사이클러 뷰
        recyclerView = (RecyclerView)findViewById(R.id.recycler_activity_first_fragment_menu);
        positionIdList = new ArrayList<>();
        intent = getIntent();
        if(intent.hasExtra("elements")){
            messege = intent.getStringExtra("elements");
        }

        //리사이클러 뷰
        list_place = new ArrayList<>();
        resultList = new ArrayList<>();
        positionList = new ArrayList<>();
        switch (messege){
            case "more_category":
                getPositionList("카테고리더보기",list_place);
                break;
            case "more_place":
                getPositionList("카테고리더보기",list_place);
                break;
            case "cardview1":
                getPositionList("쇼핑몰",list_place);
                break;
            case "cardview2":
                getPositionList("지하철·기차역",list_place);
                break;
            case "cardview3":
                getPositionList("음식점",list_place);
                break;
            case "cardview4":
                getPositionList("공원",list_place);
                break;
            case "cardview5":
                getPositionList("공항",list_place);
                break;
            case "cardview6":
                getPositionList("카페",list_place);
                break;
            case "cardview7":
                getPositionList("관광지",list_place);
                break;
            case "cardview8":
                getPositionList("골목 및 거리",list_place);
                break;
            case "cardview9":
                getPositionList("상업지구",list_place);
                break;
            case "cardview10":
                getPositionList("스파",list_place);
                break;
            case "cardview11":
                getPositionList("놀이공원",list_place);
                break;
            case "cardview12":
                getPositionList("마트",list_place);
                break;
            case "0":
                getRegionList(list_place,0);
                break;
            case "1":
                getRegionList(list_place,1);
                break;
            case "2":
                getRegionList(list_place,2);
                break;
            case "3":
                getRegionList(list_place,3);
                break;
            case "4":
                getRegionList(list_place,4);
                break;
            case "5":
                getRegionList(list_place,5);
                break;
            default:
                try {

                    Integer.parseInt(messege);
                    Log.d("FragFirstInfo",messege);

                }catch(NumberFormatException e){
                    Log.d("FragFirstInfo","error");
                }

        }

        //횡 방향 스크롤시 아이템별 정지 위함
        SnapHelper snapHelper = new PagerSnapHelper();
        if (recyclerView.getOnFlingListener() == null)
            snapHelper.attachToRecyclerView(recyclerView);

        //툴바 옆, setting 관련 코드
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragFirstInfoActivity.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_first_info_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

    }
    /** onCreate() 종료 **/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<DataMoreInfo> getPositionList(String theme_id, ArrayList<DataMoreInfo> list_place) {

        apiService = RetrofitClient.getApiService();
        // 로그인 여부 판단
        if(email.equals("null")) {
            allPlaceCall = apiService.getAllPosition();
        }
        else {
            allPlaceCall = apiService.getLoginAllPosition(email);
        }
        allPlaceCall.enqueue(new Callback<PositionResponse>() {
            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "성공");
                    positionList = response.body().getResult();


                    for (PositionResponse.Result result : positionList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    settingPage(positionList, theme_id);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            themeAdapter = new RecyclerFragFirstThemeAdapter(FragFirstInfoActivity.this, list_place);
                            /** 찜 버튼 구현 **/
                            themeAdapter.setOnLikeClickListener(new RecyclerFragFirstThemeAdapter.likeClickListener() {
                                @Override
                                public void likeButtonClick(int pos) {
                                    postLike(pos);
                                }
                            });
                            recyclerView.setAdapter(themeAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(FragFirstInfoActivity.this,RecyclerView.VERTICAL,false));
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

    public void getRegionList(ArrayList<DataMoreInfo> list_place,int position){
        apiService = RetrofitClient.getApiService();
        // 로그인 여부 판단
        if(email.equals("null")) {
            allPlaceCall = apiService.getAllPosition();
        }
        else {
            allPlaceCall = apiService.getLoginAllPosition(email);
        }
        allPlaceCall.enqueue(new Callback<PositionResponse>() {
            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "성공");
                    List<PositionResponse.Result> positionList = response.body().getResult();

                    for (PositionResponse.Result result : positionList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    getRegionInit(positionList, position);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            themeAdapter = new RecyclerFragFirstThemeAdapter(FragFirstInfoActivity.this, list_place);
                            /** 찜 버튼 구현 **/
                            themeAdapter.setOnLikeClickListener(new RecyclerFragFirstThemeAdapter.likeClickListener() {
                                @Override
                                public void likeButtonClick(int pos) {
                                    postLike(pos);
                                }
                            });
                            recyclerView.setAdapter(themeAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(FragFirstInfoActivity.this,RecyclerView.VERTICAL,false));
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
    }

    /** 찜 누르기 메서드 구현 **/
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
                            positionList.get(pos).setLikeYn(0);
                        }
                    }
                    else {
                        //찜 리스트 추가하는 경우
                        Toast.makeText(getApplicationContext(), "찜 리스트에 정상적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        list_place.get(pos).setBoolean_cart(true);
                        positionList.get(pos).setLikeYn(1);
                    }
                    /** 변경 감지 **/
                    themeAdapter.notifyItemChanged(pos);
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

    private void getRegionInit(List<PositionResponse.Result> resultList,int position){
        int index;
        String name;
        String themaName;
        long placeid;
        Integer popular;

        int likeYn;
        boolean CheckLike=false;

        long placeid_before=-1;

        PositionResponse.Result resultListIndex;

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);
            name = resultListIndex.getName();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();

            likeYn = resultListIndex.getLikeYn();
            if (likeYn == 1)
                CheckLike = true;
            else
                CheckLike = false;

            switch(position){
                case 0: // 중구
                     if(name.equals("국립중앙박물관·용산가족공원")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.yeouido ,CheckLike,popular,placeid));
                    }else if(name.equals("남산공원")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.namsan_park, CheckLike, popular, placeid));
                    }else if(name.equals("명동 관광특구")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.myongdong_tuk, CheckLike, popular, placeid));
                    }else if(name.equals("서울역")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.stn_seoul, CheckLike, popular, placeid));
                    }else if(name.equals("신세계백화점본점신관")) {
                    list_place.add(new DataMoreInfo(name, "아이템", R.drawable.shin_bon, CheckLike, popular, placeid));
                    }
                    break;
                case 1: // 종로구
                    if(name.equals("경복궁·서촌마을")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.gbkkung ,CheckLike,popular,placeid));
                    }else if(name.equals("광화문·덕수궁")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.duksu, CheckLike, popular, placeid));
                    }else if(name.equals("북촌한옥마을")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.bukchon, CheckLike, popular, placeid));
                    }else if(name.equals("종로·청계 관광특구")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.jongro_chunggye, CheckLike, popular, placeid));
                    }else if(name.equals("창덕궁·종묘")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.changduckgung, CheckLike, popular, placeid));
                    }
                    break;
                case 2: // 송파구
                    if(name.equals("가든파이브툴")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.gardenfive ,CheckLike,popular,placeid));
                    }else if(name.equals("롯데월드잠실점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.lotte_world, CheckLike, popular, placeid));
                    }else if(name.equals("잠실 관광특구")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.jamsil_tour_special_gu, CheckLike, popular, placeid));
                    }else if(name.equals("잠실종합운동장")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.jamsil_total_stadium, CheckLike, popular, placeid));
                    }else if(name.equals("잠실한강공원")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.jamsil_hangang_park, CheckLike, popular, placeid));
                    }
                    break;
                case 3: // 강남구
                    if(name.equals("가로수길")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.garosu, CheckLike, popular, placeid));
                    }else if(name.equals("강남 MICE 관광특구")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.gn_mice ,CheckLike,popular,placeid));
                    }else if(name.equals("강남역")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.stn_gn, CheckLike, popular, placeid));
                    }else if(name.equals("롯데백화점강남점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.lotte_gn, CheckLike, popular, placeid));
                    }else if(name.equals("신세계백화점강남점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.shin_gn, CheckLike, popular, placeid));
                    }
                    break;
                case 4: // 영등포구
                    if(name.equals("IFC몰")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.ifc ,CheckLike,popular,placeid));
                    }else if(name.equals("롯데백화점영등포점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.lotte_ydp, CheckLike, popular, placeid));
                    }else if(name.equals("신세계백화점타임스퀘어점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.shin_timesquare, CheckLike, popular, placeid));
                    }else if(name.equals("여의도공원")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.yeouido_park, CheckLike, popular, placeid));
                    }else if(name.equals("영등포 타임스퀘어")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.time_square, CheckLike, popular, placeid));
                    }
                    break;
                case 5: // 금천구
                    if(name.equals("W몰")){
                        list_place.add(new DataMoreInfo(name,"아이템",R.drawable.wmall ,CheckLike,popular,placeid));
                    }else if(name.equals("가산디지털단지역")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.stn_gadi, CheckLike, popular, placeid));
                    }else if(name.equals("마리오아울렛1관")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.mario1, CheckLike, popular, placeid));
                    }else if(name.equals("마리오아울렛3관")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.mario3, CheckLike, popular, placeid));
                    }else if(name.equals("현대시티아울렛가산점")) {
                        list_place.add(new DataMoreInfo(name, "아이템", R.drawable.hyundae_gasan, CheckLike, popular, placeid));
                    }
                    break;

                }
            }
            placeid_before=placeid;

        }

    }
    public ArrayList<DataMoreInfo> settingPage(List<PositionResponse.Result> resultList, String theme_id){
        int index;
        String name;
        String themaName;
        long placeid;
        Integer popular;
        int like_rate;
        boolean checkLike=false;

        PositionResponse.Result resultListIndex;
        long placeid_before = -1;

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);
            name = resultListIndex.getName();
            themaName = resultListIndex.getThema();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();
            like_rate = resultListIndex.getLikeYn();
            if (like_rate == 1)
                checkLike = true;
            else
                checkLike = false;

            if(theme_id.equals("카테고리더보기")){
                if(placeid!=placeid_before){
                    list_place.add(new DataMoreInfo(name,"아이템",R.drawable.yeouido ,checkLike,popular,placeid));
                }

            }else if(theme_id.equals(themaName)){
                list_place.add(new DataMoreInfo(name,"아이템",R.drawable.yeouido ,checkLike,popular,placeid));

            }else if(theme_id.equals(themaName)) {
                list_place.add(new DataMoreInfo(name, "아이템", R.drawable.yeouido, false, popular, placeid));
            }else if((theme_id.equals("지하철·기차역")&&(themaName.equals("지하철")||theme_id.equals("기차역")))) {
                list_place.add(new DataMoreInfo(name, "아이템", R.drawable.yeouido, false, popular, placeid));
            }
            placeid_before = placeid;

        }

        return list_place;
    }


}