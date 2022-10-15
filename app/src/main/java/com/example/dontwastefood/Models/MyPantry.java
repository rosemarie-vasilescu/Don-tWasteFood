package com.example.dontwastefood.Models;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPantry {
    private String name;
    private String image;
    private String date;
    private String id;
    private String aisle;
    public String toString(){
        return "name: " + name ;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getAisle() {
        return aisle;
    }
}
