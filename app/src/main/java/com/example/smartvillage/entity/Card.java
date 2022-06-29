package com.example.smartvillage.entity;

import android.graphics.Bitmap;

import java.io.Serializable;





public class Card implements Serializable {
    private int cid;
    private String image;
    private String title;
    private String author;
    private String content;
    private String shortInfo;
    private String timeStamp;


    public Card(int cid, String image, String title, String author, String content, String shortInfo, String timeStamp) {
        this.cid = cid;
        this.image = image;
        this.title = title;
        this.author = author;
        this.content = content;
        this.shortInfo = shortInfo;
        this.timeStamp = timeStamp;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cid=" + cid +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", shortInfo='" + shortInfo + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
