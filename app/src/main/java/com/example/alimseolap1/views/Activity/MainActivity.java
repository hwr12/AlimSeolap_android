package com.example.alimseolap1.views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alimseolap1.R;
import com.example.alimseolap1.interfaces.Main.View;
import com.example.alimseolap1.models.NotiData;
import com.example.alimseolap1.presenters.RecyclerViewAdapter;
import com.example.alimseolap1.views.Adapters.ContentsPagerAdapter;
import com.example.alimseolap1.views.Fragment.AllFragment;
import com.example.alimseolap1.views.Fragment.SettingsFragment;
import com.example.alimseolap1.views.Fragment.SortFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = findViewById(R.id.pager);
        //캐싱을 해놓을 프래그먼트 개수
        pager.setOffscreenPageLimit(3);

        //getSupportFragmentManager로 프래그먼트 참조가능
        ContentsPagerAdapter adapter = new ContentsPagerAdapter(getSupportFragmentManager());

        AllFragment allFragment = new AllFragment();
        adapter.addItem(allFragment);

        SortFragment sortFragment = new SortFragment();
        adapter.addItem(sortFragment);

        SettingsFragment settingsFragment = new SettingsFragment();
        adapter.addItem(settingsFragment);

        pager.setAdapter(adapter);

        //initComponent();




 /*      recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        notiData = new ArrayList<>();
        notiData.add(new NotiData(0,null,null,null,null,null,null,null,null, null,null,null,null));

        recyclerViewAdapter = new RecyclerViewAdapter(fragmentTabsStore.getActivity(), notiData);
        recyclerView.setAdapter(recyclerViewAdapter);


  */


        //initToolbar();

    }




    /*private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.blue_grey_600);
    }

     */



    /*private void initComponent() {

        view_pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);




    }

    */

/*
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}










