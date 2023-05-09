package com.example.a23__project_1;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// 안쓰이는 것 같은데 ,,,,

public class RecyclerFragFirstMoreInfoPage extends RecyclerView.ViewHolder {

    private TextView txtTitle;
    private TextView txtBody;
    private ImageButton imgButton;
    private ImageView imageView;

    DataMoreInfo data;

    RecyclerFragFirstMoreInfoPage(View itemView) {
        super(itemView);
        txtTitle= itemView.findViewById(R.id.recycler_activity_first_place_title);
        txtBody= itemView.findViewById(R.id.recycler_activity_first_place_body);
        imgButton = itemView.findViewById(R.id.btn_add_cart);
        imageView = itemView.findViewById(R.id.recycler_activity_first_image);
    }

    public void onBind(DataMoreInfo data){
        this.data = data;
        txtTitle.setText(data.getTitle());
        txtBody.setText(data.getBody());
        imageView.setImageResource(data.getImage_path());
    }
}