package com.example.a23__project_1.request;

public class LikeRequest {
    private Member member;
    private Place place;

    public Member getMember() {
        return member;
    }

    public Place getPlace() {
        return place;
    }

    public LikeRequest(Member member, Place place) {
        this.member = member;
        this.place = place;
    }

    public static class Member {
        private String member_id;

        public String getMember_id() {
            return member_id;
        }

        public Member(String member_id) {
            this.member_id = member_id;
        }
    }

    public static class Place {
        private Long place_id;

        public Place(Long place_id) {
            this.place_id = place_id;
        }

        public Long getPlace_id() {
            return place_id;
        }
    }
}
