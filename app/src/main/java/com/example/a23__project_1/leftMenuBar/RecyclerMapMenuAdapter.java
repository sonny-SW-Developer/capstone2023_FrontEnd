package com.example.a23__project_1.leftMenuBar;

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
            myViewHolder.menu_popular.setTextColor(0xFF008000);
            myViewHolder.back_popular.setTextColor(0xFF008000);
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.menu_popular.setTextColor(0xFFFFEA00);
            myViewHolder.back_popular.setTextColor(0xFFFFEA00);
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.menu_popular.setTextColor(0xFFFF8000);
            myViewHolder.back_popular.setTextColor(0xFFFF8000);
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.menu_popular.setTextColor(0xFFFF0000);
            myViewHolder.back_popular.setTextColor(0xFFFF0000);
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
            case 33:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_gn);
                break;
            case 34:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_starcity);
                break;
            case 35:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_gwanak);
                break;
            case 36:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_gimpo);
                break;
            case 37:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_nowon);
                break;
            case 38:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_mia);
                break;
            case 39:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_bon);
                break;
            case 40:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_avenuel_bon);
                break;
            case 41:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_avenuel_world);
                break;
            case 42:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_ydp);
                break;
            case 43:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_jamsil);
                break;
            case 44:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_ch);
                break;
            case 45:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_worldmall);
                break;
            case 46:
                myViewHolder.menu_image.setImageResource(R.drawable.lotte_world);
                break;
            case 47:
                myViewHolder.menu_image.setImageResource(R.drawable.mario1);
                break;
            case 48:
                myViewHolder.menu_image.setImageResource(R.drawable.mario3);
                break;
            case 49:
                myViewHolder.menu_image.setImageResource(R.drawable.mangwon_park);
                break;
            case 50:
                myViewHolder.menu_image.setImageResource(R.drawable.myongdong_tuk);
                break;
            case 51:
                myViewHolder.menu_image.setImageResource(R.drawable.banpo_hangang);
                break;
            case 52:
                myViewHolder.menu_image.setImageResource(R.drawable.dream_sup);
                break;
            case 53:
                myViewHolder.menu_image.setImageResource(R.drawable.bukchon);
                break;
            case 54:
                myViewHolder.menu_image.setImageResource(R.drawable.seoul_bigpark);
                break;
            case 55:
                myViewHolder.menu_image.setImageResource(R.drawable.seoul_sup);
                break;
            case 56:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_seoul);
                break;
            case 57:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_seolleung);
                break;
            case 58:
                myViewHolder.menu_image.setImageResource(R.drawable.seungsu_cafe);
                break;
            case 59:
                myViewHolder.menu_image.setImageResource(R.drawable.suuyou_mukkja);
                break;
            case 60:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_sindorim);
                break;
            case 61:
                myViewHolder.menu_image.setImageResource(R.drawable.stn_sillim);
                break;
            case 62:
                myViewHolder.menu_image.setImageResource(R.drawable.shin_gn);
                break;
            case 63:
                myViewHolder.menu_image.setImageResource(R.drawable.shin_bon);
                break;
            case 64:
                myViewHolder.menu_image.setImageResource(R.drawable.shin_timesquare);
                break;
            case 65:
                myViewHolder.menu_image.setImageResource(R.drawable.shinchon_edae);
                break;
            case 66:
                myViewHolder.menu_image.setImageResource(R.drawable.ssangmundong);
                break;
            case 67:
                myViewHolder.menu_image.setImageResource(R.drawable.apgujang_rodeo_load);
                break;
            case 68:
                myViewHolder.menu_image.setImageResource(R.drawable.kid_big_park);
                break;
            case 69:
                myViewHolder.menu_image.setImageResource(R.drawable.yeouido_park);
                break;
            case 70:
                myViewHolder.menu_image.setImageResource(R.drawable.yeoksam_station);
                break;
            case 71:
                myViewHolder.menu_image.setImageResource(R.drawable.yeonsinnae_station);
                break;
            case 72:
                myViewHolder.menu_image.setImageResource(R.drawable.time_square);
                break;
            case 73:
                myViewHolder.menu_image.setImageResource(R.drawable.wangsimli_station);
                break;
            case 74:
                myViewHolder.menu_image.setImageResource(R.drawable.yongsan_station);
                break;
            case 75:
                myViewHolder.menu_image.setImageResource(R.drawable.worldcuppark);
                break;
            case 76:
                myViewHolder.menu_image.setImageResource(R.drawable.ichon_hangang_park);
                break;
            case 77:
                myViewHolder.menu_image.setImageResource(R.drawable.itaewon_special_gu);
                break;
            case 78:
                myViewHolder.menu_image.setImageResource(R.drawable.insadong);
                break;
            case 79:
                myViewHolder.menu_image.setImageResource(R.drawable.iksundong);
                break;
            case 80:
                myViewHolder.menu_image.setImageResource(R.drawable.jamsil_tour_special_gu);
                break;
            case 81:
                myViewHolder.menu_image.setImageResource(R.drawable.jamsil_total_stadium);
                break;
            case 82:
                myViewHolder.menu_image.setImageResource(R.drawable.jamsil_hangang_park);
                break;
            case 83:
                myViewHolder.menu_image.setImageResource(R.drawable.jongro_chunggye);
                break;
            case 84:
                myViewHolder.menu_image.setImageResource(R.drawable.changduckgung);
                break;
            case 85:
                myViewHolder.menu_image.setImageResource(R.drawable.waterkingdom);
                break;
            case 86:
                myViewHolder.menu_image.setImageResource(R.drawable.hangbok);
                break;
            case 87:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_dcubecity);
                break;
            case 88:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_mokdong);
                break;
            case 89:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_trade_center);
                break;
            case 90:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_mia);
                break;
            case 91:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_sinchon);
                break;
            case 92:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_apgujeung);
                break;
            case 93:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_cheonho);
                break;
            case 94:
                myViewHolder.menu_image.setImageResource(R.drawable.hyundae_gasan);
                break;
            case 95:
                myViewHolder.menu_image.setImageResource(R.drawable.hongdae);
                break;


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