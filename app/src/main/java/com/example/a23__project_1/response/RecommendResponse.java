package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendResponse {
    private boolean isSuccess;
    private int code;
    private String message;
    private List<RecommendResponse.Result> result;

    public RecommendResponse(boolean isSuccess, int code, String message, List<RecommendResponse.Result> result) {
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

    public List<RecommendResponse.Result> getResult() {
        return result;
    }

    public class Result {
        @SerializedName("address")
        private String address;
        @SerializedName("place_id")
        private Long placeId;
        @SerializedName("popular")
        private Integer popular;
        @SerializedName("name")
        private String name;

        @SerializedName("cctv")
        private String cctv;

        @SerializedName("like_yn")
        private int likeYn;

        @SerializedName("thema")
        private String thema;

        @SerializedName("image_url")
        private String imageURL;

        public Result(String address, long placeId,Integer popular,String name,String cctv, int likeYn, String image_url,String thema ) {
            this.address = address;
            this.name = name;
            this.placeId = placeId;
            this.thema = thema;
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

        public String getThema() { return thema; }

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
