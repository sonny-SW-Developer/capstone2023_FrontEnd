package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeResponse {
    private List<Result> result;

    public NoticeResponse(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public static class Result {
        private String title;
        private String text;

        /** 필요시 SerializedName 사용 **/
        @SerializedName("inp_ddt")
        private String date;

        public Result(String title, String text, String date) {
            this.title = title;
            this.text = text;
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getDate() {
            return date;
        }
    }
}
