package com.example.a23__project_1.fragmentFirst;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.data.DataPage;
import com.example.a23__project_1.data.DataPlace;
import com.example.a23__project_1.R;
import com.example.a23__project_1.fragmentFirst.recommend.MyRecommend;

import me.relex.circleindicator.CircleIndicator3;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentFirst extends Fragment implements View.OnClickListener{

    protected ViewPager2 viewPager2;
    protected RecyclerView recyclerView_place;
    protected RecyclerFragFirstPlaceAdapter placeAdapter;
    protected Button btnToggle;
//    protected LinearLayout layout_menu;
    protected ScrollView layout_menu;
    protected LinearLayout layout_backgroundmenu;
    private CircleIndicator3 indicator;
    private LottieAnimationView animationView1_1;
    private LottieAnimationView animationView1_2;
    private LottieAnimationView animationView1_3;
    private LottieAnimationView animationView1_4;
    private LottieAnimationView animationView1_5;
    private LottieAnimationView animationView1_6;
    private LottieAnimationView animationView1_7;
    private LottieAnimationView animationView1_8;
    private LottieAnimationView animationView1_9;
    private LottieAnimationView animationView1_10;
    private LottieAnimationView animationView1_11;
    private LottieAnimationView animationView1_12;
    private LottieAnimationView animationView_recommend;
    private int Anim_Rotate_num;

    private LinearLayout layout_btn_moreInfo;
    private LinearLayout layout_btn_placeInfo;
    private LinearLayout layout_btn_recommend;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private CardView cardView7;
    private CardView cardView8;
    private CardView cardView9;
    private CardView cardView10;
    private CardView cardView11;
    private CardView cardView12;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //menu = new View(getActivity().getApplicationContext());
        layout_menu = view.findViewById(R.id.layout_firstmenu);
        //layout_backgroundmenu = view.findViewById(R.id.layout_backgroundmenu);

        //Fragment의 view가 inflate하기전에 컴포넌트를 호출하기 때문에
        //View 함수에 inflater를 한 후 return 해주면 findviewbyid를 사용할 수 있다.
        viewPager2 = view.findViewById(R.id.viewPager2);
        ArrayList<DataPage> list = new ArrayList<>();

        list.add(new DataPage(getResources().getDrawable(R.drawable.friends),"우리끼리 떠나는\n패키지"));
        list.add(new DataPage(getResources().getDrawable(R.drawable.beach), "여름 바캉스,\n찾아 보고 떠나요"));
        list.add(new DataPage(getResources().getDrawable(R.drawable.oldpalace), "국내 고궁\n기획전 모음"));
        list.add(new DataPage(getResources().getDrawable(R.drawable.chickensoup), "초복 대비\n맛집 리스트 확인하기"));

        viewPager2.setAdapter(new RecyclerFragFirstAdvertiseAdapter(list));
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager2);

        //카테고리별 카드뷰 애니메이션 적용
        animationView1_1 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_1);
        animationView1_2 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_2);
        animationView1_3 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_3);
        animationView1_4 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_4);
        animationView1_5 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_5);
        animationView1_6 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_6);
        animationView1_7 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_7);
        animationView1_8 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_8);
        animationView1_9 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_9);
        animationView1_10 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_10);
        animationView1_11 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_11);
        animationView1_12 = (LottieAnimationView) view.findViewById(R.id.lottie_card1_12);
        animationView_recommend = (LottieAnimationView) view.findViewById(R.id.lottie_recommend);

        //반복 횟수
        Anim_Rotate_num = 20;
        //애니메이션 재생
        animationView1_1.playAnimation();
        animationView1_1.setRepeatCount(Anim_Rotate_num);

        animationView1_2.playAnimation();
        animationView1_2.setRepeatCount(Anim_Rotate_num);

        animationView1_3.playAnimation();
        animationView1_3.setRepeatCount(Anim_Rotate_num);

        animationView1_4.playAnimation();
        animationView1_4.setRepeatCount(Anim_Rotate_num);

        animationView1_5.playAnimation();
        animationView1_5.setRepeatCount(Anim_Rotate_num);

        animationView1_6.playAnimation();
        animationView1_6.setRepeatCount(Anim_Rotate_num);

        animationView1_7.playAnimation();
        animationView1_7.setRepeatCount(Anim_Rotate_num);

        animationView1_8.playAnimation();
        animationView1_8.setRepeatCount(Anim_Rotate_num);

        animationView1_9.playAnimation();
        animationView1_9.setRepeatCount(Anim_Rotate_num);

        animationView1_10.playAnimation();
        animationView1_10.setRepeatCount(Anim_Rotate_num);

        animationView1_11.playAnimation();
        animationView1_11.setRepeatCount(Anim_Rotate_num);

        animationView1_12.playAnimation();
        animationView1_12.setRepeatCount(Anim_Rotate_num);

        animationView_recommend.playAnimation();
        animationView_recommend.setRepeatCount(Anim_Rotate_num);

        layout_btn_moreInfo = (LinearLayout) view.findViewById(R.id.layout_btn_moreInfo);
        layout_btn_moreInfo.setOnClickListener(this);

        layout_btn_placeInfo = (LinearLayout) view.findViewById(R.id.layout_btn_placeInfo);
        layout_btn_placeInfo.setOnClickListener(this);

        layout_btn_recommend = (LinearLayout) view.findViewById(R.id.layout_btn_recommend);
        layout_btn_recommend.setOnClickListener(this);

        cardView1 = (CardView) view.findViewById(R.id.cardview1);
        cardView1.setOnClickListener(this);

        cardView2 = (CardView) view.findViewById(R.id.cardview2);
        cardView2.setOnClickListener(this);

        cardView3 = (CardView) view.findViewById(R.id.cardview3);
        cardView3.setOnClickListener(this);

        cardView4 = (CardView) view.findViewById(R.id.cardview4);
        cardView4.setOnClickListener(this);

        cardView5 = (CardView) view.findViewById(R.id.cardview5);
        cardView5.setOnClickListener(this);

        cardView6 = (CardView) view.findViewById(R.id.cardview6);
        cardView6.setOnClickListener(this);

        cardView7 = (CardView) view.findViewById(R.id.cardview7);
        cardView7.setOnClickListener(this);

        cardView8 = (CardView) view.findViewById(R.id.cardview8);
        cardView8.setOnClickListener(this);

        cardView9 = (CardView) view.findViewById(R.id.cardview9);
        cardView9.setOnClickListener(this);

        cardView10 = (CardView) view.findViewById(R.id.cardview10);
        cardView10.setOnClickListener(this);

        cardView11 = (CardView) view.findViewById(R.id.cardview11);
        cardView11.setOnClickListener(this);

        cardView12 = (CardView) view.findViewById(R.id.cardview12);
        cardView12.setOnClickListener(this);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_place = view.findViewById(R.id.recycler_frag_first_place);
        ArrayList<DataPlace> list_place = new ArrayList<>();

        list_place.add(new DataPlace("중구",R.drawable.junggu));
        list_place.add(new DataPlace("종로구",R.drawable.jongro ));
        list_place.add(new DataPlace("송파구",R.drawable.songpa ));
        list_place.add(new DataPlace("강남구",R.drawable.gangnam ));
        list_place.add(new DataPlace("영등포구",R.drawable.yeouido ));
        list_place.add(new DataPlace("금천구",R.drawable.gyumcheongu ));
        recyclerView_place.setAdapter(new RecyclerFragFirstPlaceAdapter(getActivity(),list_place));
        //recyclerView_place.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_place.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        //횡 방향 스크롤시 아이템별 정지 위함
        SnapHelper snapHelper = new PagerSnapHelper();
        if (recyclerView_place.getOnFlingListener() == null)
            snapHelper.attachToRecyclerView(recyclerView_place);



        recyclerView_place.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager =
                        LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
            }
        });
    }

    private Intent intent;
    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.layout_btn_moreInfo:
                Log.d("frag","테마별 모두보기");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","more_category");
                startActivity(intent);
                break;

            case R.id.layout_btn_placeInfo:
                Log.d("frag","지역별 모두보기");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","more_place");
                startActivity(intent);
                break;

            case R.id.layout_btn_recommend:
                Log.d("frag","일정추천");
                intent = new Intent(getActivity(), MyRecommend.class);
                intent.putExtra("elements","my_recommend");
                startActivity(intent);
                break;


            case R.id.cardview1:
                Log.d("frag","cardview1");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview1");
                startActivity(intent);
                break;

            case R.id.cardview2:
                Log.d("frag","cardview2");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview2");
                startActivity(intent);
                break;

            case R.id.cardview3:
                Log.d("frag","cardview3");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview3");
                startActivity(intent);
                break;

            case R.id.cardview4:
                Log.d("frag","cardview4");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview4");
                startActivity(intent);
                break;

            case R.id.cardview5:
                Log.d("frag","cardview5");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview5");
                startActivity(intent);
                break;

            case R.id.cardview6:
                Log.d("frag","cardview6");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview6");
                startActivity(intent);
                break;

            case R.id.cardview7:
                Log.d("frag","cardview7");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview7");
                startActivity(intent);
                break;

            case R.id.cardview8:
                Log.d("frag","cardview8");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview8");
                startActivity(intent);
                break;

            case R.id.cardview9:
                Log.d("frag","cardview9");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview9");
                startActivity(intent);
                break;

            case R.id.cardview10:
                Log.d("frag","cardview10");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview10");
                startActivity(intent);
                break;

            case R.id.cardview11:
                Log.d("frag","cardview11");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview11");
                startActivity(intent);
                break;

            case R.id.cardview12:
                Log.d("frag","cardview12");
                intent = new Intent(getActivity(), FragFirstInfoActivity.class);
                intent.putExtra("elements","cardview12");
                startActivity(intent);
                break;

        }


    }
}