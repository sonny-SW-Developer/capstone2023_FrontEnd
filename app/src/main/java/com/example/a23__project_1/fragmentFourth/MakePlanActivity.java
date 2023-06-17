package com.example.a23__project_1.fragmentFourth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.a23__project_1.R;

public class MakePlanActivity extends AppCompatActivity {

    ImageButton close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);

        close = findViewById(R.id.btn_back);
        close.setOnClickListener(closeClickListener);
    }

    View.OnClickListener closeClickListener = v -> { finish();};
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}