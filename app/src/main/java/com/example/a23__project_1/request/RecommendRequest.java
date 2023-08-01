package com.example.a23__project_1.request;

public class RecommendRequest {
    private double lat;
    private double lon;
    private String email;
    private String thema;

    public RecommendRequest(String email,double lat, double lon, String thema) {
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.thema = thema;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getRecommendEmail(){
        return this.email;
    }

    public String getRecommendThema(){
        return this.thema;
    }
}