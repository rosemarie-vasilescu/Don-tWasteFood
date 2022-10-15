package com.example.dontwastefood.Models;

public class ShoppingList {
    private String id;
    private String name;
    private String quantity;
    private String image;
    private String aisle;

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }
    public String getId(){
        return id;
    }
    public String getAisle(){
        return id;
    }
    public String toString(){
        return "name: " + name;
    }
}
