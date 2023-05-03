package com.example.a23__project_1;

import android.app.AppComponentFactory;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

public class DataPage extends AppCompatActivity {
    int color;
    String title;
    Drawable source;

    public DataPage(Drawable source, String title){


        this.source = source;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Drawable getImage(){
        return source;
    }

    public void setImage(Drawable source){
        this.source = source;
    }

    public String getText() {
        return title;
    }

    public void setText(String title) {
        this.title = title;
    }
}