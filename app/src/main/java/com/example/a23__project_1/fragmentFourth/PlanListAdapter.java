package com.example.a23__project_1.fragmentFourth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.PlanListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;
    private List<PlanListResponse.Result> resultList;

    public PlanListAdapter(Context mainContext, List<PlanListResponse.Result> resultList) {
        this.mainContext = mainContext;
        this.resultList = resultList;
    }

    /** 아이템 클릭 리스너 **/
    private itemClickListener icl;
    public interface itemClickListener {
        void itemClick(List<PlanListResponse.Result> list, int position);
    }
    public void setOnItemClickListener(itemClickListener listener) {
        this.icl = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_plan_list, parent, false);
        return new planListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        planListViewHolder vh = (planListViewHolder) holder;
        String str_date = resultList.get(position).getStartDate();
        vh.title.setText(resultList.get(position).getTitle());
        vh.date.setText(str_date);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(str_date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Calendar today = Calendar.getInstance();

            long diff = calendar.getTimeInMillis() - today.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000); // 밀리초를 일로 변환

            // D-Day 출력
            if (days == 0) {
                vh.dDay.setText("D-Day");
            } else if (days > 0) {
                vh.dDay.setText("D-" + (days+1));
            } else {
                vh.dDay.setText("종료된 일정입니다.");
                int color = ContextCompat.getColor(mainContext, R.color.secondary_grey_black_7);
                vh.itemView.setBackgroundColor(color);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class planListViewHolder extends RecyclerView.ViewHolder {
        private TextView dDay, date, title;
        public planListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            dDay = itemView.findViewById(R.id.tv_dDay);
            date = itemView.findViewById(R.id.tv_date);

            // 아이템 클릭 리스너
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(icl != null) {
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            icl.itemClick(resultList, position);
                        }
                    }
                }
            });
        }
    }
}
