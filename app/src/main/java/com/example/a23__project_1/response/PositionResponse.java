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

        @SerializedName("like_yn")
        private int likeYn;

        private double latitude;
        private double longitude;

        @SerializedName("image_url")
        private String imageURL;

        public Result(Long placeId,Integer popular,String name,int likeYn,
                     String image_url,double latitude,double longitude,String thema_name) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.placeId = placeId;
            this.thema_name = thema_name;
            this.popular = popular;
            this.likeYn = likeYn;
            this.imageURL = image_url;
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

        public String getImageURL(){return imageURL;}

        public int getLikeYn() {
            return likeYn;
        }

        public void setLikeYn(int likeYn) {
            this.likeYn = likeYn;
        }
    }
}
