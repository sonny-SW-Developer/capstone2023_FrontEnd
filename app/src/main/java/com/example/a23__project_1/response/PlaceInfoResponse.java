package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

public class PlaceInfoResponse {
    private String message;
    private PlaceInfo result;

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
