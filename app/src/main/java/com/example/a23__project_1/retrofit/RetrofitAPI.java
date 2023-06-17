package com.example.a23__project_1.retrofit;

import com.example.a23__project_1.request.InquiryRequest;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.response.GetThemeResponse;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.LoginResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.response.ThemaAllResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/login")
    Call<LoginResponse> kakaoLogin(@Body String token);

    @GET("/thema")
    Call<ThemaAllResponse> getAllThema();

    @GET("/place")
    Call<PlaceAllResponse> getAllPlace();

    @POST("/like")
    Call<LikeResponse> doLike(@Body LikeRequest likeRequest);

    @GET("/place/position")
    Call<PositionResponse> getAllPosition();

    @GET("/place/thema")
    Call<GetThemeResponse> getThemePlace(@Query("theme_id") int theme_id);

    /** 문의사항 생성하기 **/
    @POST("/inquiry")
    Call<CommonResponse> doInquiry(@Body InquiryRequest request);
}
