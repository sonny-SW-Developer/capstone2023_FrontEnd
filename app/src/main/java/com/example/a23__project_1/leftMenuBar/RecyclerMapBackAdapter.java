package com.example.a23__project_1.leftMenuBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataMoreInfo;

import java.util.ArrayList;
/*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */

public class RecyclerMapBackAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewMapBack";
    private int Anim_Rotate_num = 20;
    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataMoreInfo> dataModels;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public RecyclerMapBackAdapter(Context context, ArrayList<DataMoreInfo> dataModels) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return dataModels.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_map_back, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.menu_title.setText(dataModels.get(position).getTitle());
        myViewHolder.menu_popular.setText(dataModels.get(position).getPopularStr());

        Log.d("RecyclerMapBack",dataModels.get(position).getPopularStr());
        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.menu_popular.setTextColor(0xFF008000);
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.menu_popular.setTextColor(0xFFFFEA00);
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.menu_popular.setTextColor(0xFFFF8000);
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.menu_popular.setTextColor(0xFFFF0000);
        }
        //setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
        setLottie(myViewHolder,position);

//        myViewHolder.imgButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, position + "번째 텍스트 뷰 클릭", Toast.LENGTH_SHORT).show();
//                if(dataModels.get(position).getBoolean_cart()){
//                    dataModels.get(position).setBoolean_cart(false);
//                }else{
//                    dataModels.get(position).setBoolean_cart(true);
//                }
//                setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
//            }
//        });
//        myViewHolder.btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nfc2_anim); //애니메이션설정파일
////                layout_background.startAnimation(anim);
////                layout_menu.setClickable(false);
////                layout_menu.setVisibility(View.GONE);
////                layout_background.setClickable(false);
////                layout_background.setVisibility(View.GONE);
////
////                //menu_btn.setVisibility(View.VISIBLE);
////                map.setClickable(true);
////                background_ctr = 0;
////                main_ctr = 0;
////                marker_ctr = 0;
////                mapView.removeAllCircles();
////                mapView.getCircles();
////
//            }
//        });



    }
    public void setLottie(MyViewHolder myViewHolder,int position){

        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.menu_popular_lottie.setAnimation("popular1.json");
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.menu_popular_lottie.setAnimation("popular2.json");
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.menu_popular_lottie.setAnimation("popular3.json");
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.menu_popular_lottie.setAnimation("popular4.json");
        }

    }
//    public void setCart(boolean isit, MyViewHolder myViewHolder){
//        if(isit){
//            myViewHolder.imgButton.setImageResource(R.drawable.baseline_star_rate_24);
//        }else{
//            myViewHolder.imgButton.setImageResource(R.drawable.baseline_star_border_24);
//        }
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menu_title;
        TextView menu_popular;
        LottieAnimationView menu_popular_lottie;
        ImageButton btn_close;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_title = itemView.findViewById(R.id.activity_map_place_title);
            menu_popular = itemView.findViewById(R.id.activity_first_place_popular) ;
            menu_popular_lottie = itemView.findViewById(R.id.lottie_activity_first_place_popular);
            btn_close=itemView.findViewById(R.id.btn_map_close1);
        }
    }

}