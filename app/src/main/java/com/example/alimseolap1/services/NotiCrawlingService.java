package com.example.alimseolap1.services;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.example.alimseolap1.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotiCrawlingService extends NotificationListenerService {


    public NotiCrawlingService() {

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            NotificationChannel channel = new NotificationChannel("test1", "MyService", NotificationManager.IMPORTANCE_HIGH);
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(true);
//            manager.createNotificationChannel(channel);
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test1");
//            builder.setSmallIcon(android.R.drawable.ic_menu_search);
//            builder.setContentTitle("서비스 가동");
//            builder.setContentText("서비스가 가동 중입니다");
//            builder.setAutoCancel(true);
//            Notification notification = builder.build();
//            // 현재 노티피케이션 메시즈를 포그라운드 서비스의 메시지로 등록한다.
//            startForeground(10, notification);
//        }

        Log.d("준영_서비스", "NotiCrawlingService: 실행됨");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("준영_서비스", "서비스가 종료됩니다.");
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);



        Log.d("준영", "onNotificationPosted: 실행됨");

        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        String pkg_name = sbn.getPackageName();
        long post_time = sbn.getPostTime();
        Log.d("준영", "onNotificationPosted: 포스트 시간" + post_time);

        //제목과 내용중 하나가 null이면 걸러집니다.
        if(extras.getString(notification.EXTRA_TITLE) == null || extras.getString(notification.EXTRA_TEXT) == null){
            Log.d("준영", "제목과 내용중 하나가 null이면 걸러집니다. ");
            return;
        }

        //기본적으로 거를 앱
        ArrayList<String> not_crawled_pkg = new ArrayList<String>(Arrays.asList(
                "com.android.systemui",
                "com.example.noticrawler",
                "com.android.providers.downloads",
                "com.google.android.setupwizard"
        ));

        ArrayList<String> not_crawled_category = new ArrayList<String>(Arrays.asList(
//                "msg",
                "alarm",
                "call",
                "progress",
                "sys"
        ));

        //사용자가 관리하고 싶지 않은 어플 리스트를 불러와 적용합니다.
        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase ReadDb = helper.getReadableDatabase();
        String sql = "select * from NoCrawling";
        Cursor c = ReadDb.rawQuery(sql, null);
        while(c.moveToNext()){
            not_crawled_pkg.add(c.getString(c.getColumnIndex("app_name")));
        }

        sql = "select * from TestTable order by idx DESC limit 1";
        c = ReadDb.rawQuery(sql, null);
        if(c.moveToFirst()) {
            String pre_data_notiTitle = c.getString((c.getColumnIndex("notiTitle")));
            String pre_data_notiText = c.getString((c.getColumnIndex("notiText")));
            String now_data_notiTitle = extras.getString(notification.EXTRA_TITLE);
            String now_data_notiText = extras.getString(notification.EXTRA_TEXT);
            Log.d("준영_중복", "notiTitle : " + pre_data_notiTitle + "/" + now_data_notiTitle+
                    "\n notiText : " + pre_data_notiText + "/" + now_data_notiText+
                    "\n 이전 데이터를 가져옵니다. ");
            if ((now_data_notiTitle.equals(pre_data_notiTitle)) && (now_data_notiText.equals(pre_data_notiText))) {
                Log.d("준영_중복", "notiTitle : " + pre_data_notiTitle +
                        "\n notiText : " + pre_data_notiText +
                        "\n 중복 알림으로 간주되어 걸러집니다. ");
                return;
            }
        }

        ReadDb.close();




        if(not_crawled_pkg.contains(pkg_name)){
            Log.d("준영", pkg_name + "은 걸러집니다. ");
            return;
        }

        if(pkg_name.contains("android")){
            Log.d("준영", pkg_name + "은 걸러집니다. ");
            return;
        }

        if(not_crawled_category.contains(notification.category)){
            Log.d("준영", "알림의 카테고리가 " + notification.category + "면 걸러집니다.");
            return;
        }


        Log.d("현우", "DB 저장 스레드 실행됨");
        ThreadClass thread = new ThreadClass(extras, notification, pkg_name);
        thread.start();

    }



    class ThreadClass extends Thread{
        Bundle extras;
        Notification notification;
        String pkg_name;

        public ThreadClass(Bundle extras, Notification notification, String pkg_name){
            this.extras = extras;
            this.notification = notification;
            this.pkg_name = pkg_name;
        }

        public void run(){
            List<String> field_value = new ArrayList<String>();

            if (pkg_name != null) {
                field_value.add(pkg_name);
            }else { field_value.add("None");}

            if (notification.EXTRA_TITLE != null) {
                field_value.add(extras.getString(notification.EXTRA_TITLE));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_TITLE는: " + extras.getString(notification.EXTRA_TITLE));
            }else { field_value.add("None");}

            if (notification.EXTRA_TEXT != null) {
                field_value.add(extras.getString(notification.EXTRA_TEXT));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_TEXT는: " + extras.getString(notification.EXTRA_TEXT));
            }else { field_value.add("None");}

            if (notification.EXTRA_INFO_TEXT != null) {
                field_value.add(extras.getString(notification.EXTRA_INFO_TEXT));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_INFO_TEXT는: " + extras.getString(notification.EXTRA_INFO_TEXT));
                Log.d("준영", " EXTRA_INFO_TEXT는: " + notification.EXTRA_INFO_TEXT);
            }else { field_value.add("None");}

            if (notification.EXTRA_PEOPLE_LIST != null) {
                field_value.add(extras.getString(notification.EXTRA_PEOPLE_LIST));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_PEOPLE_LIST는: " + extras.getString(notification.EXTRA_PEOPLE_LIST));
                Log.d("준영", " EXTRA_PEOPLE_LIST는: " + notification.EXTRA_PEOPLE_LIST);
            }else { field_value.add("None");}

            if (notification.EXTRA_PICTURE != null) {
                field_value.add(extras.getString(notification.EXTRA_PICTURE));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_PICTURE는: " + extras.getString(notification.EXTRA_PICTURE));
                Log.d("준영", " EXTRA_PICTURE는: " + notification.EXTRA_PICTURE);
            }else { field_value.add("None");}

            if (notification.EXTRA_SUB_TEXT != null) {
                field_value.add(extras.getString(notification.EXTRA_SUB_TEXT));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_SUB_TEXT: " + extras.getString(notification.EXTRA_SUB_TEXT));
                Log.d("준영", " EXTRA_SUB_TEXT: " + notification.EXTRA_SUB_TEXT);
            }else { field_value.add("None");}

            if (notification.EXTRA_SUMMARY_TEXT != null) {
                field_value.add(extras.getString(notification.EXTRA_SUMMARY_TEXT));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_SUMMARY_TEXT: " + extras.getString(notification.EXTRA_SUMMARY_TEXT));
                Log.d("준영", " EXTRA_SUMMARY_TEXT: " + notification.EXTRA_SUMMARY_TEXT);
            }else { field_value.add("None");}

            if (notification.EXTRA_MESSAGES != null) {
                field_value.add(extras.getString(notification.EXTRA_MESSAGES));
                Log.d("준영", " extra.getString으로 변환된 EXTRA_MESSAGES는: " + extras.getString(notification.EXTRA_MESSAGES) );
                Log.d("준영", " EXTRA_MESSAGES는: " + notification.EXTRA_MESSAGES);
            }else { field_value.add("None");}

            if (notification.getGroup() != null) {
                field_value.add(notification.getGroup());
            }else { field_value.add("None");}

            if (notification.toString() != null) {
                field_value.add(notification.toString());
            }else { field_value.add("None");}

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String noti_date = format1.format(time);
            field_value.add(noti_date);


            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();

            String sql = "insert into TestTable (pkg_name, notiTitle, notiText, extra_info_text, extra_people_list, extra_picture, extra_sub_text, extra_summary_text, extra_massage, getGroup, toString, noti_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String[] arg1 = new String[ field_value.size() ];
            field_value.toArray(arg1);

            db.execSQL(sql, arg1);
            db.close();
            Log.d("준영", "디비에 잘 저장되었습니다.");
        }
    }
}