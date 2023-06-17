package com.example.a23__project_1.fragmentSecond;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;
    private SparseBooleanArray selectedItems;
    private List<String> selectedCategories = new ArrayList<>();
    /**
     * 리사이클러뷰 버튼 클릭 리스너
     **/
    private categoryClickListener clickListener;

    public interface categoryClickListener {
        void OnCategoryClickListener(List<String> selectedList, int pos);
    }

    public void setOnCategoryClickListener(categoryClickListener listener) {
        this.clickListener = listener;
    }

    private List<String> dataList;

    /**
     * 리스트 추가 예정
     **/
    public categoryAdapter(Context mainContext, List<String> dataList) {
        this.mainContext = mainContext;
        this.dataList = dataList;
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_second_category_item, parent, false);
        return new categoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder vh = (CategoryViewHolder) holder;
//        vh.button.setText(dataList.get(position));
        vh.bind(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.btn_category);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            toggleSelection(pos);
                            clickListener.OnCategoryClickListener(selectedCategories, pos);
                        }
                    }
                }
            });
        }

        public void bind(int position) {
            String item = dataList.get(position);
            button.setText(item);
            button.setSelected(isItemSelected(position));

            // 배경 색깔 바꾸기
            if(isItemSelected(position)) {
                button.setBackgroundTintList(ColorStateList.valueOf
                        (ContextCompat.getColor(mainContext, R.color.main_dark_100)));
            }
            else {
                button.setBackgroundTintList(ColorStateList.valueOf
                        (ContextCompat.getColor(mainContext, R.color.secondary_grey_black_7)));
            }
        }
    }

    /** 선택 상태 토글 **/
    private void toggleSelection(int position) {
        boolean isSelected = selectedItems.get(position, false);
        selectedItems.put(position, !isSelected);

        String category = dataList.get(position);
        if(!isSelected) {
            selectedCategories.add(category);
        } else {
            selectedCategories.remove(category);
        }

        notifyItemChanged(position);
    }

    /** 주어진 위치의 아이템이 선택되었는지 여부를 반환**/
    private boolean isItemSelected(int position) {
        return selectedItems.get(position, false);
    }
}
