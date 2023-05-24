package com.example.a23__project_1;

public class Places {
    private double latitude;
    private double longitude;
    private String name;
    private String congestion;
    private String comment;
    private String theme;

    public Places(String name,String congestion,double latitude, double longitude,String comment, String theme){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.congestion = congestion;
        this.comment = comment;
        this.theme = theme;
    }
    public double getLatitude(){
        return this.latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCongestion(){
        return this.congestion;
    }

    public void setCongestion(String congestion){
        this.congestion = congestion;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getTheme(){
        return this.theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

}
