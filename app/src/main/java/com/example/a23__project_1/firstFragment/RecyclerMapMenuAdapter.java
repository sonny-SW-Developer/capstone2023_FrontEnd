package com.example.a23__project_1.firstFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecyclerMapMenuAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewMapMenu";
    private int Anim_Rotate_num = 20;
    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataMoreInfo> dataModels;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public RecyclerMapMenuAdapter(Context context, ArrayList<DataMoreInfo> dataModels) {
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
                .inflate(R.layout.recylcer_map_menu, parent, false);
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
        myViewHolder.back_title.setText(dataModels.get(position).getTitle());
        myViewHolder.back_popular.setText(dataModels.get(position).getPopularStr());
        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.menu_popular.setTextColor(0x008000);
            myViewHolder.back_popular.setTextColor(0x008000);
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.menu_popular.setTextColor(0xFFFF00);
            myViewHolder.back_popular.setTextColor(0xFFFF00);
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.menu_popular.setTextColor(0xFF8000);
            myViewHolder.back_popular.setTextColor(0xFF8000);
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.menu_popular.setTextColor(0xFF0000);
            myViewHolder.back_popular.setTextColor(0xFF0000);
        }
        setImage(myViewHolder,position);
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




    }
    // Api 사진 로딩 가능할때, 변경해야 하는 함수 => 임의 구현
    public void setImage(MyViewHolder myViewHolder,int position){
        int id = (int) dataModels.get(position).getPlaceId();
        switch (id){
            case 1:
                myViewHolder.menu_image.setImageResource(R.drawable.dmc);
                break;
            case 2:
                myViewHolder.menu_image.setImageResource(R.drawable.ifc);
                break;
            case 3:
                myViewHolder.menu_image.setImageResource(R.drawable.nc_gangseo);
                break;
            case 4:
                myViewHolder.menu_image.setImageResource(R.drawable.nc_bul);
                break;
            case 5:
                myViewHolder.menu_image.setImageResource(R.drawable.nc_songpa);
                break;
            case 6:
                myViewHolder.menu_image.setImageResource(R.drawable.nc_singro);
                break;
            case 7:
                myViewHolder.menu_image.setImageResource(R.drawable.wmall);
                break;
            case 8:
                myViewHolder.menu_image.setImageResource(R.drawable.gardenfive);
                break;
            case 9:
                myViewHolder.menu_image.setImageResource(R.drawable.garosu);
                break;
            case 10:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_gadi);
                break;
            case 11:
                myViewHolder.menu_image.setImageResource(R.drawable.gn_mice);
                break;
            case 12:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_gn);
                break;
            case 13:
                myViewHolder.menu_image.setImageResource(R.drawable.galleria_east);
                break;
            case 14:
                myViewHolder.menu_image.setImageResource(R.drawable.galleria_west);
                break;
            case 15:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_gundae);
                break;
            case 16:
                myViewHolder.menu_image.setImageResource(R.drawable.gbkkung);
                break;
            case 17:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_goter);
                break;
            case 18:
                myViewHolder.menu_image.setImageResource(R.drawable.duksu);
                break;
            case 19:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_gyodae);
                break;
            case 20:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_gudi);
                break;
            case 21:
                myViewHolder.menu_image.setImageResource(R.drawable.guklib_museum);
                break;
            case 22:
                myViewHolder.menu_image.setImageResource(R.drawable.gimpo_guknae);
                break;
            case 23:
                myViewHolder.menu_image.setImageResource(R.drawable.gimpo_gukjae);
                break;
            case 24:
                myViewHolder.menu_image.setImageResource(R.drawable.naksan);
                break;
            case 25:
                myViewHolder.menu_image.setImageResource(R.drawable.namsan_park);
                break;
            case 26:
                myViewHolder.menu_image.setImageResource(R.drawable.noryangjin);
                break;
            case 27:
                myViewHolder.menu_image.setImageResource(R.drawable.thehyundai);
                break;
            case 28:
                myViewHolder.menu_image.setImageResource(R.drawable.dongdae_tukgu);
                break;
            case 29:
                myViewHolder.menu_image.setImageResource(R.drawable.ttuksum);
                break;
            case 30:
                myViewHolder.menu_image.setImageResource(R.drawable.lottemax_ydp);
                break;
            case 31:
                myViewHolder.menu_image.setImageResource(R.drawable.lottemart_songpa);
                break;
            case 32:
                myViewHolder.menu_image.setImageResource(R.drawable.lottemall_gimpo);
                break;
//            case 33:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 34:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 35:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 36:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 37:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 38:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 39:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 40:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 1:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 2:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 3:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 4:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 5:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 6:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 7:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 8:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 9:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;
//            case 10:
//                myViewHolder.imageView.setImageResource(R.drawable);
//                break;

        }

    }
    public void setLottie(MyViewHolder myViewHolder,int position){

        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.menu_popular_lottie.setAnimation("popular1.json");
            myViewHolder.menu_back_lottie.setAnimation("popular1.json");
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.menu_popular_lottie.setAnimation("popular2.json");
            myViewHolder.menu_back_lottie.setAnimation("popular2.json");
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.menu_popular_lottie.setAnimation("popular3.json");
            myViewHolder.menu_back_lottie.setAnimation("popular3.json");
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.menu_popular_lottie.setAnimation("popular4.json");
            myViewHolder.menu_back_lottie.setAnimation("popular4.json");
        }
//        myViewHolder.menu_popular_lottie.playAnimation();
//        myViewHolder.menu_back_lottie.playAnimation();
//        myViewHolder.menu_popular_lottie.setRepeatCount(Anim_Rotate_num);
//        myViewHolder.menu_back_lottie.setRepeatCount(Anim_Rotate_num);
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
        TextView back_title;
        TextView back_popular;
        LottieAnimationView menu_back_lottie;
        ImageView menu_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_title = itemView.findViewById(R.id.activity_map_place_title);
            menu_popular = itemView.findViewById(R.id.activity_first_place_popular) ;
            menu_popular_lottie = itemView.findViewById(R.id.lottie_activity_first_place_popular);
            back_title = itemView.findViewById(R.id.activity_map_background_title);
            back_popular= itemView.findViewById(R.id.activity_map_background_popular) ;
            menu_back_lottie= itemView.findViewById(R.id.activity_map_background_lottie_popular);
            menu_image = itemView.findViewById(R.id.activity_map_menu_image);
        }
    }

}