package com.example.a23__project_1.response;

public class LikeResponse {
    private int code;
    private String message;
    private Object result;

    public LikeResponse(int code, String message, Result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public class Result {
        private String result;

        public Result(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
