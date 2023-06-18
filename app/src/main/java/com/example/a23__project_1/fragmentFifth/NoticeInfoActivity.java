package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a23__project_1.R;

public class NoticeInfoActivity extends AppCompatActivity {

    ImageButton back;
    TextView tv_date, tv_title, tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_info);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(closeClickListener);
        tv_date = findViewById(R.id.tv_date);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);

        tv_date.setText(date);
        tv_title.setText(title);
        tv_content.setText(content);
    }

    View.OnClickListener closeClickListener = v -> {
      finish();
    };
}