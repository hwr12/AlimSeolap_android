package com.whysly.alimseolap1.views.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.AppInformation;
import com.whysly.alimseolap1.models.databases.AppDatabase;
import com.whysly.alimseolap1.models.entities.AppEntity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Adapters.SelectApp_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectAppFragment extends Fragment {

    ProgressBar progressBar;
    ArrayList<String> apps = new ArrayList<String>();
    ArrayList NoCrawling = new ArrayList();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    AppDatabase ad;
    int isCrawled;
    String TAG = "SelectFragment";
    AppEntity appEntity;


    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_set_notied_app, null);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.set_notied_recyclerview);

        AsyncTaskClass async = new AsyncTaskClass();
        async.execute();

        return view;
    }

    class AsyncTaskClass extends AsyncTask<Integer, Long, List<AppInformation>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<AppInformation> doInBackground(Integer... integers) {

            PackageManager pkgMgr = getActivity().getPackageManager();
            List<ResolveInfo> mApps;
            List<AppInformation> appInfos = new ArrayList<AppInformation>();
            ad = AppDatabase.getAppDatabase(getContext());


            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mApps = pkgMgr.queryIntentActivities(mainIntent, 0); // 실행가능한 Package만 추출.

            Collections.sort(mApps, new ResolveInfo.DisplayNameComparator(pkgMgr));

            for (int i = 0; i < mApps.size(); i++) {
                Boolean existing_app = ad.appDao().existing_app(mApps.get(i).activityInfo.packageName);
                if (existing_app) {
                    //do nothing
                    continue;
                } else {
                    //없으니까 새로 넣어주기!!
                    ad.appDao().insertApp(mApps.get(i).activityInfo.packageName, mApps.get(i).activityInfo.loadLabel(pkgMgr).toString(), 1);
                }
                //appEntity.ex(mApps.get(i).activityInfo.packageName);
            }



            for (int i = 0; i < mApps.size(); i++)

            {
                String app_name = mApps.get(i).activityInfo.loadLabel(pkgMgr).toString();
                String app_pkg_name = mApps.get(i).activityInfo.packageName;
                ad = AppDatabase.getAppDatabase(getActivity());
                appEntity = ad.appDao().loadapp(app_pkg_name);

                Log.d(TAG, app_pkg_name);
                Log.d(TAG, "앱 디비 생성됨");
                Log.d(TAG, appEntity.app_name);
                isCrawled = appEntity.isCrawled;
                System.out.println(isCrawled);//
                //                //isCrawled = mApps.get(i).activityInfo.
                AppInformation appInfo = new AppInformation(app_name, app_pkg_name, isCrawled);

                appInfos.add(appInfo);
            }

            return appInfos;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<AppInformation> appInfos) {
            super.onPostExecute(appInfos);
            progressBar.setVisibility(View.GONE);


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new SelectApp_RecyclerViewAdapter(appInfos, getContext()));


        }
    }
}
