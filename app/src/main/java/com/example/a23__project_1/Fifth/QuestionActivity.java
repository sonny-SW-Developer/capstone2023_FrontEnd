package com.example.a23__project_1.Fifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a23__project_1.R;

public class QuestionActivity extends AppCompatActivity {

    ImageButton back;
    EditText input, title;
    Button make_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
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
        Toast.makeText(getApplicationContext(), "눌렸다!", Toast.LENGTH_SHORT).show();
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
}