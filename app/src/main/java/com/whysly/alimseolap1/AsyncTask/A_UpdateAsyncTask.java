package com.whysly.alimseolap1.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.models.AppInformation;
import com.whysly.alimseolap1.models.databases.AppDatabase;
import com.whysly.alimseolap1.models.entities.AppEntity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Adapters.SelectApp_RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class A_UpdateAsyncTask extends AsyncTask<Integer, Long, List<AppInformation>> {
    SelectApp_RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewAdapter rva;
    List<AppInformation> appInformation;
    int user_id;
    String notititle;
    AppEntity appEntity;
    AppDatabase ad;
    SimpleDateFormat format1;
    Date time;
    Intent intent_redirect;
    String pendingIntent;
    Intent intent1;
    String arrived_time;
    Context mContext;
    Activity mActivity;
    int isCrawled;
    String pkg_name;
    RecyclerView mRecyclerView;

    public A_UpdateAsyncTask(Activity activity, String pkg_name, int isCrawled) {
        this.mActivity = activity;
        this.pkg_name = pkg_name;
        this.isCrawled = isCrawled;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected  List<AppInformation> doInBackground(Integer... integers) {
        ad = AppDatabase.getAppDatabase(mActivity);
        if (isCrawled == 1) {
            ad.appDao().updateApp(pkg_name, 1);
        }
        else {
            ad.appDao().updateApp(pkg_name, 0);
        }

        return appInformation;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<AppInformation> appInformation) {
        super.onPostExecute(appInformation);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //mRecyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new SelectApp_RecyclerViewAdapter(appInformation, mContext);
        Log.d("AllFragment", "RecyclerviewAdapter의 updateList()메서드 실행시킴");
        recyclerViewAdapter.notifyDataSetChanged();
        //mRecyclerView.setAdapter(recyclerViewAdapter);
    }
}