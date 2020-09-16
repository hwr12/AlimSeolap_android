package com.whysly.alimseolap1.views.Games;

public class GameNotiItem {
    String package_name;
    String app_name;
    String notitext;
    String noti_date;
    String notititle;

    public GameNotiItem(String package_name, String app_name, String notititle, String notitext, String noti_date) {
        this.app_name = app_name;
        this.notititle = notititle;
        this.noti_date = noti_date;
        this.notitext = notitext;
        this.package_name = package_name;

    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getNoti_date() {
        return noti_date;
    }

    public void setNoti_date(String noti_date) {
        this.noti_date = noti_date;
    }

    public String getNotititle() {
        return notititle;
    }

    public void setNotititle(String notititle) {
        this.notititle = notititle;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getName() {
        return app_name;
    }

    public void setName(String app_name) {
        this.app_name = app_name;
    }

    public String getNotitext() {
        return notitext;
    }

    public void setNotitext(String notitext) {
        this.notitext = notitext;
    }
}