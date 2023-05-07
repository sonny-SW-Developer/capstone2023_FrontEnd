package com.example.a23__project_1;

public class DataMoreInfo {
    String title;
    String body;
    int image_path;
    boolean isInCart;

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

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public Boolean getBoolean_cart() {
        return isInCart;
    }

    public void setBoolean_cart(Boolean isInCart) {
        this.isInCart = isInCart;
    }

    public DataMoreInfo(String title, String body,int image_path,Boolean isInCart) {
        this.title = title;
        this.body = body;
        this.image_path = image_path;
        this.isInCart = isInCart;
    }
}
