package com.rgs.capstone.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "fav_news")
public class NewsTable {
    @NonNull
    @PrimaryKey
    private String title;

    private String author;
    private String content;
    private String image;
    private String url;
    private String date;
    private String description;

    public NewsTable(String author, String content, String image, String title, String url, String date, String description) {
        this.author = author;
        this.content = content;
        this.image = image;
        this.title = title;
        this.url = url;
        this.date = date;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
