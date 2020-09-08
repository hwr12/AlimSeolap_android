package com.whysly.alimseolap1.views.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.views.Activity.MainActivity;
import com.whysly.alimseolap1.views.Adapters.CategoryPagerAdapter;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment.CategoryAll;
import com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment.CategoryContents;
import com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment.CategoryGame;
import com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment.CategoryOthers;
import com.whysly.alimseolap1.views.Fragment.SortFragment_Sub_Category_Fragment.CategoryShopping;

import java.util.List;

public class SortFragment extends Fragment {
    ViewPager viewPager;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    TabLayout tab_layout;
    EditText editsearch;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SortViewModel viewModel = new ViewModelProvider(requireActivity()).get(SortViewModel.class);

        View view = inflater.inflate(R.layout.sort_fragment, null);




//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
 //       ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        //툴바레이아웃을 연결시킵니다
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_toolbar);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        View viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.custom_toolbar, null);
        EditText searchEditText = viewToolbar.findViewById(R.id.search);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.queryText.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activity.getSupportActionBar().setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));



        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_category);
        tab_layout.getTabAt(0).setText("전체");
        tab_layout.getTabAt(1).setText("쇼핑");
        tab_layout.getTabAt(2).setText("게임");
        tab_layout.getTabAt(3).setText("컨텐츠");
        tab_layout.getTabAt(4).setText("기타");




        ViewPager pager = view.findViewById(R.id.category_pager);


        //캐싱을 해놓을 프래그먼트 개수
        pager.setOffscreenPageLimit(5);

        //getSupportFragmentManager로 프래그먼트 참조가능
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getFragmentManager());

//        CategoryAll categoryAll = new CategoryAll();
//        adapter.addItem(categoryAll);

        CategoryAll categoryAll = new CategoryAll();
        adapter.addItem(categoryAll);

        CategoryShopping categoryShopping = new CategoryShopping();
        adapter.addItem(categoryShopping);

        CategoryGame categoryGame = new CategoryGame();
        adapter.addItem(categoryGame);

        CategoryContents categoryContents = new CategoryContents();
        adapter.addItem(categoryContents);

        CategoryOthers categoryOthers = new CategoryOthers();
        adapter.addItem(categoryOthers);

        pager.setAdapter(adapter);

        //뷰페이저와 탭레이아웃 연동
        tab_layout.setupWithViewPager(pager);






        return view;
    }
}
