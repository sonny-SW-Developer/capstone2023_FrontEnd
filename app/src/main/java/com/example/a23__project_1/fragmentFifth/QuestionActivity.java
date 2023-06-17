package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.request.InquiryRequest;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    private static final String TAG = "QuestionActivity";
    ImageButton back;
    EditText input, title;
    Button make_question;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;
    private Call<CommonResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // 뒤로 가기 버튼
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(closeClickListener);

        // 문의사항 작성
        title = findViewById(R.id.et_title);
        input = findViewById(R.id.et_question);
        // 문의사항 생성 버튼
        make_question = findViewById(R.id.make_question);
        make_question.setOnClickListener(questionClickListener);
        make_question.setClickable(false);

        textSet(title);
        textSet(input);
    }

    /** 뒤로 가기 버튼 **/
    View.OnClickListener closeClickListener  = v -> {finish();};

    /** 문의사항 생성 버튼 **/
    View.OnClickListener questionClickListener = v -> {
        /** API 신청 **/
        requireInquiry();
    };

    /** EditText 설정 **/
    public void textSet(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInputs();
            }
        });
    }

    /** 입력 여부 판단 **/
    public void checkInputs() {
        if(input.length() > 0 && title.length() > 0) {
            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.secondary_grey_black_1));
            make_question.setBackgroundTintList(colorStateList);
            make_question.setClickable(true);
        }
        else {
            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.secondary_grey_black_7));
            make_question.setBackgroundTintList(colorStateList);
            make_question.setClickable(true);
        }
    }

    /** 문의사항 요청 API 메서드 **/
    public void requireInquiry() {
        apiService = RetrofitClient.getApiService();
        // 사용자 정보 가져오기
        String email = sharedPreferences.getString("email", "null");
        // 사용자 정보가 존재하지 않는 경우
        if (email.equals("null")) {
            /** 다이얼로그 출력해주기 **/
            Toast.makeText(getApplicationContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
            return;
        }
        InquiryRequest.Member member = new InquiryRequest.Member(email);
        InquiryRequest request = new InquiryRequest(member, input.getText().toString(), title.getText().toString());
        call = apiService.doInquiry(request);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMessage().contains("성공")) {
                        Toast.makeText(getApplicationContext(), "문의가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Log.d(TAG, "문의사항 연동 실패 1 : " + response.errorBody().toString());
                    }
                }
                else {
                    Log.d(TAG, "문의사항 연동 실패 2 : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG, "문의사항 연동 실패 3 : " + t.getMessage());
            }
        });

    }
}