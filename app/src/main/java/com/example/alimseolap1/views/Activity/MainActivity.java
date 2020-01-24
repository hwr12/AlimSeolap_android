package com.example.alimseolap1.views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alimseolap1.DBHelper;
import com.example.alimseolap1.R;
import com.example.alimseolap1.interfaces.Main.View;
import com.example.alimseolap1.interfaces.MyService;
import com.example.alimseolap1.models.NotiData;
import com.example.alimseolap1.presenters.RecyclerViewAdapter;
import com.example.alimseolap1.services.NotiCrawlingService;
import com.example.alimseolap1.views.Adapters.ContentsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    int user_id;
    List<NotiData> app;

    private Context mContext;
    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private ContentsPagerAdapter mContentsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();




        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("추려보기")));

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("모두보기")));

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("세팅")));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);


        mContentsPagerAdapter = new ContentsPagerAdapter(

                getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mContentsPagerAdapter);



        mViewPager.addOnPageChangeListener(

                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {



            @Override

            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

            }



            @Override

            public void onTabUnselected(TabLayout.Tab tab) {



            }



            @Override

            public void onTabReselected(TabLayout.Tab tab) {



            }

        });














        final Switch sw =  findViewById(R.id.switch1);

        //user_id를 등록합니다.
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref.getInt("user_id", 1) == 1){
            SharedPreferences.Editor editor = pref.edit();
            user_id = (int)(Math.random()*10000) + 1;
            Log.d("준영", "user_id: " + user_id);
            editor.putInt("user_id", user_id);
            editor.commit();
        }else{
            user_id = pref.getInt("user_id", 1);
            Log.d("준영", "user_id가 이미 있어서 그냥 가져옵니다." + user_id);
        }

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("준영", "새로고침이 시도되었습니다.");
                app.clear();


                DBHelper helper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                Cursor dbCursor = db.query("TestTable", null, null, null, null, null, null);
                String[] columnNames = dbCursor.getColumnNames();
                Log.d("준영", "columnNames: " + columnNames[0]);
                Log.d("준영", "columnNames: " + columnNames[1]);
                Log.d("준영", "columnNames: " + columnNames[2]);

                String sql = "select * from TestTable order by idx DESC ";

                // 쿼리실행
                Cursor c = db.rawQuery(sql, null);

                // 선택된 로우를 끝까지 반복하며 데이터를 가져온다.
                while(c.moveToNext()){
                    // 가져올 컬럼의 인덱스 번호를 추출한다.
                    int noti_id_pos = c.getColumnIndex("idx");
                    int pkg_name_pos = c.getColumnIndex("pkg_name");
                    int notiTitle_pos = c.getColumnIndex("notiTitle");
                    int notiText_pos = c.getColumnIndex("notiText");
                    int extra_info_text_pos = c.getColumnIndex("extra_info_text");
                    int extra_people_list_pos = c.getColumnIndex("extra_people_list");
                    int extra_picture_pos = c.getColumnIndex("extra_picture");
                    int extra_sub_text_pos = c.getColumnIndex("extra_sub_text");
                    int extra_summary_text_pos = c.getColumnIndex("extra_summary_text");
                    int extra_massage_pos = c.getColumnIndex("extra_massage");
                    int getGroup_pos = c.getColumnIndex("getGroup");
                    int toString_pos = c.getColumnIndex("toString");
                    int noti_date_pos = c.getColumnIndex("noti_date");


                    // 컬럼 인덱스 번호를 통해 데이터를 가져온다.
                    int idx = c.getInt(noti_id_pos);
                    String textData1 = c.getString(pkg_name_pos);
                    String textData2 = c.getString(notiTitle_pos);
                    String textData3 = c.getString(notiText_pos);
                    String textData4 = c.getString(extra_info_text_pos);
                    String textData5 = c.getString(extra_people_list_pos);
                    String textData6 = c.getString(extra_picture_pos);
                    String textData7 = c.getString(extra_sub_text_pos);
                    String textData8 = c.getString(extra_summary_text_pos);
                    String textData9 = c.getString(extra_massage_pos);
                    String textData10 = c.getString(getGroup_pos);
                    String textData11 = c.getString(toString_pos);
                    String textData12 = c.getString(noti_date_pos);

                    app.add(new NotiData(idx, textData1, textData2, textData3, textData4, textData5, textData6, textData7, textData8, textData9, textData10, textData11, textData12));


                }

                db.close();
                recyclerViewAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        if (!isPermissionGranted()) {
            // 접근 혀용이 되어있지 않다면 1. 메시지 발생 / 2, 설정으로 이동시킴
            Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " 앱의 알림 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        Intent service_intent = new Intent(this, NotiCrawlingService.class);

        //서비스가 안돌아가고 있을 때만 서비스를 실행시켜줍니다.
        if(!isServiceRunningCheck()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Log.d("준영", "버전이 오레오 이상이라서 포그라운로 서비스 실행");
                startForegroundService(service_intent);
            } else {
                Log.d("준영", "버전이 오레오 미만이라서 일반 서비스 실행");
                startService(service_intent);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);
        app = new ArrayList<>();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor dbCursor = db.query("TestTable", null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        Log.d("준영", "columnNames: " + columnNames[0]);
        Log.d("준영", "columnNames: " + columnNames[1]);
        Log.d("준영", "columnNames: " + columnNames[2]);

        String sql = "select * from TestTable order by idx DESC ";

        // 쿼리실행
        Cursor c = db.rawQuery(sql, null);

        // 선택된 로우를 끝까지 반복하며 데이터를 가져온다.
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호를 추출한다.
            int noti_id_pos = c.getColumnIndex("idx");
            int pkg_name_pos = c.getColumnIndex("pkg_name");
            int notiTitle_pos = c.getColumnIndex("notiTitle");
            int notiText_pos = c.getColumnIndex("notiText");
            int extra_info_text_pos = c.getColumnIndex("extra_info_text");
            int extra_people_list_pos = c.getColumnIndex("extra_people_list");
            int extra_picture_pos = c.getColumnIndex("extra_picture");
            int extra_sub_text_pos = c.getColumnIndex("extra_sub_text");
            int extra_summary_text_pos = c.getColumnIndex("extra_summary_text");
            int extra_massage_pos = c.getColumnIndex("extra_massage");
            int getGroup_pos = c.getColumnIndex("getGroup");
            int toString_pos = c.getColumnIndex("toString");
            int noti_date_pos = c.getColumnIndex("noti_date");


            // 컬럼 인덱스 번호를 통해 데이터를 가져온다.
            int idx = c.getInt(noti_id_pos);
            String textData1 = c.getString(pkg_name_pos);
            String textData2 = c.getString(notiTitle_pos);
            String textData3 = c.getString(notiText_pos);
            String textData4 = c.getString(extra_info_text_pos);
            String textData5 = c.getString(extra_people_list_pos);
            String textData6 = c.getString(extra_picture_pos);
            String textData7 = c.getString(extra_sub_text_pos);
            String textData8 = c.getString(extra_summary_text_pos);
            String textData9 = c.getString(extra_massage_pos);
            String textData10 = c.getString(getGroup_pos);
            String textData11 = c.getString(toString_pos);
            String textData12 = c.getString(noti_date_pos);

            app.add(new NotiData(idx, textData1, textData2, textData3, textData4, textData5, textData6, textData7, textData8, textData9, textData10, textData11, textData12));


        }

        db.close();

        recyclerViewAdapter = new RecyclerViewAdapter(this, app);
        recyclerView.setAdapter(recyclerViewAdapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);






    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            String evaluate = "none";

            if ( direction == 8) {
                Log.d("준영_방향", "onSwiped: 오른쪽");
                viewHolder.getItemId();
                evaluate = "true";
            }

            else if ( direction == 4) {
                Log.d("준영_방향", "onSwiped: 왼쪽");
                viewHolder.getItemId();
                evaluate = "false";
            }

            // 스와이프 하여 제거하면 밑의 코드가 실행되면서 스와이프 된 뷰홀더의 위치 값을 통해 어댑터에서 아이템이 지워졌다고 노티파이 해줌.
            Log.d("준영", "noti_idx1: "+viewHolder.getAdapterPosition());
            int noti_position = viewHolder.getAdapterPosition();
            Log.d("준영", "noti_idx2: "+noti_position);

            recyclerViewAdapter.removeItemView(noti_position);

            String notitext = "foo";

            // 스와이프와 동시에 스와이프 방향과 스와이프된 뷰홀더의 모든 내용을 서버로 전송
            String notititle = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notititle)).getText().toString();
            notitext = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notitext)).getText().toString();
//            String noti_sub_text = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.extra_sub_text)).getText().toString();
            String app_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.app_name)).getText().toString();
//            String package_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.packge_name)).getText().toString();

            String noti_date1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_date)).getText().toString();
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String noti_date2 = format1.format(time);
            Log.d("준영", "noti_date1 : " + noti_date1 + " vs noti_date2 : " + noti_date2);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gonglee.pythonanywhere.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("현우", "Retrofit 빌드 성공");


            MyService service = retrofit.create(MyService.class);
            //json 객체 생성하여 값을 넣어줌
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("app_name", app_name);
            jsonObject.addProperty("package_name", "X");
            jsonObject.addProperty("title", notititle);
            jsonObject.addProperty("content", notitext);
            jsonObject.addProperty("subContent", "X");
            jsonObject.addProperty("noti_date", noti_date1);
            jsonObject.addProperty("user_id", user_id);
            jsonObject.addProperty("user_value", evaluate);


            Call<JsonObject> call = service.createPost(jsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    System.out.println("알림데이터 전송성공");
                    Log.d("현우", response.toString());
                    Log.d("현우", retrofit.toString());



                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("알림데이터 전송실패");
                    Log.d("현우", t.toString());


                }
            });


        }
    };

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.noticrawler.NotiCrawlingService".equals(service.service.getClassName())) {
                Log.d("준영_서비스", "NotiCrawlingService가 돌아가고 있습니다 ");
                return true;
            }
        }
        Log.d("준영_서비스", "NotiCrawlingService가 안돌아가고 있습니다 ");
        return false;
    }



    private boolean isPermissionGranted() {
        Log.d("준영", "isPermissionGranted: 퍼미션을 체크합니다.");
        // 노티수신을 확인하는 권한을 가진 앱 모든 리스트
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(this);
        //  Notify앱의 알림 접근 허용이 되어있는가?
        return sets != null && sets.contains(getPackageName());
    }

    private android.view.View createTabView(String tabName) {

        android.view.View tabView = (android.view.View) LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);

        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);

        txt_name.setText(tabName);

        return tabView;

    }




}

