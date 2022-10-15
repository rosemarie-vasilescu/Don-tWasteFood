package com.example.dontwastefood.Models;

public class ProductMatch {
    private int id;
    private String title;
    private String description;
    private String price;
    private String imageUrl;
    private double averageRating;
    private double ratingCount;
    private double score;
    private String link;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public double getRatingCount() {
        return ratingCount;
    }

    public double getScore() {
        return score;
    }

    public String getLink() {
        return link;
    }
}
