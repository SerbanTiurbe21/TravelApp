package com.google.myapplication_test.api.city;

import com.google.gson.annotations.SerializedName;
import com.google.myapplication_test.api.city.Coordinates;

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
