package com.example.gallery.model;

import java.util.ArrayList;

public class Memory {
    private String name;
    private long date;
    private String place;
    private ArrayList<Photo> photos = new ArrayList<>();

    public Memory(String name, long date, String place) {
        this.name = name;
        this.date = date;
        this.place = place;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public Photo getPhoto(int position){
        return photos.get(position);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
