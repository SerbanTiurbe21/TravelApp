package com.google.myapplication_test.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cities")
public class City {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public String trip_name;
    @NonNull
    public String destination;
    @NonNull
    public String trip_type;
    @NonNull
    public float price;
    @NonNull
    public String start_date;
    @NonNull
    public String end_date;
    @NonNull
    public float rating;
    @NonNull
    public String photo_uri;
    @NonNull
    public float temperature;
    @NonNull
    public float latitude;
    @NonNull
    public float longitude;
    @NonNull
    public boolean isFavourite;

    public City(@NonNull String trip_name, @NonNull String destination, @NonNull String trip_type, @NonNull float price, @NonNull String start_date, @NonNull String end_date, @NonNull float rating, @NonNull String photo_uri, @NonNull float temperature, @NonNull float latitude, @NonNull float longitude, @NonNull boolean isFavourite) {
        this.trip_name = trip_name;
        this.destination = destination;
        this.trip_type = trip_type;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.rating = rating;
        this.photo_uri = photo_uri;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFavourite = isFavourite;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getTrip_name() {
        return trip_name;
    }

    @NonNull
    public String getDestination() {
        return destination;
    }

    @NonNull
    public String getTrip_type() {
        return trip_type;
    }

    @NonNull
    public float getPrice() {
        return price;
    }

    @NonNull
    public String getStart_date() {
        return start_date;
    }

    @NonNull
    public String getEnd_date() {
        return end_date;
    }

    @NonNull
    public float getRating() {
        return rating;
    }

    @NonNull
    public String getPhoto_uri() {
        return photo_uri;
    }

    @NonNull
    public float getTemperature() {
        return temperature;
    }

    @NonNull
    public float getLatitude() {
        return latitude;
    }

    @NonNull
    public float getLongitude() {
        return longitude;
    }

    @NonNull
    public boolean getIsFavourite(){
        return isFavourite;
    }
}
