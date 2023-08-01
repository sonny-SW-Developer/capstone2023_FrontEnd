package com.example.a23__project_1.fragmentFirst;

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
import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.R;
import com.example.a23__project_1.response.PositionResponse;

import java.util.ArrayList;
import java.util.List;
/*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */

public class RecyclerFragFirstThemeAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewAdapter";
    private int Anim_Rotate_num = 20;
    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataMoreInfo> dataModels;
    Context context;

    /** 찜 버튼 클릭 리스너 **/
    private likeClickListener lcl;
    public interface likeClickListener {
        void likeButtonClick(ArrayList<DataMoreInfo> dataModels, int pos);
    }
    public void setOnLikeClickListener(likeClickListener listener) {this.lcl = listener;}

    //생성자를 통하여 데이터 리스트 context를 받음
    public RecyclerFragFirstThemeAdapter(Context context, ArrayList<DataMoreInfo> dataModels) {
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
                .inflate(R.layout.item_recycler_frag_first_menu, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.txtTitle.setText(dataModels.get(position).getTitle());
        myViewHolder.txtBody.setText(dataModels.get(position).getPopularStr());
        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.txtBody.setTextColor(0xFF008000);
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.txtBody.setTextColor(0xFFFFEA00);
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.txtBody.setTextColor(0xFFFF8000);
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.txtBody.setTextColor(0xFFFF0000);
        }
        setImage(myViewHolder,position);
        setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
        setLottie(myViewHolder,position);

        boolean like_rate = dataModels.get(position).getBoolean_cart();
        if(like_rate)
            myViewHolder.imgButton.setBackground(context.getDrawable(R.drawable.ic_heart_fill));
        else
            myViewHolder.imgButton.setBackground(context.getDrawable(R.drawable.ic_heart_no_fill));



        setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
    }
    // Api 사진 로딩 가능할때, 변경해야 하는 함수 => 임의 구현
    public void setImage(MyViewHolder myViewHolder,int position){
        int id = (int) dataModels.get(position).getPlaceId();
        switch (id){
            case 1:
                myViewHolder.imageView.setImageResource(R.drawable.dmc);
                break;
            case 2:
                myViewHolder.imageView.setImageResource(R.drawable.ifc);
                break;
            case 3:
                myViewHolder.imageView.setImageResource(R.drawable.nc_gangseo);
                break;
            case 4:
                myViewHolder.imageView.setImageResource(R.drawable.nc_bul);
                break;
            case 5:
                myViewHolder.imageView.setImageResource(R.drawable.nc_songpa);
                break;
            case 6:
                myViewHolder.imageView.setImageResource(R.drawable.nc_singro);
                break;
            case 7:
                myViewHolder.imageView.setImageResource(R.drawable.wmall);
                break;
            case 8:
                myViewHolder.imageView.setImageResource(R.drawable.gardenfive);
                break;
            case 9:
                myViewHolder.imageView.setImageResource(R.drawable.garosu);
                break;
            case 10:
                myViewHolder.imageView.setImageResource(R.drawable.stn_gadi);
                break;
            case 11:
                myViewHolder.imageView.setImageResource(R.drawable.gn_mice);
                break;
            case 12:
                myViewHolder.imageView.setImageResource(R.drawable.stn_gn);
                break;
            case 13:
                myViewHolder.imageView.setImageResource(R.drawable.galleria_east);
                break;
            case 14:
                myViewHolder.imageView.setImageResource(R.drawable.galleria_west);
                break;
            case 15:
                myViewHolder.imageView.setImageResource(R.drawable.stn_gundae);
                break;
            case 16:
                myViewHolder.imageView.setImageResource(R.drawable.gbkkung);
                break;
            case 17:
                myViewHolder.imageView.setImageResource(R.drawable.stn_goter);
                break;
            case 18:
                myViewHolder.imageView.setImageResource(R.drawable.duksu);
                break;
            case 19:
                myViewHolder.imageView.setImageResource(R.drawable.stn_gyodae);
                break;
            case 20:
                myViewHolder.imageView.setImageResource(R.drawable.stn_gudi);
                break;
            case 21:
                myViewHolder.imageView.setImageResource(R.drawable.guklib_museum);
                break;
            case 22:
                myViewHolder.imageView.setImageResource(R.drawable.gimpo_guknae);
                break;
            case 23:
                myViewHolder.imageView.setImageResource(R.drawable.gimpo_gukjae);
                break;
            case 24:
                myViewHolder.imageView.setImageResource(R.drawable.naksan);
                break;
            case 25:
                myViewHolder.imageView.setImageResource(R.drawable.namsan_park);
                break;
            case 26:
                myViewHolder.imageView.setImageResource(R.drawable.noryangjin);
                break;
            case 27:
                myViewHolder.imageView.setImageResource(R.drawable.thehyundai);
                break;
            case 28:
                myViewHolder.imageView.setImageResource(R.drawable.dongdae_tukgu);
                break;
            case 29:
                myViewHolder.imageView.setImageResource(R.drawable.ttuksum);
                break;
            case 30:
                myViewHolder.imageView.setImageResource(R.drawable.lottemax_ydp);
                break;
            case 31:
                myViewHolder.imageView.setImageResource(R.drawable.lottemart_songpa);
                break;
            case 32:
                myViewHolder.imageView.setImageResource(R.drawable.lottemall_gimpo);
                break;
            case 33:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_gn);
                break;
            case 34:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_starcity);
                break;
            case 35:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_gwanak);
                break;
            case 36:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_gimpo);
                break;
            case 37:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_nowon);
                break;
            case 38:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_mia);
                break;
            case 39:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_bon);
                break;
            case 40:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_avenuel_bon);
                break;
            case 41:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_avenuel_world);
                break;
            case 42:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_ydp);
                break;
            case 43:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_jamsil);
                break;
            case 44:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_ch);
                break;
            case 45:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_worldmall);
                break;
            case 46:
                myViewHolder.imageView.setImageResource(R.drawable.lotte_world);
                break;
            case 47:
                myViewHolder.imageView.setImageResource(R.drawable.mario1);
                break;
            case 48:
                myViewHolder.imageView.setImageResource(R.drawable.mario3);
                break;
            case 49:
                myViewHolder.imageView.setImageResource(R.drawable.mangwon_park);
                break;
            case 50:
                myViewHolder.imageView.setImageResource(R.drawable.myongdong_tuk);
                break;
            case 51:
                myViewHolder.imageView.setImageResource(R.drawable.banpo_hangang);
                break;
            case 52:
                myViewHolder.imageView.setImageResource(R.drawable.dream_sup);
                break;
            case 53:
                myViewHolder.imageView.setImageResource(R.drawable.bukchon);
                break;
            case 54:
                myViewHolder.imageView.setImageResource(R.drawable.seoul_bigpark);
                break;
            case 55:
                myViewHolder.imageView.setImageResource(R.drawable.seoul_sup);
                break;
            case 56:
                myViewHolder.imageView.setImageResource(R.drawable.stn_seoul);
                break;
            case 57:
                myViewHolder.imageView.setImageResource(R.drawable.stn_seolleung);
                break;
            case 58:
                myViewHolder.imageView.setImageResource(R.drawable.seungsu_cafe);
                break;
            case 59:
                myViewHolder.imageView.setImageResource(R.drawable.suuyou_mukkja);
                break;
            case 60:
                myViewHolder.imageView.setImageResource(R.drawable.stn_sindorim);
                break;
            case 61:
                myViewHolder.imageView.setImageResource(R.drawable.stn_sillim);
                break;
            case 62:
                myViewHolder.imageView.setImageResource(R.drawable.shin_gn);
                break;
            case 63:
                myViewHolder.imageView.setImageResource(R.drawable.shin_bon);
                break;
            case 64:
                myViewHolder.imageView.setImageResource(R.drawable.shin_timesquare);
                break;
            case 65:
                myViewHolder.imageView.setImageResource(R.drawable.shinchon_edae);
                break;
            case 66:
                myViewHolder.imageView.setImageResource(R.drawable.ssangmundong);
                break;
            case 67:
                myViewHolder.imageView.setImageResource(R.drawable.apgujang_rodeo_load);
                break;
            case 68:
                myViewHolder.imageView.setImageResource(R.drawable.kid_big_park);
                break;
            case 69:
                myViewHolder.imageView.setImageResource(R.drawable.yeouido_park);
                break;
            case 70:
                myViewHolder.imageView.setImageResource(R.drawable.yeoksam_station);
                break;
            case 71:
                myViewHolder.imageView.setImageResource(R.drawable.yeonsinnae_station);
                break;
            case 72:
                myViewHolder.imageView.setImageResource(R.drawable.time_square);
                break;
            case 73:
                myViewHolder.imageView.setImageResource(R.drawable.wangsimli_station);
                break;
            case 74:
                myViewHolder.imageView.setImageResource(R.drawable.yongsan_station);
                break;
            case 75:
                myViewHolder.imageView.setImageResource(R.drawable.worldcuppark);
                break;
            case 76:
                myViewHolder.imageView.setImageResource(R.drawable.ichon_hangang_park);
                break;
            case 77:
                myViewHolder.imageView.setImageResource(R.drawable.itaewon_special_gu);
                break;
            case 78:
                myViewHolder.imageView.setImageResource(R.drawable.insadong);
                break;
            case 79:
                myViewHolder.imageView.setImageResource(R.drawable.iksundong);
                break;
            case 80:
                myViewHolder.imageView.setImageResource(R.drawable.jamsil_tour_special_gu);
                break;
            case 81:
                myViewHolder.imageView.setImageResource(R.drawable.jamsil_total_stadium);
                break;
            case 82:
                myViewHolder.imageView.setImageResource(R.drawable.jamsil_hangang_park);
                break;
            case 83:
                myViewHolder.imageView.setImageResource(R.drawable.jongro_chunggye);
                break;
            case 84:
                myViewHolder.imageView.setImageResource(R.drawable.changduckgung);
                break;
            case 85:
                myViewHolder.imageView.setImageResource(R.drawable.waterkingdom);
                break;
            case 86:
                myViewHolder.imageView.setImageResource(R.drawable.hangbok);
                break;
            case 87:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_dcubecity);
                break;
            case 88:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_mokdong);
                break;
            case 89:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_trade_center);
                break;
            case 90:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_mia);
                break;
            case 91:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_sinchon);
                break;
            case 92:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_apgujeung);
                break;
            case 93:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_cheonho);
                break;
            case 94:
                myViewHolder.imageView.setImageResource(R.drawable.hyundae_gasan);
                break;
            case 95:
                myViewHolder.imageView.setImageResource(R.drawable.hongdae);
                break;
        }

    }
    public void setLottie(MyViewHolder myViewHolder,int position){

        if(dataModels.get(position).getPopular() == 1){
            myViewHolder.animationView.setAnimation("popular1.json");
        }else if(dataModels.get(position).getPopular() == 2){
            myViewHolder.animationView.setAnimation("popular2.json");
        }else if(dataModels.get(position).getPopular() == 3){
            myViewHolder.animationView.setAnimation("popular3.json");
        }else if(dataModels.get(position).getPopular() == 4){
            myViewHolder.animationView.setAnimation("popular4.json");
        }
        myViewHolder.animationView.playAnimation();
        myViewHolder.animationView.setRepeatCount(Anim_Rotate_num);
    }
    public void setCart(boolean isit, MyViewHolder myViewHolder){
        if(isit){
            myViewHolder.imgButton.setImageResource(R.drawable.ic_heart_fill);
        }else{
            myViewHolder.imgButton.setImageResource(R.drawable.ic_heart_no_fill);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtBody;
        TextView txtTitle;
        ImageButton imgButton;
        ImageView imageView;
        LottieAnimationView animationView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle= itemView.findViewById(R.id.recycler_activity_first_place_title);
            txtBody= itemView.findViewById(R.id.recycler_activity_first_place_body);
            imgButton = itemView.findViewById(R.id.btn_add_cart);
            imageView = itemView.findViewById(R.id.recycler_activity_first_image);
            animationView= itemView.findViewById(R.id.lottie_popular);

            /** 찜 버튼 클릭 리스너 **/
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("여기여기","clicked");
                    if(lcl != null) {
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            lcl.likeButtonClick(dataModels, position);
                        }
                    }
                }
            });
        }
    }

}