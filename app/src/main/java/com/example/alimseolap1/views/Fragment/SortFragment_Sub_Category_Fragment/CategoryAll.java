package com.example.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alimseolap1.R;
import com.example.alimseolap1.models.NotiData;
import com.example.alimseolap1.views.Activity.MainActivity;
import com.example.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryAll extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_category_all, null);


        recyclerView = view.findViewById(R.id.recycler1);

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
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
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