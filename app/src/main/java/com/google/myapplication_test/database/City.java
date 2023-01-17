package com.google.myapplication_test.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cities")
public class City {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "tripName")
    @NonNull
    public String tripName;

    @ColumnInfo(name = "destination")
    @NonNull
    public String destination;

    @ColumnInfo(name = "tripType")
    @NonNull
    public String tripType;

    @ColumnInfo(name = "price")
    @NonNull
    public float price;

    @ColumnInfo(name = "startDate")
    @NonNull
    public String startDate;

    @ColumnInfo(name = "endDate")
    @NonNull
    public String endDate;

    @ColumnInfo(name = "rating")
    @NonNull
    public float rating;

    @ColumnInfo(name = "photoUri")
    @NonNull
    public String photoUri;

    @ColumnInfo(name = "temperature")
    @NonNull
    public float temperature;

    @ColumnInfo(name = "latitude")
    @NonNull
    public float latitude;

    @ColumnInfo(name = "longitude")
    @NonNull
    public float longitude;

    @ColumnInfo(name = "isFavourite")
    @NonNull
    public boolean isFavourite;

    public City(@NonNull String tripName, @NonNull String destination, @NonNull String tripType, @NonNull float price, @NonNull String startDate, @NonNull String endDate, @NonNull float rating, @NonNull String photoUri, @NonNull float temperature, @NonNull float latitude, @NonNull float longitude, @NonNull boolean isFavourite) {
        this.tripName = tripName;
        this.destination = destination;
        this.tripType = tripType;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.photoUri = photoUri;
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
        return tripName;
    }

    @NonNull
    public String getDestination() {
        return destination;
    }

    @NonNull
    public String getTrip_type() {
        return tripType;
    }

    @NonNull
    public float getPrice() {
        return price;
    }

    @NonNull
    public String getStart_date() {
        return startDate;
    }

    @NonNull
    public String getEnd_date() {
        return endDate;
    }

    @NonNull
    public float getRating() {
        return rating;
    }

    @NonNull
    public String getPhoto_uri() {
        return photoUri;
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
