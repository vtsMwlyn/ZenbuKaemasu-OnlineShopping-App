package com.example.uasmp;

public class Outlet {
    private String id, name;
    private double latitude, longitude;

    public Outlet(String id, String name, double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
