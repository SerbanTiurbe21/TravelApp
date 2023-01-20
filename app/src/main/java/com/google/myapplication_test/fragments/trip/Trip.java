package com.google.myapplication_test.fragments.trip;

import java.util.Objects;

public class Trip {
    private String tripName;
    private String destination;
    private String imageUrl;
    private float price;
    private float rating;
    private boolean isBookmarked;
    private String email;

    public Trip(String tripName, String destination, String imageUrl, float price, float rating, boolean isBookmarked, String email) {
        this.tripName = tripName;
        this.destination = destination;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;
        this.isBookmarked = isBookmarked;
        this.email = email;
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

    public String getEmail() {
        return email;
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

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(tripName, trip.tripName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripName);
    }
}
