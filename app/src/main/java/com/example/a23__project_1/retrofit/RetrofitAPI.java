package com.example.a23__project_1.retrofit;

import com.example.a23__project_1.request.InquiryRequest;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.request.LoginUserRequest;
import com.example.a23__project_1.request.RegisterUserRequest;
import com.example.a23__project_1.request.MakePlanRequest;
import com.example.a23__project_1.response.ChangeJwtResponse;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.response.GetThemeResponse;
import com.example.a23__project_1.response.InquiryResponse;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.LoginUserResponse;
import com.example.a23__project_1.response.LoginResponse;
import com.example.a23__project_1.response.NoticeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PlaceInfoResponse;
import com.example.a23__project_1.response.PlanListResponse;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.response.ThemaAllResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<LikeResponse> doLike(@Header("Authorization") String token, @Body LikeRequest likeRequest);

    @GET("/place/position")
    Call<PositionResponse> getAllPosition();

    @GET("/place/position/member")
    Call<PositionResponse> getLoginAllPosition(@Query("member_id") String memberId);

    @GET("/place/thema")
    Call<GetThemeResponse> getThemePlace(@Query("theme_id") int theme_id);

    /** 문의사항 생성하기 **/
    @POST("/inquiry")
    Call<CommonResponse> doInquiry(@Body InquiryRequest request);

    /** 찜리스트 가져오기 **/
    @GET("/like/all/{member_id}")
    Call<PlaceAllResponse> getAllLikes(@Header("Authorization") String token, @Query("member_id") String memberId);

    /** 로그인 되어있을 때 모든 장소 리스트 가져오기 **/
    @GET("/place/member")
    Call<PlaceAllResponse> getLoginPlaceList(@Header("Authorization") String token, @Query("member_id") String memberId);

    /** 일정 추가 **/
    @POST("/schedule")
    Call<CommonResponse> makePlan(@Body MakePlanRequest request);

    /** 일정 확인 **/
    @GET("/schedule/all/{member_id}")
    Call<PlanListResponse> getPlanList(@Query("member_id") String memberId);

    /** 장소 이름으로 검색 **/
    @GET("/place/name")
    Call<PlaceInfoResponse> getPlaceInfo(@Query("name") String name);

    /** 공지사항 **/
    @GET("/faq/notice")
    Call<NoticeResponse> getNotice();

    /** 문의사항 목록 **/
    @GET("inquiry/all/{member_id}")
    Call<InquiryResponse> getInquiryList(@Query("member_id") String memberId);

    /** 카카오톡 로그인 (수정) **/
    @POST("/auth/kakao")
    Call<LoginUserResponse> kakaoAuthLogin(@Body String token);

    /** Jwt 갱신 **/
    @POST("/auth/reissue")
    Call<ChangeJwtResponse> changeJwt(@Header("Authorization") String token);

    /** 자체 로그인 회원가입 **/
    @POST("/auth/signUp")
    Call<ResponseBody> userSignUp(@Body RegisterUserRequest request);

    /** 자체 로그인 **/
    @POST("/auth/signIn")
    Call<LoginUserResponse> userAuthLogin(@Body LoginUserRequest request);

    /** 로그아웃 **/
    @POST("/auth/logout")
    Call<ResponseBody> userLogout(@Header("Authorization") String token);
}
