package com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Activity.MainViewModel;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.util.List;

public class CategoryShopping extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainViewModel model;
    List<NotificationEntity> entities;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_shopping, null);

        recyclerView = view.findViewById(R.id.recycler_shopping);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        model = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(MainViewModel.class);
        model.getAllNotification().observe(this, entities -> recyclerViewAdapter.setEntities(entities));
        model.getDefaultNotifications().observe(this, entities -> recyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount()));

        return view;
    }


}
