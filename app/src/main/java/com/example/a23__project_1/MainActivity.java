package com.example.a23__project_1;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.a23__project_1.fragmentFifth.FragmentFifth;
import com.example.a23__project_1.fragmentSecond.FragmentSecond;

import com.example.a23__project_1.fragmentFirst.FragmentFirst;
import com.example.a23__project_1.fragmentFourth.FragmentFourth;
import com.example.a23__project_1.fragmentThird.FragmentThird;

import com.example.a23__project_1.fragmentThirdTest.FragmentThirdTest;
import com.example.a23__project_1.response.LoginResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements FragmentThird.OnFragmentInteractionListener, FragmentSecond.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private RetrofitAPI apiService;

    private LottieAnimationView animationView;
    private Animation animFlip;
    private ConstraintLayout layout_loading;
    private LinearLayout layout_main;
    private TextView txtTitle, txtSubtitle;
    public TextView kakaoName;

    //Fragment
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentFirst fragmentFirst = new FragmentFirst();
    private FragmentSecond fragmentSecond = new FragmentSecond();
    private FragmentThird fragmentThird = new FragmentThird(); //원래 코드
    //private FragmentThirdTest fragmentThird = new FragmentThirdTest();  //테스트용 코드
    private FragmentFourth fragmentFourth = new FragmentFourth();
    private FragmentFifth fragmentFifth = new FragmentFifth();

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    BottomNavigationView bottomNavigationView;
    private SharedViewModel sharedViewModel;
    private int fromWhere = 2;
    public void setfromWhere(int index){
        this.fromWhere = index;
    }
    public int getfromWhere(){
        return fromWhere;
    }
    private String user_name = "";

    private SlidingRootNav slidingRootNav;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Main);
        setContentView(R.layout.activity_main);

        // user 정보 저장
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // API 통신
        apiService = RetrofitClient.getApiService();

        // 해시키 값 확인
        // Log.d("keyHash : ", getKeyHash());

        txtTitle = (TextView)findViewById(R.id.txt_title);
        txtSubtitle = (TextView)findViewById(R.id.txt_subtitle);
        layout_loading = (ConstraintLayout)findViewById(R.id.layout_mainloading);
        layout_main=(LinearLayout) findViewById(R.id.layout_main);
        animationView = (LottieAnimationView) findViewById(R.id.lottie);

        animFlip = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.flip);

        /**********************************************************
         *  로딩 액티비티 동작
         * ********************************************************/

        // txtView 애니메이션 동작 코드
        txtSubtitle.startAnimation(animFlip);
        txtTitle.startAnimation(animFlip);

        // lottie 애니메이션 동작 코드
        animationView.playAnimation();
        animationView.setRepeatCount(1);

        startLoading();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        /**********************************************************
         *  toolbar
         * ********************************************************/

        //툴바 옆, setting 관련 코드
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.a23__project_1.leftMenuBar.SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.activity_main_slidemenu)
                .inject();

        // 사용자 이름 받기
        kakaoName = findViewById(R.id.personal_name);
        user_name = sharedPreferences.getString("name", "null");
        Log.d(TAG, "onCreate에서 user_name : " + user_name);

        // 이름 여부에 따라 이름 배치
        kakaoName.setText(!user_name.equals("null") ? user_name : "name");


        //장소 추천받기
        findViewById(R.id.btn_recommend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(fragmentFirst,R.id.firstItem);
            }
        });


        //혼잡도 리스트 검색
        findViewById(R.id.btn_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(fragmentSecond,R.id.secondItem);
            }
        });

        //지도에서 보기
        findViewById(R.id.btn_mymap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(fragmentThird,R.id.thirdItem);
            }
        });

        //일정 기록 확인
        findViewById(R.id.btn_myplan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(fragmentFourth,R.id.fourthItem);
            }
        });

        //사용자 정보
        findViewById(R.id.btn_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(fragmentFifth,R.id.fifthItem);
            }
        });

        /**********************************************************
         *  main 액티비티 동작 - 프래그먼트
         * ********************************************************/

        //화면 추가할 프래그먼트 추가
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout_main, fragmentThird).commitAllowingStateLoss();

        //메뉴클릭 리스너 등록
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.thirdItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener(2));


        //animation listener
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        String userName = sharedPreferences.getString("name", "null");
        // kakaoName.setText(!user_name.equals("null") ? user_name : "name");
        if(!userName.equals("null")) {
            kakaoName.setText(userName);
        } else kakaoName.setText("name");
    }

    @Override
    public void onFragmentInteraction(int itemId) {
        bottomNavigationView.setSelectedItemId(itemId);
    }
    //로딩 화면  메소드
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout_loading.setVisibility(View.GONE);
                layout_main.setVisibility(View.VISIBLE);

            }
        }, 4500);
    }


    //뒤로가기 버튼 클릭 관련 코드
    private final long finishtimeed = 1000;
    private long presstime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - presstime;

        if (0 <= intervalTime && finishtimeed >= intervalTime) {
            finish();
        } else {
            presstime = tempTime;
            Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        int from; // 0:default, 1:fragment, 2:activity
        MainActivity activity;
        public ItemSelectedListener(int i){
            this.from = i;
        }
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.firstItem:
                    transaction.replace(R.id.frameLayout_main, fragmentFirst).commitAllowingStateLoss();
                    break;

                case R.id.secondItem:
                    transaction.replace(R.id.frameLayout_main, fragmentSecond).commitAllowingStateLoss();
                    break;

                case R.id.thirdItem:
                    Bundle bundle = new Bundle();
                    bundle.putInt("placeName", 1);
                    if(fromWhere == 1){
                        sharedViewModel.setFromWhere(1); // MainActivity에서 호출됐음을 나타냄
                        fromWhere = 2;
                    }else if(fromWhere == 2){
                        sharedViewModel.setFromWhere(2); // MainActivity에서 호출됐음을 나타냄
                    }else if(fromWhere == 3){
                        sharedViewModel.setFromWhere(3);
                        fromWhere = 2;
                    }
                    transaction.replace(R.id.frameLayout_main, fragmentThird).commitAllowingStateLoss();
                    break;

                case R.id.fourthItem:
                    transaction.replace(R.id.frameLayout_main, fragmentFourth).commitAllowingStateLoss();
                    break;

                case R.id.fifthItem:
                    transaction.replace(R.id.frameLayout_main, fragmentFifth).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }

    //카카오 키 해시 값 얻기 (key Hash)
//    private String getKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                Log.e("Hash key", something);
//                return something;
//            }
//        } catch (Exception e) {
//            Log.e("name not found", e.toString());
//        }
//        return "";
//    }

    // leftmenu fragment 변경 함수
    private void switchFragment(Fragment fragment,@IdRes int bottomNavItemId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_main, fragment)
                .commit();
        slidingRootNav.closeMenu(true);
        bottomNavigationView.setSelectedItemId(bottomNavItemId);
    }
}