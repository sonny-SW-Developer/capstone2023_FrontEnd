package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a23__project_1.R;

public class InquiryInfoActivity extends AppCompatActivity {

    ImageButton back;
    TextView tv_answer, tv_title, tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_info);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(closeClickListener);

        Intent intent = getIntent();
        String answer = intent.getStringExtra("date");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        tv_answer = findViewById(R.id.tv_answer);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);

        tv_answer.setText(answer);
        tv_title.setText(title);
        tv_content.setText(content);
    }

    View.OnClickListener closeClickListener = v -> {
        finish();
    };
}