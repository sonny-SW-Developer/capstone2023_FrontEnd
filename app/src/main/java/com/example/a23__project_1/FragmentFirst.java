package com.example.a23__project_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
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
    private int Anim_Rotate_num;

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

        viewPager2.setAdapter(new ViewPagerAdapter(list));
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

//        //장소별 모아보기 RecyclerView
//        recyclerView_place = view.findViewById(R.id.recycler_frag_first_layout);
//        ArrayList<DataPage> list_place = new ArrayList<>();
//
//        list_place.add(new DataPage(getResources().getDrawable(R.drawable.junggu),"중구"));
//        list_place.add(new DataPage(getResources().getDrawable(R.drawable.jongro), "종로구"));
//        list_place.add(new DataPage(getResources().getDrawable(R.drawable.songpa), "송파구"));
//        list_place.add(new DataPage(getResources().getDrawable(R.drawable.gangnam), "강남구"));
//        list_place.add(new DataPage(getResources().getDrawable(R.drawable.yeouido), "영등포구"));


//        recyclerView_place.setAdapter(new RecyclerFragFirstPlaceAdapter(list_place));
//        recyclerView_place.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Return view
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
        recyclerView_place.setAdapter(new RecyclerFragFirstPlaceAdapter(getActivity(),list_place));
        //recyclerView_place.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_place.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

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

    @Override
    public void onClick(View view) {
        switch(view.getId()){

//            case R.id.btnToggle:
//
//                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
//                    btnToggle.setText("가로로 슬라이드");
//
//                }else {
//                    btnToggle.setText("세로로 슬라이드");
//                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
//                }
//
//                break;

//            case R.id.btn_close:
//
//                if (layout_ctr == 1) {
//
//                    anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
//                            R.anim.nfc2_anim); //애니메이션설정파일
//                    layout_menu.startAnimation(anim);
//
//                    layout_menu.setVisibility(View.GONE);
//                    layout_backgroundmenu.setVisibility(View.GONE);
//
//                    menu_btn.setVisibility(View.VISIBLE);
//                    menu_btn.setClickable(true);
//                    layout_ctr = 0;
//                }
//                break;

        }
    }
}