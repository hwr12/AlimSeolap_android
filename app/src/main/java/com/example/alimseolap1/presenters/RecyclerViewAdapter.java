package com.example.alimseolap1.presenters;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimseolap1.DBHelper;
import com.example.alimseolap1.R;
import com.example.alimseolap1.models.NotiData;

import java.util.List;

import retrofit2.http.HEAD;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private List<NotiData> app;


    public RecyclerViewAdapter(Activity activity, List<NotiData> person) {
        this.activity = activity;
        this.app = person;
        Log.d("준영_갱신", "RecyclerViewAdapter: 실행됨");
    }


    @Override
    public int getItemCount() {
        Log.d("준영_갱신", "getItemCount: 실행됨");
        return app.size();
    }

    @Override
    public long getItemId(int position) {
        NotiData data = app.get(position);
        return data.getNoti_id();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);        return viewHolder;
    }

    // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(getItemCount() == 0){
            Log.d("준영", "앱 리스트의 사이즈가 0입니다.");
            return;
        }

        Log.d("준영", "앱 리스트의 사이즈는 " + getItemCount() + "입니다.");
        NotiData data = app.get(position);

        // 데이터 결합
        holder.notiTitle.setText(data.getNotiTitle());
        Log.d("준영", position + " 번째 알림의 extra_Title은 " + data.getNotiTitle() + " 입니다.");
        holder.notiText.setText(data.getNotiText());
        Log.d("준영", position + " 번째 알림의 extra_text은 " + data.getPkg_name() + " 입니다.");
//        holder.extra_info_text.setText("extra_info_text : " + data.getExtra_info_text());
//        Log.d("준영", position + " 번째 알림의 extra_info_text은 " + data.getExtra_info_text() + " 입니다.");
//        holder.extra_people_list.setText("extra_people_text : " + data.getExtra_people_list());
//        Log.d("준영", position + " 번째 알림의 extra_people_text은 " + data.getExtra_people_list() + " 입니다.");
//        holder.extra_picture.setText("extra_picture : " + data.getExtra_picture());
//        Log.d("준영", position + " 번째 알림의 extra_picture은 " + data.getExtra_picture() + " 입니다.");
//        holder.extra_sub_text.setText(data.getExtra_sub_text());
//        Log.d("준영", position + " 번째 알림의 extra_sub_text은 " + data.getExtra_sub_text() + " 입니다.");
//        holder.extra_summary_text.setText("extra_summary_text : " + data.getExtra_summary_text());
//        Log.d("준영", position + " 번째 알림의 extra_summary_text은 " + data.getExtra_summary_text() + " 입니다.");
//        holder.extra_massage.setText("extra_massage : " + data.getExtra_massage());
//        Log.d("준영", position + " 번째 알림의 extra_massage은 " + data.getExtra_massage() + " 입니다.");
//        holder.group_name.setText("group_name : " + data.getGroup_name());
//        Log.d("준영", position + " 번째 알림의 group_name은 " + data.getGroup_name() + " 입니다.");
//        holder.app_string.setText("app_string : " + data.getApp_string());
//        Log.d("준영", position + " 번째 알림의 app_string은 " + data.getApp_string() + " 입니다.");

        holder.noti_date.setText(data.getNoti_date());
        Log.d("준영", position + " 번째 알림의 noti_date은 " + data.getNoti_date() + " 입니다.");

        try{
            Drawable icon = activity.getPackageManager().getApplicationIcon(data.getPkg_name());
            holder.icon.setImageDrawable(icon);

        }
        catch (PackageManager.NameNotFoundException e)
        {

        }

        final PackageManager pm = activity.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = activity.getPackageManager().getApplicationInfo(data.getPkg_name(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");

        holder.app_name.setText(applicationName);
//        holder.package_name.setText(data.getPkg_name());

    }


    public void removeItemView(int position) {

        //db에서도 값을 지웁니다.
        Log.d("준영", "noti_idx2: "+position);
        long long_noti_idx = getItemId(position);
        DBHelper helper = new DBHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM TestTable WHERE idx = " + long_noti_idx;
        db.execSQL(sql);
        db.close();
        Log.d("준영_삭제", "noti position이  "+position+"인 노티 정보가 삭제되었습니다.");

        app.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, app.size()); // 지워진 만큼 다시 채워넣기.
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView notiTitle;
        TextView notiText;
        TextView app_name;
        ImageView icon;
//        TextView package_name;

//        TextView extra_info_text;
//        TextView extra_people_list;
//        TextView extra_picture;
//        TextView extra_sub_text;
//        TextView extra_summary_text;
//        TextView extra_massage;
//        TextView group_name;
//        TextView app_string;

        TextView noti_date;


        public ViewHolder(View itemView) {
            super(itemView);
            notiTitle = (TextView) itemView.findViewById(R.id.notititle);
            notiText = (TextView) itemView.findViewById(R.id.notitext);
            icon = (ImageView) itemView.findViewById(R.id.app_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
//            package_name = (TextView) itemView.findViewById(R.id.packge_name);

//            extra_info_text = (TextView) itemView.findViewById(R.id.extra_info_text);
//            extra_people_list = (TextView) itemView.findViewById(R.id.extra_people_list);
//            extra_picture = (TextView) itemView.findViewById(R.id.extra_picture);
//            extra_sub_text = (TextView) itemView.findViewById(R.id.extra_sub_text);
//            extra_summary_text = (TextView) itemView.findViewById(R.id.extra_summary_text);
//            extra_massage = (TextView) itemView.findViewById(R.id.extra_massage);
//            group_name = (TextView) itemView.findViewById(R.id.group_name);
//            app_string = (TextView) itemView.findViewById(R.id.app_string);

            noti_date = (TextView) itemView.findViewById(R.id.noti_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "click " +
                            app.get(getAdapterPosition()).getNotiTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(activity, "remove " +
                            app.get(getAdapterPosition()).getNotiTitle(), Toast.LENGTH_SHORT).show();
                    removeItemView(getAdapterPosition());



                    return false;
                }
            });

        }

    }
}