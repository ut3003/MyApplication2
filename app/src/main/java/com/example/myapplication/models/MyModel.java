package com.example.myapplication.models;

import android.widget.TextView;

public class MyModel {

    private String title;
    private String imageUrl;

    public MyModel(String imageUrl, String title) {
        this.title = title;
        this.imageUrl = imageUrl;
    }


    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
