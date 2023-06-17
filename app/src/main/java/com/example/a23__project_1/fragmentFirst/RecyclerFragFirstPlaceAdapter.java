package com.example.a23__project_1.fragmentFirst;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a23__project_1.data.DataPlace;
import com.example.a23__project_1.R;

import java.util.ArrayList;
/*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */


public class RecyclerFragFirstPlaceAdapter extends RecyclerView.Adapter {

    /*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataPlace> dataModels;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public RecyclerFragFirstPlaceAdapter(Context context, ArrayList<DataPlace> dataModels) {
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
                .inflate(R.layout.recycler_fragment_first_place_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.textView.setText(dataModels.get(position).getTitle());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position + "번째 텍스트 뷰 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.imageView.setImageResource(dataModels.get(position).getImage_path());

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_frag_first_place_title);
            imageView = itemView.findViewById(R.id.recycler_frag_first_place_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    String strpos= Integer.toString(pos);
                    if (pos != RecyclerView.NO_POSITION) {
                        Log.d("장소별",strpos );
                        Intent intent = new Intent(context, FragFirstInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("elements",strpos );
                        context.startActivity(intent);
                    }
                }
            });


        }
    }

}