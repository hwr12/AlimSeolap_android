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

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryOthers extends Fragment {
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



        View view = inflater.inflate(R.layout.fragment_category_others, null);


        recyclerView = view.findViewById(R.id.recycler_others);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_update,
                new IntentFilter("Update"));


        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        notiData = new ArrayList<>();
        db = NotificationDatabase.getNotificationDatabase(getActivity());
        Log.i("현우", "노티총개수는" + db.notificationDao().number_of_notification() + "입니다");
        System.out.println(db.notificationDao().number_of_notification());
        notiData = new ArrayList<>();
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
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), notiData);
        recyclerView.setAdapter(recyclerViewAdapter);




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
        db = NotificationDatabase.getNotificationDatabase(getContext());
        noti = db.notificationDao().loadNotification(db.notificationDao().number_of_notification());
        notiData.add(new NotiData((int) noti.id, noti.pakage_name, noti.title, noti.content, "null", "null", "null", "null", "null", "null", "null", noti.app_name, noti.arrive_time.toString()));
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), notiData);
        Log.d("AllFragment", "RecyclerviewAdapter의 updateList()메서드 실행시킴");
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
    }


}
