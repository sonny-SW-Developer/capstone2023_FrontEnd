package com.example.a23__project_1.response;

import java.util.List;

public class ThemaAllResponse {
    private List<Result> result;

    public ThemaAllResponse(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {
        private Long thema_id;
        private String name;

        public Result(Long thema_id, String name) {
            this.thema_id = thema_id;
            this.name = name;
        }

        public Long getThema_id() {
            return thema_id;
        }

        public String getName() {
            return name;
        }
    }
}
