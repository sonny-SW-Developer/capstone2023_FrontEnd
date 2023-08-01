package com.example.a23__project_1.response;

public class LikeResponse {
    private boolean isSuccess;
    private int code;
    private String message;
    private Object result;

    public LikeResponse(int code, String message, Result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // result 값이 String인지, 객체인지 판별
    public boolean isResultString() {
        return result instanceof String;
    }

    // String 결과 값 리턴
    public String getResultAsString() {
        if (isResultString())
            return (String) result;
        else
            return null;
    }

    // 객체 값 리턴
    public PlaceInfoResponse getResultAsCustomObject() {
        if(!isResultString() && result != null) {
            return (PlaceInfoResponse) result;
        } else return null;
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
