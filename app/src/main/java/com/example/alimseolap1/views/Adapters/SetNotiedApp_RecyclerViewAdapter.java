package com.example.alimseolap1.views.Adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimseolap1.DBHelper;
import com.example.alimseolap1.R;
import com.example.alimseolap1.models.AppInfomation;
import com.example.alimseolap1.models.databases.AppDatabase;
import com.example.alimseolap1.models.databases.NotificationDatabase;
import com.example.alimseolap1.models.entities.AppEntity;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.List;

public class SetNotiedApp_RecyclerViewAdapter extends RecyclerView.Adapter<SetNotiedApp_RecyclerViewAdapter.ViewHolder> {


    private DBHelper helper;
    List<AppInfomation> appInfos;
    AppInfomation appInfomation;
    Context context;
    AppDatabase ad;



    public SetNotiedApp_RecyclerViewAdapter(List<AppInfomation> appInfos, Context context) {
        this.appInfos = appInfos;
        helper = new DBHelper(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("준영", "onCreateViewHolder: 진입");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seted_app, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        appInfomation = appInfos.get(position);
        String app_name = appInfomation.getApp_name();
        holder.textView.setText(app_name);
        //holder.textView2 = appInfomation.getApp_pkg_name();
        holder.pkg_name.setText(appInfomation.getApp_pkg_name());
        ad = AppDatabase.getAppDatabase(context);
        ad.appDao().searchNotApp(appInfomation.getApp_pkg_name());
        System.out.println(appInfomation.getApp_pkg_name() + "스위치는? " + holder.aSwitch.isChecked() + "  " + ad.appDao().searchNotApp(appInfomation.getApp_pkg_name()) + "->얘는 1이면 안받는거");


/*
        SQLiteDatabase ReadDb = helper.getReadableDatabase();
        Log.d("준영_앱 아이템 생성", "패키지 명 :" + holder.pkg_name);
        String sql = "select * from NoCrawling where app_name = '" + holder.pkg_name + "'";
        Cursor c = ReadDb.rawQuery(sql, null);

 */
        //패키지네임으로 앱데이터베이스에 접속해서 1(크롤할경우) true로 바꿔줍니다.


        if(ad.appDao().searchNotApp(appInfomation.getApp_pkg_name()) == 1){
            holder.aSwitch.setChecked(false);
        }else{
            holder.aSwitch.setChecked(true);
        }



        try{
            Drawable icon = context.getPackageManager().getApplicationIcon(appInfomation.getApp_pkg_name());
            holder.icon.setImageDrawable(icon);
        }
        catch (PackageManager.NameNotFoundException e)
        {

        }

//        while(c.moveToNext()){
//            NoCrawling.add(c.getString(c.getColumnIndex("app_name")));
//        }
//        ReadDb.close();

//
//        //관리 되지 않는 앱은 체크 미상태, 관리 되고 있는 앱은 체크 상태입니다.
//        if(NoCrawling.contains(app_name)){
//            holder.aSwitch.setChecked(false);
//        }else{
//            holder.aSwitch.setChecked(true);
//        }
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Switch aSwitch;
        ImageView icon;
        TextView pkg_name;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("준영", "ViewHolder.ViewHolder: 진입");
            textView = (TextView) itemView.findViewById(R.id.textView3);
            aSwitch = (Switch) itemView.findViewById(R.id.switch1);
            icon = (ImageView) itemView.findViewById(R.id.app_icon);
            pkg_name = (TextView) itemView.findViewById(R.id.pkg_name);
            pkg_name.getText().toString();
            String textView2 = pkg_name.getText().toString();
            System.out.println("textView2는 " + textView2);

            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d("준영", "체크 이벤트가 감지되었습니다. ");
                    changeNoCrawlingApp(pkg_name.getText().toString());
                    System.out.println(pkg_name.getText().toString());
                }
            });
        }

        private void changeNoCrawlingApp(String pkg_name) {
            ad = AppDatabase.getAppDatabase(context);
            SQLiteDatabase WriteDb = helper.getWritableDatabase();

            System.out.println(pkg_name);

            if(aSwitch.isChecked() == true){
                aSwitch.setChecked(true);
                ad.appDao().updateApp(pkg_name, 1);
                System.out.println("searchnotapp값은(1일 경우 크롤안함) " + ad.appDao().searchNotApp(pkg_name) + pkg_name);
                System.out.println("searchnotapp값은(1일 경우 크롤안함) " + ad.appDao().number_of_apps() + pkg_name);

                String sql = "delete from NoCrawling where app_name = '" +pkg_name +"'";
                Log.d("tmp", "onCheckedChanged: " +textView.getText() +" 가 관리되기 시작합니다.");
                WriteDb.execSQL(sql);
                WriteDb.close();

            }else if(aSwitch.isChecked() == false){
                aSwitch.setChecked(false);
                ad.appDao().updateApp(pkg_name, 0);
                System.out.println("searchnotapp값은(1일 경우 크롤안함) " + ad.appDao().searchNotApp(pkg_name) + pkg_name);
                String sql = "insert into NoCrawling (app_name) values ('" + pkg_name +"')";
                Log.d("tmp", "onCheckedChanged: " +textView.getText() +" 가 관리 대상에서 빠집니다.");
                WriteDb.execSQL(sql);
                WriteDb.close();
            }
        }

    }
}
