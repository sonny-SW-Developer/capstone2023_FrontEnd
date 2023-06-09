package com.example.a23__project_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentFifth extends Fragment {
    private static final String TAG = "FragmentFifth";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String name = "";
    private String email = "";
    private String profile = "";

    private ImageView imgProfile;
    private TextView tv_name, tv_name1,tv_email;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "null");
        email = sharedPreferences.getString("email", "null");
        profile = sharedPreferences.getString("profile", "null");

        // 이름 설정
        tv_name = view.findViewById(R.id.info_name);
        tv_name1 = view.findViewById(R.id.text_user_name);
        // 이메일
        tv_email = view.findViewById(R.id.info_email);
        imgProfile = view.findViewById(R.id.iv_profile);

        // 값이 존재한다면
        if (!name.equals("null")) {
            Log.d(TAG, name);
            tv_name.setText(name);
            tv_name1.setText(name);
        }
        if(!email.equals("null")) {
            tv_email.setText(email);
        }
        if(!profile.equals("null")) {
            //프로필 이미지 사진 set
            Glide.with(this).load(profile).into(imgProfile);
        }

        return view;

    }
}