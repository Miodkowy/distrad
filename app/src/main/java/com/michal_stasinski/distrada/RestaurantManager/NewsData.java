package com.michal_stasinski.distrada.RestaurantManager;

/**
 * Created by win8 on 04.01.2017.
 */

public class NewsData {
    private String title ,news, imageUrl, date;

    public NewsData(){}

    public NewsData(String title, String news, String imageUrl, String date) {
        this.title = title;
        this.news = news;
        this.date = date;
        this.imageUrl = imageUrl;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
