package com.example.a23__project_1.placeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.PlaceAllResponse;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;
    private List<PlaceAllResponse.Result> resultList;

    public PlaceListAdapter(Context mainContext, List<PlaceAllResponse.Result> result) {
        this.mainContext = mainContext;
        this.resultList = result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_second_place_list, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceViewHolder vh = (PlaceViewHolder) holder;

        // 이름, 타이틀, 좋아요 수, 이미지, 붐빔도 설정
        vh.title.setText(resultList.get(position).getName());
        vh.category.setText(resultList.get(position).getThema());
        int traffic_rate = resultList.get(position).getPopular();
        switch (traffic_rate) {
            case 0:
                vh.traffic.setText("로딩중...");
                break;
            case 1:
                vh.traffic.setText("여유");
                break;
            case 2:
                vh.traffic.setText("보통");
                break;
            case 3:
                vh.traffic.setText("약간 붐빔");
                break;
            case 4:
                vh.traffic.setText("매우 붐빔");
                break;
        }
        int like_rate = resultList.get(position).getLikeYn();
        if (like_rate == 1) {
            vh.like.setBackgroundResource(R.drawable.ic_heart_fill);
        }
        else
            vh.like.setBackgroundResource(R.drawable.ic_heart_no_fill);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, category, traffic;
        private ImageButton like;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.tv_title);
            category = itemView.findViewById(R.id.tv_content);
            traffic = itemView.findViewById(R.id.tv_traffic);
            like = itemView.findViewById(R.id.iv_heart);
        }
    }
}
