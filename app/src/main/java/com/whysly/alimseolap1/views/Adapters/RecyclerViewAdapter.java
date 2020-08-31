package com.whysly.alimseolap1.views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.OpenApp;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Activity activity;
    Context context;

    int lastPosition;
    private List<NotificationEntity> entities = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public int getItemCount() {
        Log.d("준영_갱신", "getItemCount: 실행됨");
        return entities.size();
        //return notiData.size();
    }

    @Override
    public long getItemId(int position) {
        //NotiData data = notiData.get(position);
        //return data.getNoti_id();
        return  entities.get(position).id;
    }

    public void setEntities(List<NotificationEntity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
        notifyItemInserted(entities.size());

    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);        return viewHolder;

  /*    else if(false) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_date_item, parent, false);
            DateViewHolder dateViewHolder = new DateViewHolder(view);        return dateViewHolder;
        }*/
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        setAnimation(holder.itemView, position);
        if(getItemCount() == 0){
            Log.d("준영", "앱 리스트의 사이즈가 0입니다.");
            return;
        }
        //entities.get(position).this_user_real_evaluation

        Log.d("준영", "앱 리스트의 사이즈는 " + getItemCount() + "입니다.");
        //NotiData data = notiData.get(position);
        NotificationEntity data = entities.get(position);

        // 데이터 결합
//      holder.notiTitle.setText(data.getNotiTitle());
        Log.d("준영", position + " 번째 알림의 extra_Title은 " + data.title + " 입니다.");
        holder.notiText.setText(data.content);
        Log.d("준영", position + " 번째 알림의 extra_text은 " + data.pakage_name + " 입니다.");
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

        holder.noti_date.setText(data.arrive_time.toString());
        Log.d("준영", position + " 번째 알림의 noti_date은 " + data.arrive_time.toString() + " 입니다.");

        try{
            Drawable icon = activity.getPackageManager().getApplicationIcon(data.pakage_name);
            holder.icon.setImageDrawable(icon);

        }
        catch (PackageManager.NameNotFoundException e)
        {

        }

        final PackageManager pm = activity.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = activity.getPackageManager().getApplicationInfo(data.pakage_name, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        holder.app_name.setText(applicationName);
//        holder.package_name.setText(data.getPkg_name());
        holder.noti_id.setText(Integer.toString((int) data.id));
        holder.notiTitle.setText(data.title);
        holder.noti_category.setText(data.category);

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.popup);
            animation.setInterpolator(new DecelerateInterpolator());
            if(position < 4) animation.setStartOffset(position * 200);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public void removeItemView(int position) {
        //db에서도 값을 지웁니다.
        Log.d("지웁니다", "noti_idx2: "+position);
        long long_noti_idx = getItemId(position);
        NotificationDatabase  db = NotificationDatabase.getNotificationDatabase(activity);
        entities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, entities.size()); // 지워진 만큼 다시 채워넣기.
    }





    class ViewHolder extends RecyclerView.ViewHolder {
        TextView noti_id;
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
        TextView noti_category;


        public ViewHolder(View itemView) {
            super(itemView);
            notiTitle = (TextView) itemView.findViewById(R.id.notititle);
            notiText = (TextView) itemView.findViewById(R.id.notitext);
            icon = (ImageView) itemView.findViewById(R.id.app_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            noti_id = (TextView) itemView.findViewById(R.id.noti_id);
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
            noti_category = (TextView) itemView.findViewById(R.id.noti_category);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent("intent_redirect");
                    //intent.putExtra("adapterposition", getAdapterPosition());
                    //LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
                    String packageName = entities.get(getAdapterPosition()).pakage_name;
                    OpenApp.openApp(context, packageName);

                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent("remove");
                    intent.putExtra("position", getAdapterPosition());
                    System.out.println("getAdpaterPosition은 " + getAdapterPosition());
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
                    return true;

                }
            });
        }
    }
}
