package com.whysly.alimseolap1.views.Activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.interfaces.MainInterface;

public class NofticationListenerSettings extends BaseActivity implements MainInterface.View {
    int code;

    WindowManager wm;
    android.view.View v;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_guide);

        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));


        WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.TRANSLUCENT);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        v =  inflater.inflate(R.layout.floating_guide, null);
        wm.addView(v, windowManagerParams);




    }

   public void onButtonClick(View view) {
        wm.removeView(v);
    }
}
