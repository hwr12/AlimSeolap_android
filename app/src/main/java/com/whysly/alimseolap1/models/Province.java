package com.whysly.alimseolap1.models;

public class Province {
    int id;
    String province;
    public Province(int id, String province) {
        this.id = id;
        this.province = province;
    }



    public int getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
