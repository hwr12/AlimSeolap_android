package com.whysly.alimseolap1.models;

public class City {
    int id;
    String city;
    public City(int id, String city) {
        this.id = id;
        this.city = city;
    }



    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
