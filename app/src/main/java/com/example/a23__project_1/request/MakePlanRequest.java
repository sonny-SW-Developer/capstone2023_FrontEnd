package com.example.a23__project_1.request;

public class MakePlanRequest {
    private Member member;
    private String place_name;
    private String start_date;
    private String start_time;
    private String title;

    public MakePlanRequest(Member member, String place_name, String start_date, String start_time, String title) {
        this.member = member;
        this.place_name = place_name;
        this.start_date = start_date;
        this.start_time = start_time;
        this.title = title;
    }

    public Member getMember() {
        return member;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getTitle() {
        return title;
    }

    public static class Member {
        private String member_id;

        public Member(String member_id) {
            this.member_id = member_id;
        }

        public String getMember_id() {
            return member_id;
        }
    }
}
