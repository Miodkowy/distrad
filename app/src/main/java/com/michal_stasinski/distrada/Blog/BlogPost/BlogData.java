package com.michal_stasinski.distrada.Blog.BlogPost;

/**
 * Created by win8 on 04.01.2017.
 */

public class BlogData {
    private String title ,news, imageUrl, date;

    public BlogData(){}

    public BlogData(String title, String news, String imageUrl, String date) {
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
