package com.example.a23__project_1.fragmentFifth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.InquiryResponse;
import com.example.a23__project_1.response.NoticeResponse;

import java.util.List;

public class InquiryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;
    List<InquiryResponse.Result> list;

    /** 아이템 클릭 리스너 **/
    private inquiryClickListener icl;
    public interface inquiryClickListener {
        void onItemClick(int pos);
    }
    public void setOnInquiryClickListener(inquiryClickListener icl) {
        this.icl = icl;
    }

    public InquiryListAdapter(Context mainContext, List<InquiryResponse.Result> list) {
        this.mainContext = mainContext;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_inquiry_list, parent, false);
        return new InquiryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InquiryListViewHolder vh = (InquiryListViewHolder) holder;
        vh.title.setText(list.get(position).getTitle());
        vh.content.setText(list.get(position).getText());
        String answer = list.get(position).getAnswer();

        if(answer == null || answer.isEmpty()) {
            vh.state.setText("답변 대기중...");
            int color = ContextCompat.getColor(mainContext, R.color.secondary_grey_black_7);
            vh.state.setTextColor(color);
        }
        else {
            vh.state.setText("답변 완료!");
            int color = ContextCompat.getColor(mainContext, R.color.main_white_500);
            vh.state.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InquiryListViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, state;
        public InquiryListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            state = itemView.findViewById(R.id.tv_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(icl != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            icl.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
