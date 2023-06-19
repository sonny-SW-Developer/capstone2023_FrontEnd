package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.NoticeResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {
    private static final String TAG = "NoticeActivity";
    ImageButton back;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;
    private NoticeAdapter noticeAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(closeClickListener);

        recyclerView = findViewById(R.id.recycle_notice);

        getNotice();
    }

    //뒤로가기
    View.OnClickListener closeClickListener = v -> {
        finish();
    };

    /** 공지사항 목록 불러오는 API **/
    private void getNotice() {
        apiService = RetrofitClient.getApiService();
        Call<NoticeResponse> call = apiService.getNotice();
        call.enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                if(response.isSuccessful()) {
                    List<NoticeResponse.Result> list = response.body().getResult();

                    noticeAdapter = new NoticeAdapter(NoticeActivity.this, list);

                    /** 아이템 클릭 리스너 **/
                    noticeAdapter.setOnNoticeClickListener(new NoticeAdapter.noticeClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            Intent intent = new Intent(getApplicationContext(), NoticeInfoActivity.class);
                            intent.putExtra("date", list.get(pos).getDate());
                            intent.putExtra("title", list.get(pos).getTitle());
                            intent.putExtra("content", list.get(pos).getText());
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(noticeAdapter);
                    // LayoutManager 연결
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NoticeActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    Log.d(TAG, "공지 연동 성공!");

                }
                //Log.d(TAG, "공지 연동 실패 1..." + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                Log.d(TAG, "공지 연동 실패 2..." + t.getMessage());
            }
        });
    }
}