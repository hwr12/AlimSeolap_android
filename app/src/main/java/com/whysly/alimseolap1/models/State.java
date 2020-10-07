package com.whysly.alimseolap1.models;

public class State {
    int id;
    String state;
    public State(int id, String state) {
        this.id = id;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }
}
