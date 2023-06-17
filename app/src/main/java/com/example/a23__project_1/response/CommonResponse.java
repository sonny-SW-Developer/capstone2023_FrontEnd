package com.example.a23__project_1.response;

public class CommonResponse {
    private int code;
    private String message;
    private Object result;

    public CommonResponse(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
