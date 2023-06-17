package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceAllResponse {
    private List<Result> result;

    public PlaceAllResponse(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {
        private String name;
        @SerializedName("place_id")
        private Long placeId;
        private List<String> thema;
        @SerializedName("like_yn")
        private int likeYn;
        private String picture;
        private Integer popular;
        private String cctv;
        private String rec;

        public Result(String name, Long placeId, List<String> thema, int likeYn,
                      String picture, Integer popular, String cctv, String rec) {
            this.name = name;
            this.placeId = placeId;
            this.thema = thema;
            this.likeYn = likeYn;
            this.picture = picture;
            this.popular = popular;
            this.cctv = cctv;
            this.rec = rec;
        }

        public String getName() {
            return name;
        }

        public Long getPlaceId() {
            return placeId;
        }

        public List<String> getThema() {
            return thema;
        }

        public int getLikeYn() {
            return likeYn;
        }

        public void setLikeYn(int likeYn) {
            this.likeYn = likeYn;
        }

        public String getPicture() {
            return picture;
        }

        public Integer getPopular() {
            return popular;
        }

        public String getCctv() {
            return cctv;
        }

        public String getRec() { return rec; }
    }
}
