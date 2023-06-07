package com.example.a23__project_1.retrofit;

import com.example.a23__project_1.response.LoginResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.ThemaAllResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/login")
    Call<LoginResponse> kakaoLogin(@Body String token);

    @GET("/thema")
    Call<ThemaAllResponse> getAllThema();

    @GET("/place")
    Call<PlaceAllResponse> getAllPlace();
}
