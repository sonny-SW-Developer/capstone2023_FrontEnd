package com.example.a23__project_1.request;

public class InquiryRequest {
    private Member member;
    private String text;
    private String title;

    public InquiryRequest(Member member, String text, String title) {
        this.member = member;
        this.text = text;
        this.title = title;
    }

    public Member getMember() {
        return member;
    }

    public String getTextString() {
        return text;
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
