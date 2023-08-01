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
        private String email;

        public Member(String email) {
            this.email = email;
        }

        public String getMember_id() {
            return email;
        }
    }
}
