/*
package com.example.mapme;

import java.io.Serializable;
import java.util.Locale;

public class Employee implements Serializable {
    private String name, latitude, longitude; private Integer id, location;

    public Employee(Integer id, String name, Integer location, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Override
    public String toString(){
        return String.format(Locale.getDefault(),"[%d, %s, %d, %s, %s]",
                getId(),getName(),getLocation(), getLatitude(), getLongitude());
    }
}
*/
