package com.example.a23__project_1.fragmentFifth;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a23__project_1.R;
import com.example.a23__project_1.s3.DownloadImageCallback;
import com.example.a23__project_1.s3.S3Utils;

public class TestActivity extends AppCompatActivity {
    private Button back;
    private ImageView image_s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(backClickListener);
        image_s3 = findViewById(R.id.img_s3);
        String imageName = "capstone_background.png";
        S3Utils.downloadImageFromS3(imageName, new DownloadImageCallback() {
            @Override
            public void onImageDownloaded(byte[] data) {
                runOnUiThread(() -> Glide.with(TestActivity.this)
                        .asBitmap()
                        .load(data)
                        .into(image_s3));
            }

            @Override
            public void onImageDownloadFailed() {
                runOnUiThread(() -> Glide.with(TestActivity.this)
                        .load(imageName)
                        .into(image_s3));
            }
        });
    }

    View.OnClickListener backClickListener = v -> {
      finish();
    };
}