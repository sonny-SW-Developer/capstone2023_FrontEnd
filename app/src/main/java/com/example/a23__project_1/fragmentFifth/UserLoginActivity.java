package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.request.LoginUserRequest;
import com.example.a23__project_1.response.LoginUserResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {
    private static final String TAG = "UserLoginActivity";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;

    private ImageButton back;
    private EditText email, pw;
    private Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(backClickListener);

        email = findViewById(R.id.et_email);
        pw = findViewById(R.id.et_pw);

        register = findViewById(R.id.btn_register);
        register.setOnClickListener(registerClickListener);

        login = findViewById(R.id.btn_user_login);
        login.setOnClickListener(loginClickListener);
    }

    // 로그인 버튼 클릭 시
    View.OnClickListener loginClickListener = v -> {
        // 모두 값이 있을 때
        if(!email.getText().toString().isEmpty() && !pw.getText().toString().isEmpty()) {
            /** 로그인 API 작성 **/
            apiService = RetrofitClient.getApiService();
            String user_email = email.getText().toString();
            String user_password = pw.getText().toString();
            LoginUserRequest request = new LoginUserRequest(user_email, user_password);
            Call<LoginUserResponse> call = apiService.userAuthLogin(request);
            call.enqueue(new Callback<LoginUserResponse>() {
                @Override
                public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {
                    if(response.isSuccessful()) {
                        if(!isStringEmpty(response.body().getAccessToken())) {
                            String accessToken = response.body().getAccessToken();
                            String refreshToken = response.body().getRefreshToken();
                            String user_email = response.body().getEmail();
                            String user_role = response.body().getRole();
                            String user_name = response.body().getName();
                            String profileUrl = response.body().getProfileUrl();

                            // sharedPreferences에 변수 저장
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", user_name);
                            editor.putString("accessToken", accessToken);
                            editor.putString("refreshToken", refreshToken);
                            editor.putString("email", user_email);
                            editor.putString("role", user_role);
                            editor.putString("profile", profileUrl);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "로그인이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else
                        Log.d(TAG, "자체 로그인 연동 실패1..." + response.errorBody().toString() + response.message());
                }

                @Override
                public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                    Log.d(TAG, "자체 로그인 연동 실패2..." + t.getMessage());
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 올바르게 입력해주세요...", Toast.LENGTH_SHORT).show();
        }
    };

    // 회원가입 버튼 클릭 시
    View.OnClickListener registerClickListener = v -> {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    };

    // 뒤로 가기 버튼 클릭 시
    View.OnClickListener backClickListener = v -> {
        finish();
    };

    // 문자열이 null이거나 비어있는지를 판단하는 함수
    public boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}