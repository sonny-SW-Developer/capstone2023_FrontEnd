package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

public class PlaceInfoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private PlaceInfo result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public PlaceInfo getResult() {
        return result;
    }

    public static class PlaceInfo {
        @SerializedName("place_id")
        private int placeId;

        @SerializedName("name")
        private String name;

        @SerializedName("thema")
        private String thema;

        @SerializedName("like_yn")
        private String likeYn;

        @SerializedName("popular")
        private int popular;

        @SerializedName("cctv")
        private String cctv;

        @SerializedName("rec")
        private String rec;

        public int getPlaceId() {
            return placeId;
        }

        public String getName() {
            return name;
        }

        public String getThema() {
            return thema;
        }

        public String getLikeYn() {
            return likeYn;
        }

        public int getPopular() {
            return popular;
        }

        public String getCctv() {
            return cctv;
        }

        public String getRec() {
            return rec;
        }
    }
}
