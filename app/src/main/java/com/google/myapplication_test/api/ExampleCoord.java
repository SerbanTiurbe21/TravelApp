package com.google.myapplication_test.api;

import com.google.gson.annotations.SerializedName;

public class ExampleCoord {

    @SerializedName("Coordinates")
    Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
