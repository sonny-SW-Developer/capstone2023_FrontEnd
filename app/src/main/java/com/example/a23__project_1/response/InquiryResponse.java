package com.example.a23__project_1.response;

import java.util.List;

public class InquiryResponse {
    private String message;
    private List<Result> result;

    public InquiryResponse(String message, List<Result> result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public List<Result> getResult() {
        return result;
    }

    public static class Result {
        private String text;
        private String answer;
        private String member;
        private String title;

        public Result(String text, String answer, String member, String title) {
            this.text = text;
            this.answer = answer;
            this.member = member;
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public String getAnswer() {
            return answer;
        }

        public String getMember() {
            return member;
        }

        public String getTitle() {
            return title;
        }
    }
}
