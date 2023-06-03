package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private int code;
    private String message;
    private Result result;

    public LoginResponse(int code, String message, Result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result getResult() {
        return result;
    }

    public class Result {
        @SerializedName("accessToken")
        private String accessToken;
        @SerializedName("refreshToken")
        private String refreshToken;
        @SerializedName("process")
        private String process;
        @SerializedName("userId")
        private Long userId;

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getProcess() {
            return process;
        }

        public Long getUserId() {
            return userId;
        }
    }

}
