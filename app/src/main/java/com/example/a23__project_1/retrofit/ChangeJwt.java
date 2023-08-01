package com.example.a23__project_1.retrofit;

/** Jwt 토큰이 만료되었을 때 사용 **/

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.a23__project_1.response.ChangeJwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Context를 넣어줘야 함. 예를 들어 MainActivity 라면 MainActivity.this 를 넣어주면 됨. **/
public class ChangeJwt {
    private static final String TAG = "ChangeJWT";
    private static final String PREF_NAME = "userInfo";
    private static final String JWT_ACCESS_TOKEN = "accessToken";
    private static final String JWT_REFRESH_TOKEN = "refreshToken";

    public static void updateJwtToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String refresh_token = sharedPreferences.getString(JWT_REFRESH_TOKEN, null); // 액세스 토큰 검색

        RetrofitAPI ApiService = RetrofitClient.getApiService();

        /** 수정 **/
        Call<ChangeJwtResponse> call = ApiService.changeJwt(refresh_token);
        call.enqueue(new Callback<ChangeJwtResponse>() {
            @Override
            public void onResponse(Call<ChangeJwtResponse> call, Response<ChangeJwtResponse> response) {
                if(response.isSuccessful()) {
                    ChangeJwtResponse jwtResponse = response.body();
                    String accessToken = jwtResponse.getAccessToken();
                    String refreshToken = jwtResponse.getRefreshToken();
                    /** 저장 **/
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(JWT_ACCESS_TOKEN, accessToken); // 수정된 값 삽입
                    editor.putString(JWT_REFRESH_TOKEN, refreshToken); // 수정된 값 삽입
                    editor.apply(); // 변경사항 저장
                }
            }

            @Override
            public void onFailure(Call<ChangeJwtResponse> call, Throwable t) {
                Log.d(TAG, "Change JWT 실패...." + t.getMessage());
            }
        });
    }

}
