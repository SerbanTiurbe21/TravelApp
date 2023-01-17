package com.google.myapplication_test.database;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cities")
public class City {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "userId")
    public String userId;

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
    public float temperature;

    @ColumnInfo(name = "latitude")
    public float latitude;

    @ColumnInfo(name = "longitude")
    public float longitude;

    @ColumnInfo(name = "isFavourite")
    public boolean isFavourite;

    public City(@NonNull String userId, @NonNull String tripName, @NonNull String destination, @NonNull String tripType, @NonNull float price, @NonNull String startDate, @NonNull String endDate, @NonNull float rating, @NonNull String photoUri, float temperature, float latitude,float longitude, boolean isFavourite) {
        this.userId = userId;
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

    public int getId() {
        return id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getTripName() {
        return tripName;
    }

    @NonNull
    public String getDestination() {
        return destination;
    }

    @NonNull
    public String getTripType() {
        return tripType;
    }

    public float getPrice() {
        return price;
    }

    @NonNull
    public String getStartDate() {
        return startDate;
    }

    @NonNull
    public String getEndDate() {
        return endDate;
    }

    public float getRating() {
        return rating;
    }

    @NonNull
    public String getPhotoUri() {
        return photoUri;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
