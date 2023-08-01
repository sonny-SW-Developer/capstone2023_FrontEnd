package com.example.a23__project_1.data;

public class DataPlaceWithS3 {
        String title;
        String body;
        String image_url;
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

        public String getImage_url() {
            return image_url;
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

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public Boolean getBoolean_cart() {
            return isInCart;
        }

        public void setBoolean_cart(Boolean isInCart) {
            this.isInCart = isInCart;
        }

        public DataPlaceWithS3(String title, String body,String image_url,Boolean isInCart,int popular, long placeId) {
            this.title = title;
            this.body = body;
            this.image_url = image_url;
            this.isInCart = isInCart;
            this.popular = popular;
            this.placeId = placeId;
        }
    }

