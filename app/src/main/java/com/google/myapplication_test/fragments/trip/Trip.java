package com.google.myapplication_test.fragments.trip;

import androidx.annotation.NonNull;

public class Trip {
    private final String tripName;
    private final String destination;
    private final String imageUrl;
    private final float price;
    private final float rating;
    private final boolean isBookmarked;

    public Trip(String tripName, String destination, String imageUrl, float price, float rating, boolean isBookmarked) {
        this.tripName = tripName;
        this.destination = destination;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;
        this.isBookmarked = isBookmarked;
    }

    public String getTripName() {
        return tripName;
    }

    public String getDestination() {
        return destination;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", destination='" + destination + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", isBookmarked=" + isBookmarked +
                '}';
    }
}
