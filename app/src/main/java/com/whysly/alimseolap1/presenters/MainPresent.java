package com.whysly.alimseolap1.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.interfaces.MainInterface;
import com.whysly.alimseolap1.interfaces.MainInterface.Presenter;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.databases.WordDatabase;

import java.util.Set;

public class MainPresent implements Presenter {

    MainInterface.View mainActivity;
    Context context;
    WordDatabase wordDatabase;
    NotificationDatabase notificationDatabase;

    public MainPresent(MainInterface.View view, Context context){
        this.mainActivity = view;
        this.context = context;
    }

    @Override
    public void permissionCheck(){
        Log.d("준영", "isPermissionGranted: 퍼미션을 체크합니다.");
        // 노티수신을 확인하는 권한을 가진 앱 모든 리스트
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(context);
        //  Notify앱의 알림 접근 허용이 되어있는가?
        if(!(sets != null && sets.contains(context.getPackageName()))){
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.app_name) + " 앱의 알림 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }




}