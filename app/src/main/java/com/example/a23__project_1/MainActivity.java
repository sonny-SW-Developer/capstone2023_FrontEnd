package com.example.a23__project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;

import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private LottieAnimationView animationView;
    private Animation animFlip;
    private ConstraintLayout layout_loading;
    private LinearLayout layout_main;
    private TextView txtTitle, txtSubtitle;
    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    private SupportMapFragment supportMapFragment;
    private FrameLayout layout_slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Main);
        setContentView(R.layout.activity_main);

        txtTitle = (TextView)findViewById(R.id.txt_title);
        txtSubtitle = (TextView)findViewById(R.id.txt_subtitle);
        layout_loading = (ConstraintLayout)findViewById(R.id.layout_mainloading);
        layout_main=(LinearLayout) findViewById(R.id.layout_main);
        animationView = (LottieAnimationView) findViewById(R.id.lottie);
        //layout_slidingRootNav = (FrameLayout) findViewById(R.id.activity_slidingRootNav);
        fragmentManager = getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment)fragmentManager.findFragmentById(R.id.googleMap);


        animFlip = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.flip);

        /**********************************************************
         *  로딩 액티비티 동작
         * ********************************************************/

        // txtView 애니메이션 동작 코드
        txtSubtitle.startAnimation(animFlip);
        txtTitle.startAnimation(animFlip);

        //animationView.setAnimation("loading.json");
        // lottie 애니메이션 동작 코드
        animationView.playAnimation();
        animationView.setRepeatCount(1);

        startLoading();

        /**********************************************************
         *  main 액티비티 동작
         * ********************************************************/

        supportMapFragment.getMapAsync(this);
        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");




        setSupportActionBar(toolbar);

        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.activity_menu)
                .inject();



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

    //로딩 화면  메소드
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout_loading.setVisibility(View.GONE);
 //               layout_slidingRootNav.setVisibility(View.VISIBLE);
                layout_main.setVisibility(View.VISIBLE);

            }
        }, 4500);
    }

    // GoogleMap에서 마커 설정 메소드
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng JungboIsland = new LatLng(37.494571580758944, 126.95976562605374);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(JungboIsland);
        markerOptions.title("숭실대학교");
        markerOptions.snippet("정보 island");

        mMap.addMarker(markerOptions);

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JungboIsland, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(JungboIsland, 16));
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

}