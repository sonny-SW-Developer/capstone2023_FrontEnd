package com.example.a23__project_1;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ViewHolderPage extends RecyclerView.ViewHolder {

    private TextView viewpager_title;
    private RelativeLayout viewpager_layout;
    private ImageView viewpager_image;

    DataPage data;

    ViewHolderPage(View itemView) {
        super(itemView);
        viewpager_title = itemView.findViewById(R.id.viewpager_title);
        viewpager_layout = itemView.findViewById(R.id.viewpager_layout);
        viewpager_image = itemView.findViewById(R.id.viewpager_image);
    }

    public void onBind(DataPage data){
        this.data = data;

        viewpager_title.setText(data.getText());
        viewpager_image.setBackground(data.getImage());
//        Glide.with(viewpager_layout)
//                .load(data.getImage())
//                .placeholder(R.drawable.load_fail)
//                .error(R.drawable.load_fail)
//                .fallback(R.drawable.load_fail)
//                .into(viewpager_image);

    }
}