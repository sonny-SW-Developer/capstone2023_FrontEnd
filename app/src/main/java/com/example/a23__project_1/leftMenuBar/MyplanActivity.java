package com.example.a23__project_1.leftMenuBar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.a23__project_1.R;
import com.example.a23__project_1.fragmentFirst.FragFirstInfoActivity;
import com.example.a23__project_1.fragmentFirst.RecyclerFragFirstPlacesSpecific;

public class MyplanActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan);

        intent = new Intent(this, RecyclerFragFirstPlacesSpecific.class);
        intent.putExtra("elements","more_category");
        startActivity(intent);

    }
}