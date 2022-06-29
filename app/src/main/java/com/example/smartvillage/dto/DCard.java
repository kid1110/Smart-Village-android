package com.example.smartvillage.dto;

import java.io.Serializable;




public class DCard implements Serializable {
    private int cid;
    private String image;
    private String title;
    private String author;
    private String content;
    private String timeStamp;

    public DCard(int cid, String image, String title, String author, String content, String timeStamp) {
        this.cid = cid;
        this.image = image;
        this.title = title;
        this.author = author;
        this.content = content;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "DCard{" +
                "cid=" + cid +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
