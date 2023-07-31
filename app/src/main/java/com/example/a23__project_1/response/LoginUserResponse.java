package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

public class LoginUserResponse {
    private String accessToken;
    private int accessTokenRemainTime;
    private String email;
    private String refreshToken;
    private int refreshTokenRemainTime;
    private String role;
    @SerializedName("nickname")
    private String name;
    @SerializedName("image")
    private String profileUrl;

    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getAccessTokenRemainTime() {
        return accessTokenRemainTime;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getRefreshTokenRemainTime() {
        return refreshTokenRemainTime;
    }

    public String getRole() {
        return role;
    }

    public LoginUserResponse(String accessToken, int accessTokenRemainTime, String email,
                             String refreshToken, int refreshTokenRemainTime, String role,
                             String name, String profileUrl) {
        this.accessToken = accessToken;
        this.accessTokenRemainTime = accessTokenRemainTime;
        this.email = email;
        this.refreshToken = refreshToken;
        this.refreshTokenRemainTime = refreshTokenRemainTime;
        this.role = role;
        this.name = name;
        this.profileUrl = profileUrl;
    }
}
