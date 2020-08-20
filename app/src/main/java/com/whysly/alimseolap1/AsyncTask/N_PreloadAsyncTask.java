package com.whysly.alimseolap1.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class N_PreloadAsyncTask extends AsyncTask<Integer, Long, List<NotiData>> {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewAdapter rva;
    List<NotiData> notiData;
    List<NotiData> notidata;
    LinearLayoutManager linearLayoutManager;
    int user_id;
    String notititle;
    NotificationEntity noti;
    NotificationDatabase db;
    SimpleDateFormat format1;
    Date time;
    Intent intent_redirect;
    String pendingIntent;
    Intent intent1;
    String arrived_time;
    Context mContext;
    Activity mActivity;
    RecyclerView mRecyclerView;

    public N_PreloadAsyncTask(Activity activity, RecyclerView recyclerView, List<NotiData> notiData) {
       this.mActivity = activity;
       this.mRecyclerView = recyclerView;
       this.notiData = notiData;
    }






    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<NotiData> doInBackground(Integer... integers) {

        db = NotificationDatabase.getNotificationDatabase(mActivity);
        Log.i("현우", "노티총개수는" + db.notificationDao().number_of_notification() + "입니다");
        System.out.println(db.notificationDao().number_of_notification());
        time = new Date();
        format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (int i = 1 ; i <= db.notificationDao().number_of_notification() ; i++ ){
            //사용자 실제평가(real user evaluation)이 안내려진 경우에만 리사이클러로 넣어줍니다.
            noti = db.notificationDao().loadNotification(i);
            arrived_time = format1.format(noti.arrive_time);
            if (noti.this_user_real_evaluation == 0) {

                notiData.add(new NotiData((int) noti.id, noti.pakage_name, noti.title, noti.content, "null", "null", "null", "null", "null", "null", "null", noti.app_name, arrived_time));
            }
            else {

            }
        }

        return notiData;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<NotiData> notiData) {
        super.onPostExecute(notiData);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity.getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewAdapter( mActivity, notiData));
    }


}
