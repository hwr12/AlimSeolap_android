package com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.AsyncTask.N_PreloadAsyncTask;
import com.whysly.alimseolap1.AsyncTask.N_UpdateAsyncTask;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryContents extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    NotificationEntity noti;
    NotificationDatabase db;
    SimpleDateFormat format1;
    Date time;
    String arrived_time;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_category_contents, null);


        recyclerView = view.findViewById(R.id.recycler_contents);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_update,
                new IntentFilter("Update"));




        notiData = new ArrayList<>();
        N_PreloadAsyncTask NPreloadAsyncTask = new N_PreloadAsyncTask(getActivity(), recyclerView, notiData);
        NPreloadAsyncTask.execute();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setAdapter(new RecyclerViewAdapter( getActivity(), notiData));




        return view;
    }



    private BroadcastReceiver mBroadcastReceiver_update = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("AllFragment", "쇼핑_업데이트 브로드캐스트 수신");
            updateRecyclerView();
        }
    };

    public void updateRecyclerView() {
        N_UpdateAsyncTask NUpdateAsyncTask = new N_UpdateAsyncTask(getActivity(), recyclerView, notiData);
        NUpdateAsyncTask.execute();
    }


}
