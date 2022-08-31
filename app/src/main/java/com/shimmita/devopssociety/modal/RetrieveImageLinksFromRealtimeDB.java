package com.shimmita.devopssociety.modal;

public class RetrieveImageLinksFromRealtimeDB {
    private String imageUrl;

    public RetrieveImageLinksFromRealtimeDB() {
    }

    public RetrieveImageLinksFromRealtimeDB(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
