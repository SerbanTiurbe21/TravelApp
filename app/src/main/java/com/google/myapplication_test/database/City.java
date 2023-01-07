package com.google.myapplication_test.database;

public class City {
    private int id;
    private String email;
    private String name;
    private String arrivedOn;
    private String leftOn;
    private float temperature;
    private float latitude;
    private float longitude;
    private byte[] image;

    public City(int id, String email, String name, String arrivedOn, String leftOn, float temperature, float latitude, float longitude, byte[] image) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.arrivedOn = arrivedOn;
        this.leftOn = leftOn;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrivedOn() {
        return arrivedOn;
    }

    public void setArrivedOn(String arrivedOn) {
        this.arrivedOn = arrivedOn;
    }

    public String getLeftOn() {
        return leftOn;
    }

    public void setLeftOn(String leftOn) {
        this.leftOn = leftOn;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
