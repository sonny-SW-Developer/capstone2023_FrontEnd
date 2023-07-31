package com.example.a23__project_1.fragmentFifth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.request.RegisterUserRequest;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;
    private EditText email, password, name;
    private RadioGroup radioGroup;
    private RadioButton rb_user;
    private ImageButton back;
    private String role = "USER";
    private Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(backClickListener);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_pw);
        name = findViewById(R.id.et_name);

        // setTextwatcher 설정
        setTextWatcher(email);
        setTextWatcher(password);
        setTextWatcher(name);

        // 라디오 그룹 & 버튼 관련 코드
        radioGroup = findViewById(R.id.radio_group);
        rb_user = findViewById(R.id.rb_user);
        rb_user.setChecked(true);
        // 라디오 그룹 선택 여부 코드
        checkRadioButton();

        // 회원가입 완료 버튼
        complete = findViewById(R.id.btn_complete);
        complete.setOnClickListener(registerClickListener);
        complete.setClickable(false);
    }

    // EditText 글자 변환 확인
    private void setTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
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

    // EditText 입력값 여부 확인
    private void checkInputs() {
        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !name.getText().toString().isEmpty()) {
            if(password.getText().toString().length() >= 4 && email.getText().toString().contains("@")) {
                complete.setClickable(true);
                complete.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_14));
                Drawable whiteBackground = ContextCompat.getDrawable(this, R.color.secondary_grey_black_1);
                complete.setBackground(whiteBackground);
            }
        }
        else {
            complete.setClickable(false);
            complete.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_4));
            Drawable grayBackground = ContextCompat.getDrawable(this, R.color.secondary_grey_black_9);
            complete.setBackground(grayBackground);
        }
    }

    // 라디오 그룹 선택 여부 코드
    private void checkRadioButton() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_user:
                        role = "USER";
                        break;
                    case R.id.rb_admin:
                        role = "ADMIN";
                        break;
                }
            }
        });
    }

    // 뒤로 가기 버튼 클릭 시
    View.OnClickListener backClickListener = v -> {
      finish();
    };

    // 회원가입 신청 시
    View.OnClickListener registerClickListener = v -> {
        apiService = RetrofitClient.getApiService();
        String user_email = email.getText().toString();
        String user_password = password.getText().toString();
        String user_name = name.getText().toString();
        RegisterUserRequest request = new RegisterUserRequest(user_email, user_password, role, user_name);
        Call<ResponseBody> call = apiService.userSignUp(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else {
                    Toast.makeText(getApplicationContext(), "해당 계정은 이미 존재합니다. 다른 이메일로 회원가입을 진행해주세요.", Toast.LENGTH_SHORT).show();
                    email.setText(null);
                    password.setText(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "회원가입 실패2.." + t.getMessage());
            }
        });
    };
}