package com.whysly.alimseolap1.models.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Post {


    /*
     {
        "id": 1,
        "app_name": "gmarket",
        "pakage_name": "com.test.gmarket",
        "title": "테스트",
        "content": "테스트입니다.",
        "subContent": "",
        "noti_date": "2019-12-03T15:09:10Z",
        "user_id": 1,
        "user_value": true,
        "user_eval_date": "2019-12-03T17:09:19Z"
    }
     */

    @SerializedName("body")



    private String app_name;
    private String package_name;
    private String title;
    private String subContent;
    private String content;
    private String noti_date;
    private int user_id;
    private boolean user_value;



    public Post( String app_name,String package_name, String title, String subContent, String content, String noti_date, int user_id, boolean user_value){


        this.app_name = app_name;
        this.package_name = package_name;
        this.title = title;
        this.subContent = subContent;
        this.content = content;
        this.noti_date = noti_date;
        this.user_id = user_id;
        this.user_value = user_value;

    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoti_date() {
        return noti_date;
    }

    public void setNoti_date(String noti_date) {
        this.noti_date = noti_date;
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public boolean isUser_value() {
        return user_value;
    }

    public void setUser_value(boolean user_value) {
        this.user_value = user_value;
    }
}
