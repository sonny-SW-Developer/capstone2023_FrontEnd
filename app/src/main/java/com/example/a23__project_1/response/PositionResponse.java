package com.example.a23__project_1.response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PositionResponse {
    private boolean isSuccess;
    private int code;
    private String message;
    private List<Result> result;

    public PositionResponse(boolean isSuccess, int code, String message, List<Result> result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {
        private String name;
        @SerializedName("place_id")
        private Long placeId;
        @SerializedName("thema_name")
        private String thema_name;
        private Integer popular;
        private double latitude;
        private double longitude;

        public Result(Long placeId,Integer popular,String name,
                      double latitude,double longitude,String thema_name) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.placeId = placeId;
            this.thema_name = thema_name;
            this.popular = popular;

        }

        public String getName() {
            return name;
        }

        public Long getPlaceId() {
            return placeId;
        }

        public String getThema() { return thema_name; }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public Integer getPopular() {
            return popular;
        }

    }
}
