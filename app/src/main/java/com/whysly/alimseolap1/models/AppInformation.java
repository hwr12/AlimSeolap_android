package com.whysly.alimseolap1.models;

public class AppInformation {

    private String app_name;
    private String app_pkg_name;
    private int isCrawled;

    public AppInformation(String app_name, String app_pkg_name, int isCrawled) {
        this.app_name = app_name;
        this.app_pkg_name = app_pkg_name;
        this.isCrawled = isCrawled;
    }

    public String getApp_name() {return app_name;}
    public String getApp_pkg_name() {return app_pkg_name;}
    public int getIsCrawled() {return isCrawled;}
}
