package com.example.a23__project_1.response;

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
        private Long placeId;
        private List<String> thema;
        private int likeYn;
        private String picture;
        private Integer popular;
        private String cctv;

        public Result(String name, Long placeId, List<String> thema, int likeYn,
                      String picture, Integer popular, String cctv) {
            this.name = name;
            this.placeId = placeId;
            this.thema = thema;
            this.likeYn = likeYn;
            this.picture = picture;
            this.popular = popular;
            this.cctv = cctv;
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

        public String getPicture() {
            return picture;
        }

        public Integer getPopular() {
            return popular;
        }

        public String getCctv() {
            return cctv;
        }
    }
}
