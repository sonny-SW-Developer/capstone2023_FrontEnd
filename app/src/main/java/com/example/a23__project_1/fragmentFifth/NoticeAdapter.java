package com.example.a23__project_1.fragmentFifth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.NoticeResponse;

import java.util.List;

public class NoticeAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mainContext;
    List<NoticeResponse> noticeList;

    /** 아이템 클릭 리스너 **/
    private noticeClickListener ncl;
    public interface noticeClickListener {
        void onItemClick(int pos);
    }
    public void setOnNoticeClickListener(noticeClickListener ncl) {
        this.ncl = ncl;
    }

    public NoticeAdapter(Context mainContext, List<NoticeResponse> noticeList) {
        this.mainContext = mainContext;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_notice, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoticeViewHolder vh = (NoticeViewHolder) holder;

        vh.img.setImageResource(R.drawable.ic_mark);
        vh.title.setText(noticeList.get(position).getTitle());
        vh.content.setText(noticeList.get(position).getText());
        vh.date.setText(noticeList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, content, date;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_bulb);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ncl != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            ncl.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
