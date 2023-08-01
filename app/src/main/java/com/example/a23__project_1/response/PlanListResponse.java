package com.example.a23__project_1.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanListResponse {
    private String message;
    private List<Result> result;

    public PlanListResponse(String message, List<Result> result) {
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
        @SerializedName("member_id")
        private String memberId;

        @SerializedName("title")
        private String title;

        @SerializedName("sche_id")
        private Long scheId;

        @SerializedName("place_name")
        private String placeName;

        private List<String> thema;
        @SerializedName("start_date")
        private String startDate;

        @SerializedName("start_time")
        private String startTime;

        public Result(String memberId, String title, Long scheId, String placeName, List<String> thema,
                      String startDate, String startTime) {
            this.memberId = memberId;
            this.title = title;
            this.scheId = scheId;
            this.placeName = placeName;
            this.thema = thema;
            this.startDate = startDate;
            this.startTime = startTime;
        }

        public List<String> getThema() {
            return thema;
        }

        public String getMemberId() {
            return memberId;
        }

        public String getTitle() {
            return title;
        }

        public Long getScheId() {
            return scheId;
        }

        public String getPlaceName() {
            return placeName;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getStartTime() {
            return startTime;
        }
    }
}
