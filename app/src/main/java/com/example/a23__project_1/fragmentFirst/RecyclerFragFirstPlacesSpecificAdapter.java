package com.example.a23__project_1.fragmentFirst;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import androidx.recyclerview.widget.RecyclerView;
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

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataPlaceWithS3;
import com.example.a23__project_1.s3.DownloadImageCallback;
import com.example.a23__project_1.s3.S3Utils;
import java.util.ArrayList;
/*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */

public class RecyclerFragFirstPlacesSpecificAdapter extends RecyclerView.Adapter{
    String TAG = "RecyclerViewAdapter";
    private int Anim_Rotate_num = 20;
    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataPlaceWithS3> dataModels;
    Context context;

    /** 찜 버튼 클릭 리스너 **/
    private RecyclerFragFirstPlacesSpecificAdapter.likeClickListener lcl;
    public interface likeClickListener {
        void likeButtonClick(int pos);
    }
    public void setOnLikeClickListener(RecyclerFragFirstPlacesSpecificAdapter.likeClickListener listener) {this.lcl = listener;}

    //생성자를 통하여 데이터 리스트 context를 받음
    public RecyclerFragFirstPlacesSpecificAdapter(Context context, ArrayList<DataPlaceWithS3> dataModels) {
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
                .inflate(R.layout.item_recycler_frag_first_places_specific, parent, false);
        RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder viewHolder = new RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder(view);

        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder");


        RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder myViewHolder = (RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder) holder;

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

        String imageName = dataModels.get(position).getImage_url();
        S3Utils.downloadImageFromS3(imageName, new DownloadImageCallback() {
            @Override
            public void onImageDownloaded(byte[] data) {
                runOnUiThread(() -> Glide.with(context)
                        .asBitmap()
                        .load(data)
                        .into(myViewHolder.imageView));
            }
            @Override
            public void onImageDownloadFailed() {
                runOnUiThread(() -> Glide.with(context)
                        .load(imageName)
                        .into(myViewHolder.imageView));
            }
        });

        setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
        setLottie(myViewHolder,position);

        boolean like_rate = dataModels.get(position).getBoolean_cart();
        if(like_rate)
            myViewHolder.imgButton.setBackground(context.getDrawable(R.drawable.ic_heart_fill));
        else
            myViewHolder.imgButton.setBackground(context.getDrawable(R.drawable.ic_heart_no_fill));

        setCart(dataModels.get(position).getBoolean_cart(), myViewHolder);
    }

    public void setLottie(RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder myViewHolder, int position){

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
    public void setCart(boolean isit, RecyclerFragFirstPlacesSpecificAdapter.MyViewHolder myViewHolder){
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
                            lcl.likeButtonClick(position);
                        }
                    }
                }
            });
        }
    }

}
