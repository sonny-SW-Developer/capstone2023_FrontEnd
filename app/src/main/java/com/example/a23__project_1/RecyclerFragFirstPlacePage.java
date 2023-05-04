package com.example.a23__project_1;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


public class RecyclerFragFirstPlacePage extends RecyclerView.ViewHolder {

    private TextView Recycler_title;
    private RelativeLayout Recycler_layout;
    private ImageView Recycler_image;

    DataPage data;

    RecyclerFragFirstPlacePage(View itemView) {
        super(itemView);
        Recycler_title = itemView.findViewById(R.id.recycler_frag_first_place_title);
        Recycler_layout = itemView.findViewById(R.id.recycler_frag_first_place_layout);
        Recycler_image = itemView.findViewById(R.id.recycler_frag_first_place_image);
    }

    public void onBind(DataPage data){
        this.data = data;
        Recycler_title.setText(data.getText());
        Recycler_image.setBackground(data.getImage());

    }
}