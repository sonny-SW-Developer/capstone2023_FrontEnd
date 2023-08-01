package com.example.a23__project_1.fragmentFirst.recommend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.fragmentFirst.FragFirstInfoActivity;
import com.example.a23__project_1.fragmentFirst.RecyclerFragFirstThemeAdapter;
import com.example.a23__project_1.leftMenuBar.SettingActivity;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyRecommend extends AppCompatActivity {

    private static final String TAG = "MyRecommend";
    private Intent intent;

    protected ScrollView layout_menu;
    private LottieAnimationView animationView1_1;
    private LottieAnimationView animationView1_2;
    private LottieAnimationView animationView1_3;
    private LottieAnimationView animationView1_4;
    private LottieAnimationView animationView1_5;
    private LottieAnimationView animationView1_6;
    private LottieAnimationView animationView1_7;

    private LottieAnimationView animationView_recommend;
    private int Anim_Rotate_num;

    private CardView cardView_recommend1;
    private CardView cardView_recommend2;
    private CardView cardView_recommend3;
    private CardView cardView_recommend4;
    private CardView cardView_recommend5;
    private CardView cardView_recommend6;
    private CardView cardView_recommend7;


    private String messege;
    private ImageButton backbtn;
    private Adapter adapter;
    private ArrayList<DataMoreInfo> list_place;
    private RetrofitAPI apiService;
    private Call<PositionResponse> allPlaceCall;
    private List<Long> positionIdList;
    private com.example.a23__project_1.fragmentSecond.categoryAdapter categoryAdapter;
    private com.example.a23__project_1.fragmentSecond.PlaceListAdapter placeListAdapter;
    private List<PositionResponse.Result> resultList = new ArrayList<>();

    /**
     * 좋아요 기능 구현을 위한 전역변수 선언
     **/
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "";
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트
    private Call<LikeResponse> likeCall;
    RecyclerFragFirstThemeAdapter themeAdapter;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_first_my_recommend);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "null");

        intent = getIntent();
        if (intent.hasExtra("elements")) {
            messege = intent.getStringExtra("elements");
        }
        switch (messege) {
            case "more_category":
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

        animationView_recommend = (LottieAnimationView) findViewById(R.id.lottie_recommend);
        animationView_recommend.playAnimation();
        //반복 횟수
        Anim_Rotate_num = 20;
        animationView_recommend.setRepeatCount(Anim_Rotate_num);

        cardView_recommend1 = (CardView) findViewById(R.id.cardview_recommend1);
        cardView_recommend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                Log.d("여기여기여기", location[0] + ", " + location[1]);
                intent.putExtra("elements", "관광");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend2 = (CardView) findViewById(R.id.cardview_recommend2);
        cardView_recommend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "카페");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend3 = (CardView) findViewById(R.id.cardview_recommend3);
        cardView_recommend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "식사");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend4 = (CardView) findViewById(R.id.cardview_recommend4);
        cardView_recommend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "쇼핑");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend5 = (CardView) findViewById(R.id.cardview_recommend5);
        cardView_recommend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "공원");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend6 = (CardView) findViewById(R.id.cardview_recommend6);
        cardView_recommend6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "거리");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend7 = (CardView) findViewById(R.id.cardview_recommend7);
        cardView_recommend7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] location = getLocation();
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements", "액티비티");
                intent.putExtra("latitude", location[0]);
                intent.putExtra("longitude", location[1]);
                intent.putExtra("member_id",email);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });


        //툴바 옆, setting 관련 코드
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_first_recommend_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

    }

    private static final int REQUEST_LOCATION = 1;

    private double[] getLocation() {
        double[] gps = new double[2];

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 확인을 해서 권한이 없을 경우 권한을 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return gps;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            gps[0] = location.getLatitude();
            gps[1] = location.getLongitude();
        }
        Log.d("위치위치", gps[0] + ", " + gps[1]);
        return gps;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("여기까지","ㅇㅇ");
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용되면, 위치 정보를 얻을 수 있습니다.
                    // 이 부분에 위치 정보를 얻는 코드를 넣어야 합니다.
                    double[] location = getLocation();
                    // ...
                } else {
                    // 권한이 거부되었을 경우, 필요한 작업을 수행합니다.
                    // 예를 들어, 사용자에게 이 권한이 왜 필요한지 설명하거나 기능을 비활성화할 수 있습니다.
                }
                return;
        }

    }
}