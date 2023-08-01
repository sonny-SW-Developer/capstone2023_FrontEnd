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
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.response.InquiryResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.example.a23__project_1.retrofit.RetrofitClientJwt;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryListActivity extends AppCompatActivity {
    private static final String TAG = "InquiryListActivity";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;
    private String email = "";
    private InquiryListAdapter adapter;
    private List<InquiryResponse.Result> resultList;
    private RecyclerView recyclerView;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // 사용자 정보 가져오기
        email = sharedPreferences.getString("email", "null");

        resultList = new ArrayList<>();

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(closeClickListener);

        recyclerView = findViewById(R.id.recycle_inquiry);
        // LayoutManager 연결
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getInquiry();
    }

    // 뒤로 가기 버튼
    View.OnClickListener closeClickListener = v -> {
      finish();
    };

    /** 문의사항 목록 가져오기 API **/
    private void getInquiry() {
        String accessToken = sharedPreferences.getString("accessToken", "null");
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<InquiryResponse> call = apiService.getInquiryList(accessToken, email);
        call.enqueue(new Callback<InquiryResponse>() {
            @Override
            public void onResponse(Call<InquiryResponse> call, Response<InquiryResponse> response) {
                if(response.isSuccessful() && response.body().getMessage().contains("성공")) {
                    resultList = response.body().getResult();
                    adapter = new InquiryListAdapter(InquiryListActivity.this, resultList);

                    /** 아이템 클릭 리스너 **/
                    adapter.setOnInquiryClickListener(new InquiryListAdapter.inquiryClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            Intent intent = new Intent(getApplicationContext(), InquiryInfoActivity.class);
                            String answer = resultList.get(pos).getAnswer();
                            if(answer == null || answer.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "아직 답변이 완료되지 않았습니다...",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            intent.putExtra("answer", answer);
                            intent.putExtra("title", resultList.get(pos).getTitle());
                            intent.putExtra("content", resultList.get(pos).getText());
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }
                else
                    Log.d(TAG, "문의사항 목록 연동 오류 1.." + response.errorBody().toString());

            }

            @Override
            public void onFailure(Call<InquiryResponse> call, Throwable t) {
                Log.d(TAG, "문의사항 목록 연동 오류 2.." + t.getMessage());
            }
        });
    }
}