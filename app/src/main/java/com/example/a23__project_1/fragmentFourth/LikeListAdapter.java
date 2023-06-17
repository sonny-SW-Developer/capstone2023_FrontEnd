package com.example.a23__project_1.fragmentFourth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.fragmentSecond.PlaceListAdapter;
import com.example.a23__project_1.response.PlaceAllResponse;

import java.util.ArrayList;
import java.util.List;

public class LikeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;
    private List<PlaceAllResponse.Result> resultList;
    private List<String> themaList = new ArrayList<>();

    /**
     * cctv 버튼 클릭 리스너
     **/
    private PlaceListAdapter.cctvClickListener ccl;

    public interface cctvClickListener {
        void cctvButtonClick(List<PlaceAllResponse.Result> resultList, int position);
    }

    public void setOnCCTVClickListener(PlaceListAdapter.cctvClickListener listener) {
        this.ccl = listener;
    }

    /**
     * 찜 리스트 버튼 클릭 리스너
     **/
    private PlaceListAdapter.likeClickListener lcl;

    public interface likeClickListener {
        void likeButtonClick(List<PlaceAllResponse.Result> resultList, int position);
    }

    public void setOnLikeClickListener(PlaceListAdapter.likeClickListener listener) {
        this.lcl = listener;
    }

    /** 자세히 보기 버튼 클릭 리스너 **/
    private PlaceListAdapter.mapClickListener mcl;
    public interface mapClickListener {
        void mapButtonClick(List<PlaceAllResponse.Result> resultList, int position);
    }
    public void setOnMapClickListener(PlaceListAdapter.mapClickListener listener) { this.mcl = listener;}

    public LikeListAdapter(Context mainContext, List<PlaceAllResponse.Result> result) {
        this.mainContext = mainContext;
        this.resultList = result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_second_place_list, parent, false);
        return new LikePlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LikePlaceViewHolder vh = (LikePlaceViewHolder) holder;

        vh.like.setBackgroundResource(R.drawable.ic_heart_fill);
        // 이름, 타이틀, 좋아요 수, 이미지, 붐빔도 설정
        vh.title.setText(resultList.get(position).getName());
        themaList = resultList.get(position).getThema();
        String cate = "";
        for (String thema : themaList) {
            cate += thema + ", ";
        }
        cate = cate.substring(0, cate.length() - 2); // 카테고리 문자열로 출력

        vh.category.setText(cate);
        if (cate.contains("상업"))
            vh.image.setBackgroundResource(R.drawable.ic_business_park);
        else if (cate.contains("공원"))
            vh.image.setBackgroundResource(R.drawable.ic_park);
        else if (cate.contains("스파"))
            vh.image.setBackgroundResource(R.drawable.ic_spa);
        else if (cate.contains("쇼핑몰"))
            vh.image.setBackgroundResource(R.drawable.ic_shoppingmall);
        else if (cate.contains("거리"))
            vh.image.setBackgroundResource(R.drawable.ic_street);
        else if (cate.contains("백화점"))
            vh.image.setBackgroundResource(R.drawable.ic_baekwha);
        else if (cate.contains("관광지"))
            vh.image.setBackgroundResource(R.drawable.ic_trip);
        else if (cate.contains("지하철"))
            vh.image.setBackgroundResource(R.drawable.ic_subway);
        else if (cate.contains("마트"))
            vh.image.setBackgroundResource(R.drawable.ic_supermarket);
        else if (cate.contains("공항"))
            vh.image.setBackgroundResource(R.drawable.ic_airport);

        /** 인구밀집도 설정 **/
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

        /** CCTV 링크 없는 경우 **/
        if (resultList.get(position).getCctv().equals("")) {
            vh.cctv.setVisibility(View.GONE);
        } else {
            vh.cctv.setVisibility(View.VISIBLE);
        }

        /** 찜 리스트 여부 설정 **/
        int like_rate = resultList.get(position).getLikeYn();
        if (like_rate == 1) {
            vh.like.setBackgroundResource(R.drawable.ic_heart_fill);
        }
        else {
            vh.like.setBackgroundResource(R.drawable.ic_heart_no_fill);
        }

        /** map은 따로 보여주도록 한다. **/
        vh.map.setText("자세히 보기");
        int color = ContextCompat.getColor(mainContext, R.color.teal_700);
        vh.map.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class LikePlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, category, traffic, map;
        private ImageButton like, cctv;

        public LikePlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            cctv = itemView.findViewById(R.id.iv_cctv);
            title = itemView.findViewById(R.id.tv_title);
            category = itemView.findViewById(R.id.tv_content);
            traffic = itemView.findViewById(R.id.tv_traffic);
            like = itemView.findViewById(R.id.iv_heart);
            map = itemView.findViewById(R.id.tv_map);

            /** cctv 버튼 클릭 리스너 **/
            cctv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ccl != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            ccl.cctvButtonClick(resultList, position);
                        }
                    }
                }
            });

            /** 찜 버튼 클릭 리스너 **/
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lcl != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            lcl.likeButtonClick(resultList, position);
                        }
                    }
                }
            });

            /** 자세히 보기 버튼 클릭 리스너 **/
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mcl != null) {
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mcl.mapButtonClick(resultList, position);
                        }
                    }
                }
            });
        }
    }
}
