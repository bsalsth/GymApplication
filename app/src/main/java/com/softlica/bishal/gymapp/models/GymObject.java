package com.softlica.bishal.gymapp.models;

import io.realm.annotations.PrimaryKey;

/**
 * Created by bishal on 3/18/2017.
 */

public class GymObject {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey
    private long id;
    private String name;
    private String website;
    private String address;
    private GymLocation location;

    public GymObject() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GymLocation getLocation() {
        return location;
    }

    public void setLocation(GymLocation location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "GymObject{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                '}';
    }


}
