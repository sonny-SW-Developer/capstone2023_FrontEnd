package com.example.a23__project_1.response;

public class NoticeResponse {
    private String title;
    private String text;
    /** 필요시 SerializedName 사용 **/
    private String date;

    public NoticeResponse(String title, String text, String date) {
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
