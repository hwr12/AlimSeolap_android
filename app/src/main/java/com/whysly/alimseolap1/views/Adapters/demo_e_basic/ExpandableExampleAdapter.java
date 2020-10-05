/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.whysly.alimseolap1.views.Adapters.demo_e_basic;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Adapters.common.data.AbstractExpandableDataProvider;
import com.whysly.alimseolap1.views.Adapters.common.widget.ExpandableItemIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpandableExampleAdapter
        extends AbstractExpandableItemAdapter<ExpandableExampleAdapter.MyGroupViewHolder, ExpandableExampleAdapter.MyChildViewHolder> {
    private static final String TAG = "MyExpandableItemAdapter";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd일 hh시 mm분");

    // NOTE: Make accessible with short name
    private List<NotificationEntity> entities = new ArrayList<>();
    private Context context;

    public static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public FrameLayout mContainer;
        public TextView mTextView;


        public MyBaseViewHolder(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mTextView = v.findViewById(android.R.id.text1);
        }
    }

    public void setEntities(List<NotificationEntity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
        notifyItemInserted(entities.size() - 1);
    }

    public static class MyGroupViewHolder extends MyBaseViewHolder {
        public ExpandableItemIndicator mIndicator;

        public MyGroupViewHolder(View v) {
            super(v);
            mIndicator = v.findViewById(R.id.indicator);
        }
    }

    public static class MyChildViewHolder extends MyBaseViewHolder {
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

        public MyChildViewHolder(View v) {
            super(v);
            noti_id = v.findViewById(R.id.noti_id);
            notiTitle = v.findViewById(R.id.notititle);
            notiText = v.findViewById(R.id.notitext);
            app_name = v.findViewById(R.id.app_name);
            icon = v.findViewById(R.id.app_icon);
            noti_date = v.findViewById(R.id.noti_date);
            noti_category = v.findViewById(R.id.noti_category);
        }
    }

    public ExpandableExampleAdapter() {

        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return entities.stream()
                .map(e -> simpleDateFormat.format(e.arrive_time))
                .distinct()
                .collect(Collectors.toList())
                .size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        String date = entities.stream()
                .map(e -> simpleDateFormat.format(e.arrive_time))
                .distinct()
                .collect(Collectors.toList()).get(groupPosition);

        return (int) entities.stream()
                .filter(e -> simpleDateFormat.format(e.arrive_time).equals(date))
                .count();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 1000 + childPosition;
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    @NonNull
    public MyGroupViewHolder onCreateGroupViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_group_item, parent, false);
        context = parent.getContext();
        return new MyGroupViewHolder(v);
    }

    @Override
    @NonNull
    public MyChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MyChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(@NonNull MyGroupViewHolder holder, int groupPosition, int viewType) {
        String title = entities.stream()
                .map(e -> simpleDateFormat.format(e.arrive_time))
                .sorted(Comparator.reverseOrder())
                .distinct()
                .collect(Collectors.toList())
                .get(groupPosition);

        // child item
        final AbstractExpandableDataProvider.BaseData item = new AbstractExpandableDataProvider.BaseData() {
            @Override
            public String getText() {
                return title;
            }

            @Override
            public void setPinned(boolean pinned) {

            }

            @Override
            public boolean isPinned() {
                return false;
            }
        };


        // set text
        holder.mTextView.setText(item.getText());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final ExpandableItemState expandState = holder.getExpandState();

        if (expandState.isUpdated()) {
            int bgResId;
            boolean animateIndicator = expandState.hasExpandedStateChanged();

            if (expandState.isExpanded()) {
                bgResId = R.drawable.bg_group_item_expanded_state;
            } else {
                bgResId = R.drawable.bg_group_item_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
            holder.mIndicator.setExpandedState(expandState.isExpanded(), animateIndicator);
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        // group item
        final AbstractExpandableDataProvider.ChildData item = new AbstractExpandableDataProvider.ChildData() {
            @Override
            public long getChildId() {
                return 0;
            }

            @Override
            public String getText() {
                return "null";
            }

            @Override
            public void setPinned(boolean pinned) {

            }

            @Override
            public boolean isPinned() {
                return false;
            }
        };

        //entities.get(position).this_user_real_evaluation

        Log.d("준영", "앱 리스트의 사이즈는 " + getItemCount() + "입니다.");
        //NotiData data = notiData.get(position);

        String date = entities.stream()
                .map(e -> simpleDateFormat.format(e.arrive_time))
                .sorted(Comparator.reverseOrder())
                .distinct()
                .collect(Collectors.toList()).get(groupPosition);

        NotificationEntity data = entities.stream()
                .filter(e -> simpleDateFormat.format(e.arrive_time).equals(date))
                .sorted((o1, o2) -> o1.arrive_time.compareTo(o2.arrive_time) * -1)
                .collect(Collectors.toList()).get(childPosition);



        // 데이터 결합
//      holder.notiTitle.setText(data.getNotiTitle());
            Log.d("준영", childPosition + " 번째 알림의 extra_Title은 " + data.title + " 입니다.");
        holder.notiText.setText(data.content);
        Log.d("준영", childPosition + " 번째 알림의 extra_text은 " + data.pakage_name + " 입니다.");
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

        holder.noti_date.setText(simpleDateFormat2.format(data.arrive_time));
        Log.d("준영", childPosition + " 번째 알림의 noti_date은 " + data.arrive_time.toString() + " 입니다.");

        try{
            Drawable icon = context.getPackageManager().getApplicationIcon(data.pakage_name);
            holder.icon.setImageDrawable(icon);

        }
        catch (PackageManager.NameNotFoundException e)
        {

        }

        final PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = context.getPackageManager().getApplicationInfo(data.pakage_name, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        holder.app_name.setText(applicationName);
//        holder.package_name.setText(data.getPkg_name());
        holder.noti_id.setText(Integer.toString((int) data.id));
        holder.notiTitle.setText(data.title);
        holder.noti_category.setText(data.category);


        // set background resource (target view ID: container)
        int bgResId;
        bgResId = R.drawable.bg_item_normal_state;
//        holder.mContainer.setBackgroundResource(bgResId);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check the item is *not* pinned
//        if (mProvider.getGroupItem(groupPosition).isPinned()) {
//            // return false to raise View.OnClickListener#onClick() event
//            return false;
//        }
//
//        // check is enabled
//        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
//            return false;
//        }

        return true;
    }

    public void updateList(List<NotificationEntity> items) {
        this.entities = items;
        notifyDataSetChanged();
    }
}
