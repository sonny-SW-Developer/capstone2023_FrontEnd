package com.example.a23__project_1.fragmentFifth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.a23__project_1.R;
import com.example.a23__project_1.response.LoginUserResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.example.a23__project_1.retrofit.RetrofitClientJwt;
import com.kakao.sdk.user.UserApiClient;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentFifth extends Fragment {
    private static final String TAG = "FragmentFifth";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String name = "";
    private String email = "";
    private String profile = "";

    private CircleImageView imgProfile;
    private TextView tv_name, tv_name1,tv_email;
    private LinearLayout question, notice, answer;
    private LinearLayout loginYN, logout;
    private Button userLogin;
    private ImageButton kakaoLogin;
    private RetrofitAPI apiService;

    // Test
    private Button btn_test;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);

        // 이름 설정
        tv_name = view.findViewById(R.id.info_name);
        tv_name1 = view.findViewById(R.id.text_user_name);
        // 이메일
        tv_email = view.findViewById(R.id.info_email);
        imgProfile = view.findViewById(R.id.iv_profile);
        // 문의사항 남기기
        question = view.findViewById(R.id.layout_question);
        question.setOnClickListener(questionClickListener);
        //공지사항
        notice = view.findViewById(R.id.layout_announcement);
        notice.setOnClickListener(noticeClickListener);
        //문의사항 확인
        answer = view.findViewById(R.id.layout_answer);
        answer.setOnClickListener(answerClickListener);
        // 로그인 여부 확인
        loginYN = view.findViewById(R.id.linear_login);
        logout = view.findViewById(R.id.lin_logout);
        logout.setOnClickListener(logoutClickListener);

        userLogin = view.findViewById(R.id.btn_user_login);
        userLogin.setOnClickListener(userLoginClickListener);
        kakaoLogin = view.findViewById(R.id.btn_kakao_login);
        kakaoLogin.setOnClickListener(kakaoLoginClickListener);

        /** 테스트 **/
        btn_test = view.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(testClickListener);

        changeUI();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        // UI 변경을 수행하는 로직을 작성
        changeUI();
    }

    // UI 변경 작성 여부 코드
    private void changeUI() {
        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "null");
        email = sharedPreferences.getString("email", "null");
        profile = sharedPreferences.getString("profile", "null");

        // 이름, 이메일, 이미지 String 값이 존재하는 경우
        if (!name.equals("null")) {
            // 이메일 화면이 보이지 않도록 설정한다.
            loginYN.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            tv_name.setText(name);

            changeTextStyle(name);
            tv_email.setText(email);
        }
        else {
            loginYN.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            tv_name.setText("Default");
            tv_email.setText("none");
            tv_name1.setText("로그인을 먼저 진행해주세요.");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_user);
            imgProfile.setImageBitmap(bitmap);
        }

        if(!profile.equals("null")) {
            //프로필 이미지 사진 set
            Glide.with(this).load(profile).into(imgProfile);
        }
    }

    // 사진 업로드 테스트
    View.OnClickListener testClickListener = v -> {
        Intent intent = new Intent(getActivity(), TestActivity.class);
        startActivity(intent);
    };

    // 일반 로그인 클릭 시
    View.OnClickListener userLoginClickListener = v -> {
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);
    };

    // 카카오 로그인 클릭 시
    View.OnClickListener kakaoLoginClickListener = v -> {
        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(getContext())) {
            login();
        }
        else {
            accountLogin();
        }
    };

    // 로그아웃 클릭 시
    View.OnClickListener logoutClickListener = v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("로그아웃");
        builder.setMessage("정말 로그아웃하시겠습니까?");
        builder.setIcon(R.drawable.ic_caution);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        builder.setNeutralButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 다이얼로그 표시
        AlertDialog dialog = builder.create();
        dialog.show();
    };

    // 로그아웃 기능 구현
    private void logout() {
        String accessToken = sharedPreferences.getString("accessToken", "null");
        if (!accessToken.equals("null")) {
            apiService = RetrofitClientJwt.getApiService(accessToken);
            Call<ResponseBody> call = apiService.userLogout(accessToken);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(getContext(), "로그아웃이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        changeUI();
                    }
                    else
                        Log.d(TAG, "로그아웃 실패 1..." + response.errorBody().toString() + response.message());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "로그아웃 실패2..." + t.getMessage());
                }
            });
        }
    }

    // 로그인 기능 구현
    // 카카오 계정이 있는 경우 바로 로그인
    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(getContext(),(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                /** API 요청 실행 **/
                kakaoLoginAPI(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    // 없는 경우 직접 계정으로 로그인
    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(getContext(),(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                /** API 요청 수행 **/
                kakaoLoginAPI(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    private void kakaoLoginAPI(String token) {
        apiService = RetrofitClient.getApiService();
        Call<LoginUserResponse> call = apiService.kakaoAuthLogin(token);
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

                        Toast.makeText(getContext(), "로그인이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        changeUI();

                        // 이름 설정
                        tv_name.setText(user_name);
                        changeTextStyle(user_name);
                        tv_email.setText(user_email);

                        //프로필 이미지 사진 set
                        Glide.with(getContext()).load(profileUrl).into(imgProfile);
                    }
                    else {
                        Log.d(TAG, "카카오 로그인 싪패..." + response.code() + response.message() + response.errorBody().toString());
                    }
                }
                else {
                    Log.d(TAG, "카카오 로그인 실패 1");
                    Log.d(TAG, response.message() + ", " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure : " + t.getMessage());
            }
        });
    }

    // 문자열이 null이거나 비어있는지를 판단하는 함수
    public boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // 문의사항 버튼 눌렀을 시
    /* 문의사항 남기기 */
    View.OnClickListener questionClickListener = v -> {
        // 로그인이 되어있다면
        if(!name.equals("null")) {
            Intent intent = new Intent(getActivity(), QuestionActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(requireContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
        }
    };

    // 공지사항 버튼 클릭 시
    View.OnClickListener noticeClickListener = v -> {
        Intent intent = new Intent(getActivity(), NoticeActivity.class);
        startActivity(intent);
    };

    // 문의 답변 확인하기 클릭 시
    View.OnClickListener answerClickListener = v -> {
        // 로그인이 되어있다면
        if(!name.equals("null")) {
            Intent intent = new Intent(getActivity(), InquiryListActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(requireContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
        }
    };

    // 이름만 색깔, 크기 변경하는 메서드
    private void changeTextStyle(String name) {
        String text = "반갑습니다 " + name + "님";
        SpannableString spannableString = new SpannableString(text);
        int start = text.indexOf(name);
        int end = start + name.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6200EE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_name1.setText(spannableString);

    }
}