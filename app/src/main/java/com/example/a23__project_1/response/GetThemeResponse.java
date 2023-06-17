package com.example.a23__project_1.response;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class GetThemeResponse {

    private boolean isSuccess;
    private int code;
    private String message;
    private List<GetThemeResponse.Result> result;

    public GetThemeResponse(boolean isSuccess, int code, String message, List<GetThemeResponse.Result> result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<GetThemeResponse.Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("place_id")
        private Long placeId;
        @SerializedName("name")
        private String name;
        @SerializedName("popular")
        private String popular;


        public Result(Long placeId,String name) {
            this.name = name;

            this.placeId = placeId;

            //this.popular = popular;

        }

        public String getName() {
            return name;
        }

        public Long getPlaceId() {
            return placeId;
        }

//        public Integer getPopular() {
//            return popular;
//        }

    }

}
