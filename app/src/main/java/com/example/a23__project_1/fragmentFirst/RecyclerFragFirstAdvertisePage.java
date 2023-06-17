package com.example.a23__project_1.fragmentFirst;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataPage;

public class RecyclerFragFirstAdvertisePage extends RecyclerView.ViewHolder {

    private TextView viewpager_title;
    private RelativeLayout viewpager_layout;
    private ImageView viewpager_image;

    DataPage data;

    RecyclerFragFirstAdvertisePage(View itemView) {
        super(itemView);
        viewpager_title = itemView.findViewById(R.id.viewpager_title);
        viewpager_layout = itemView.findViewById(R.id.viewpager_layout);
        viewpager_image = itemView.findViewById(R.id.viewpager_image);
    }

    public void onBind(DataPage data){
        this.data = data;

        viewpager_title.setText(data.getText());
        viewpager_image.setBackground(data.getImage());

    }
}