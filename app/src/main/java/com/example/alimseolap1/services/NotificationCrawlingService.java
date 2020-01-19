package com.example.alimseolap1.services;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.example.alimseolap1.models.databases.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationCrawlingService extends NotificationListenerService {

    AppDatabase appDatabase;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("준영", "onStartCommand: 동작");
        appDatabase = AppDatabase.getAppDatabase(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.d("준영", "onNotificationPosted: 동작");

        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        String pakage_name = sbn.getPackageName();

        //제목과 내용중 하나가 null이면 걸러집니다.
        if(extras.getString(notification.EXTRA_TITLE) == null || extras.getString(notification.EXTRA_TEXT) == null){
            Log.d("준영", "제목과 내용중 하나가 null이면 걸러집니다. ");
            return;
        }

        //아래의 카테고리에 속하는 알림은 크롤링하지 않습니다.
        ArrayList<String> not_crawled_category = new ArrayList<String>(Arrays.asList(
                "alarm",
                "call",
                "progress",
                "sys"
        ));

        if(not_crawled_category.contains(notification.category)){
            Log.d("준영", "알림의 카테고리가 " + notification.category + "면 걸러집니다.");
            return;
        }



    }

    @Override
    public void onDestroy() {
        Log.d("준영", "onDestroy: 동작");
        super.onDestroy();
    }
}
