package com.rgs.capstone;

class pojo {
    private String author;
    private String content;
    private String image;
    private String title;
    private String url;
    private String date;
    private String description;

    pojo(String author, String content, String image, String title, String url, String date, String description) {
        this.author = author;
        this.content = content;
        this.image = image;
        this.title = title;
        this.url = url;
        this.date = date;
        this.description = description;
    }

    String getAuthor() {
        return author;
    }

    String getContent() {
        return content;
    }

    String getImage() {
        return image;
    }

    String getTitle() {
        return title;
    }

    String getUrl() {
        return url;
    }

    String getDate() {
        return date;
    }

    String getDescription() {
        return description;
    }
}


