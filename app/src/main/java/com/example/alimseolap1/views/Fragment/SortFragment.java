package com.example.alimseolap1.views.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.alimseolap1.R;
import com.example.alimseolap1.models.NotiData;
import com.example.alimseolap1.views.Activity.MainActivity;
import com.example.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SortFragment extends Fragment  {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    TabLayout tab_layout;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment, null);

        recyclerView = view.findViewById(R.id.recycler1);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
 //       ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        //툴바레이아웃을 연결시킵니다
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_toolbar);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        View viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.custom_toolbar, null);
        activity.getSupportActionBar().setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));



        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_category);


        tab_layout.getTabAt(0).setText("전체");
        tab_layout.getTabAt(1).setText("쇼핑");
        tab_layout.getTabAt(2).setText("게임");
        tab_layout.getTabAt(2).setText("컨텐츠");
        tab_layout.getTabAt(2).setText("기타");


        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        notiData = new ArrayList<>();
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));


        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), notiData);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}
