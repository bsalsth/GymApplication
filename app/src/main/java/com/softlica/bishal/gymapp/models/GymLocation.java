package com.softlica.bishal.gymapp.models;

import io.realm.annotations.PrimaryKey;

/**
 * Created by bishal on 3/19/2017.
 */
public class GymLocation {
    @PrimaryKey
    private int id;
    private double latitude;
    private double longitude;

    public GymLocation() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GymLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}