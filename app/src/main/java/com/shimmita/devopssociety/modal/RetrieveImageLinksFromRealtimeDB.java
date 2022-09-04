package com.shimmita.devopssociety.modal;

public class RetrieveImageLinksFromRealtimeDB {
    private String imagePath;

    public RetrieveImageLinksFromRealtimeDB() {
    }

    public RetrieveImageLinksFromRealtimeDB(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
