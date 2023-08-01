package com.example.a23__project_1.fragmentFourth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a23__project_1.R;
import com.example.a23__project_1.request.MakePlanRequest;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.example.a23__project_1.retrofit.RetrofitClientJwt;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakePlanActivity extends AppCompatActivity {
    private static final String TAG = "MakePlanActivity";
    ImageButton close;
    EditText title, place;
    Button btn_calendar, btn_time, btn_complete;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private RetrofitAPI apiService;
    private String email="";
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);

        Intent intent = getIntent();
        placeName = intent.getStringExtra("name");

        // sharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "null");

        close = findViewById(R.id.btn_back);
        close.setOnClickListener(closeClickListener);
        title = findViewById(R.id.et_title);
        place = findViewById(R.id.et_place);
        place.setText(placeName);
        btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(calendarClickListener);
        btn_time = findViewById(R.id.btn_time);
        btn_time.setOnClickListener(timeClickListener);
        btn_complete = findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(comPleteClickListener);
        btn_complete.setClickable(false);

        setTextWatcher(title);
    }

    // 뒤로 가기 1
    View.OnClickListener closeClickListener = v -> { finish();};

    // 뒤로 가기 2
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /** 달력 버튼 클릭 시 **/
    View.OnClickListener calendarClickListener = v -> {
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 생성
        DatePickerDialog datePickerDialog = new DatePickerDialog(MakePlanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 사용자가 선택한 날짜 처리
                        // year, monthOfYear, dayOfMonth 변수에 선택한 날짜 정보가 전달됨

                        // 월과 일이 한 자리 수인 경우 앞에 0을 붙여줍니다.
                        String monthString = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);

                        String selectedDate = year + "-" + monthString + "-" + dayString;
                        btn_calendar.setText(selectedDate);
                        checkInputs();
                    }
                }, year, month, day);

        // minDate 설정 (오늘 이전의 날짜는 선택하지 못하도록 함)
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // DatePickerDialog 표시
        datePickerDialog.show();
    };

    /** 시간 버튼 클릭 시 **/
    View.OnClickListener timeClickListener = v -> {
        // 현재 시간 가져오기
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // TimePickerDialog 생성
        TimePickerDialog timePickerDialog = new TimePickerDialog(MakePlanActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 사용자가 선택한 시간 처리
                        // hourOfDay와 minute 변수에 선택한 시간 정보가 전달됨
                        // 이곳에 선택한 시간에 대한 처리 코드를 작성하면 됩니다.
                        String hour = "";
                        if(hourOfDay < 10) {
                            hour = "0" + String.valueOf(hourOfDay);
                        } else {
                            hour = String.valueOf(hourOfDay);
                        }

                        String min = "";
                        if (minute < 10) {
                            min = "0" + String.valueOf(minute);
                        } else {
                            min = String.valueOf(minute);
                        }

                        String selectedTime = hour+":"+min;
                        btn_time.setText(selectedTime);
                        checkInputs();
                    }
                }, hour, minute, false);

        // TimePickerDialog 표시
        timePickerDialog.show();
    };

    /** 일정 생성 버튼 클릭 시 **/
    View.OnClickListener comPleteClickListener = v -> {
        doMakePlan();
    };

    /** 입력 여부 확인 **/
    private void setTextWatcher(EditText et) {
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

    /** 모든 값이 들어와 있는지 확인 **/
    private void checkInputs() {
        Log.d(TAG, "place : " + place.getText().toString());
        // 완료 버튼 활성화
        if(title.length() > 0 && place.getText().toString().length() > 0 && btn_calendar.length() > 0 && btn_time.length() > 0 ) {
            btn_complete.setClickable(true);
            btn_complete.setBackgroundTintList(ColorStateList.valueOf
                    (ContextCompat.getColor(this, R.color.secondary_grey_black_4)));
            btn_complete.setTextColor(getResources().getColor(R.color.secondary_grey_black_14));
        }
        // 완료 버튼 비활성화
        else {
            btn_complete.setClickable(false);
            btn_complete.setBackgroundTintList(ColorStateList.valueOf
                    (ContextCompat.getColor(this, R.color.secondary_grey_black_7)));
            btn_complete.setTextColor(getResources().getColor(R.color.secondary_grey_black_6));

        }
    }

    /** 일정 생성 API **/
    private void doMakePlan() {
        String accessToken = sharedPreferences.getString("accessToken", "null");
        apiService = RetrofitClientJwt.getApiService(accessToken);

        MakePlanRequest.Member member = new MakePlanRequest.Member(email);
        MakePlanRequest request = new MakePlanRequest(member, placeName,
                btn_calendar.getText().toString(), btn_time.getText().toString(), title.getText().toString());

        Call<CommonResponse> call = apiService.makePlan(accessToken, request);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                // 요청 성공 시
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "일정이 성공적으로 생성되었습니다!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Log.d(TAG, "연동 실패1..." + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG, "연동 실패2..." + t.getMessage());
            }
        });
    }
}