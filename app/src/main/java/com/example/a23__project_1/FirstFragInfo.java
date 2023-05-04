package com.example.a23__project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a23__project_1.R;

public class FirstFragInfo extends AppCompatActivity {
    private Intent intent;
    private String messege;
    private TextView testText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_frag_info);

        testText = (TextView)findViewById(R.id.testText);

        intent = getIntent();
        if(intent.hasExtra("elements")){
            messege = intent.getStringExtra("elements");
        }
        switch (messege){
            case "more_category":
                testText.setText("카테고리더보기");
                break;
            case "more_place":
                testText.setText("장소더보기");
                break;
            default:
                testText.setText("에러");

        }

    }
}