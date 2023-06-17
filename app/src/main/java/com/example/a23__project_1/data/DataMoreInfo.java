package com.example.a23__project_1.data;

public class DataMoreInfo {
    String title;
    String body;
    int image_path;
    int popular;
    boolean isInCart;
    long placeId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String title) {
        this.body = body;
    }

    public int getImage_path() {
        return image_path;
    }
    public int getPopular() {
        return popular;
    }
    public String getPopularStr() {
        if(this.popular == 0){
            return "혼잡도 로딩중입니다...";
        }else if(this.popular == 1){
            return "여유";
        }else if(this.popular == 2){
            return "보통";
        }else if(this.popular == 3){
            return "약간혼잡";
        }else if(this.popular == 4){
            return "매우혼잡";
        }
        return "";
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public Boolean getBoolean_cart() {
        return isInCart;
    }

    public void setBoolean_cart(Boolean isInCart) {
        this.isInCart = isInCart;
    }

    public DataMoreInfo(String title, String body,int image_path,Boolean isInCart,int popular, long placeId) {
        this.title = title;
        this.body = body;
        this.image_path = image_path;
        this.isInCart = isInCart;
        this.popular = popular;
        this.placeId = placeId;
    }
}
