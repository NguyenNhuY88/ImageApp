package com.example.gallery.model;

import android.graphics.Bitmap;

import java.sql.Date;

public class Photo {
    private String title;
    private String path;
    private String album;
    private Date createdDate;
    private int memoryId;
    private Bitmap thumbnail;

    public Photo() {
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
