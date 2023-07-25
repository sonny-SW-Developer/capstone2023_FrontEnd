package com.example.a23__project_1.fragmentFirst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.leftMenuBar.SettingActivity;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
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

    /** 좋아요 기능 구현을 위한 전역변수 선언 **/
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "";
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트
    private Call<LikeResponse> likeCall;
    RecyclerFragFirstThemeAdapter themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);

        intent = getIntent();
        if(intent.hasExtra("elements")){
            messege = intent.getStringExtra("elements");
        }
        switch (messege){
            case "more_category":
//                getPositionList("카테고리더보기",list_place);
                break;
            default:
                try {

                    Integer.parseInt(messege);
                    Log.d(TAG,messege);

                }catch(NumberFormatException e){
                    Log.d(TAG,"error");
                }

        }

        animationView_recommend = (LottieAnimationView)findViewById(R.id.lottie_recommend);
        animationView_recommend.playAnimation();
        //반복 횟수
        Anim_Rotate_num = 20;
        animationView_recommend.setRepeatCount(Anim_Rotate_num);

        cardView_recommend1 = (CardView)findViewById(R.id.cardview_recommend1);
        cardView_recommend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","tour");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend2 = (CardView)findViewById(R.id.cardview_recommend2);
        cardView_recommend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","cafe");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend3 = (CardView)findViewById(R.id.cardview_recommend3);
        cardView_recommend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","cook");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend4 = (CardView)findViewById(R.id.cardview_recommend4);
        cardView_recommend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","shopping");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend5 = (CardView)findViewById(R.id.cardview_recommend5);
        cardView_recommend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","park");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend6 = (CardView)findViewById(R.id.cardview_recommend6);
        cardView_recommend6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","street");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        cardView_recommend7 = (CardView)findViewById(R.id.cardview_recommend7);
        cardView_recommend7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecommend.this, MyRecommendResult.class);
                intent.putExtra("elements","activity");
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_recommend_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


    }
}