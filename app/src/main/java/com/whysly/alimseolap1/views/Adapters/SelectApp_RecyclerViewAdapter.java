package com.whysly.alimseolap1.views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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

import com.whysly.alimseolap1.AsyncTask.A_UpdateAsyncTask;
import com.whysly.alimseolap1.DBHelper;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.AppInformation;
import com.whysly.alimseolap1.models.databases.AppDatabase;

import java.util.List;

public class SelectApp_RecyclerViewAdapter extends RecyclerView.Adapter<SelectApp_RecyclerViewAdapter.ViewHolder> {


    private DBHelper helper;
    List<AppInformation> appInfos;
    AppInformation appInformation;
    Context context;
    AppDatabase ad;



    public SelectApp_RecyclerViewAdapter(List<AppInformation> appInfos, Context context) {
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
        appInformation = appInfos.get(position);
        String app_name = appInformation.getApp_name();
        holder.textView.setText(app_name);
        //holder.textView2 = appInfomation.getApp_pkg_name();
        holder.pkg_name.setText(appInformation.getApp_pkg_name());
        System.out.println(appInformation.getApp_pkg_name() + "스위치는? " + holder.aSwitch.isChecked() + " isCrawled 값은" + appInformation.getIsCrawled() + "->얘는 1이면 안받는거");

/*
        SQLiteDatabase ReadDb = helper.getReadableDatabase();
        Log.d("준영_앱 아이템 생성", "패키지 명 :" + holder.pkg_name);
        String sql = "select * from NoCrawling where app_name = '" + holder.pkg_name + "'";
        Cursor c = ReadDb.rawQuery(sql, null);
 */
        ad = AppDatabase.getAppDatabase(context);
        //패키지네임으로 앱데이터베이스에 접속해서 1(크롤할경우) true로 바꿔줍니다.
        int isCrawled = ad.appDao().loadapp(appInformation.getApp_pkg_name()).getIsCrawled();
        if(isCrawled == 1){
            holder.aSwitch.setChecked(true);
            System.out.println("isCrawled 값은 0이므로 switch false로 바꿈");
        }else {
            holder.aSwitch.setChecked(false);
            System.out.println("isCrawled 값은 1이므로 switch true로 바꿈");
        }



        try{
            Drawable icon = context.getPackageManager().getApplicationIcon(appInformation.getApp_pkg_name());
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

    private void changeNoCrawlingApp(String pkg_name, Switch aSwitch) {
        System.out.println(pkg_name);
        if(aSwitch.isChecked()){
            //aSwitch.setChecked(true);
            A_UpdateAsyncTask a_updateAsyncTask = new A_UpdateAsyncTask((Activity) context, pkg_name, 1);
            a_updateAsyncTask.execute();
            System.out.println("true- 알림 받아옴 - isCrawled 1로 바뀜 ");
        }else{
            //aSwitch.setChecked(false);
            A_UpdateAsyncTask a_updateAsyncTask = new A_UpdateAsyncTask((Activity) context, pkg_name, 0);
            a_updateAsyncTask.execute();
            System.out.println("false - 알림 안받아옴 - isCrawled 0으로 바뀜 ");
        }
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
                    changeNoCrawlingApp(pkg_name.getText().toString(), aSwitch);

                    System.out.println(pkg_name.getText().toString());
                }
            });


        }



    }
}
