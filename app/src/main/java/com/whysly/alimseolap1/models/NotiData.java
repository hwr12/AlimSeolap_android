package com.whysly.alimseolap1.models;

public class NotiData {

    String notiTitle;
    String notiText;
    String pkg_name;
    String extra_info_text;
    String extra_people_list;
    String extra_picture;
    String extra_sub_text;
    String extra_summary_text;
    String extra_massage;
    String group_name;
    String app_string;
    String noti_date;
    String pending_intent;
    int noti_id;

    public NotiData(int noti_id, String pkg_name, String notiTitle, String notiText, String extra_info_text, String extra_people_list, String extra_picture, String extra_sub_text, String extra_summary_text, String extra_massage, String group_name, String app_string, String noti_date) {
        this.noti_id = noti_id;
        this.notiTitle = notiTitle;
        this.notiText = notiText;
        this.pkg_name = pkg_name;
        this.extra_info_text = extra_info_text;
        this.extra_people_list = extra_people_list;
        this.extra_picture = extra_picture;
        this.extra_sub_text = extra_sub_text;
        this.extra_summary_text = extra_summary_text;
        this.extra_massage = extra_massage;
        this.group_name = group_name;
        this.app_string = app_string;
        this.noti_date = noti_date;

    }

    public int getNoti_id() { return  noti_id; }
    public String getNotiTitle() {
        return notiTitle;
    }
    public String getNotiText() {
        return notiText;
    }
    public String getPkg_name() {
        return pkg_name;
    }
    public String getExtra_info_text() {
        return extra_info_text;
    }
    public String getExtra_people_list() {
        return extra_people_list;
    }
    public String getExtra_picture() {
        return extra_picture;
    }
    public String getExtra_sub_text() {
        return extra_sub_text;
    }
    public String getExtra_summary_text() { return extra_summary_text; }
    public String getExtra_massage() {
        return extra_massage;
    }
    public String getGroup_name() {
        return group_name;
    }
    public String getApp_string() {
        return app_string;
    }
    public String getNoti_date() {
        return noti_date;
    }




    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }



    public void setNotiText(String notiTitle) {
        this.notiText = notiText;
    }


    public void setPkg_name(String pkg_name) {
        this.pkg_name = pkg_name;
    }

    public void setNoti_id(int noti_id) {
        this.noti_id = noti_id;
    }
}
