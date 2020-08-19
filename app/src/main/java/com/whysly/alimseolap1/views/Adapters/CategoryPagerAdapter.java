package com.whysly.alimseolap1.views.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> items = new ArrayList<Fragment>();
    private String[] tabTitles = new String[]{"전체", "쇼핑", "게임", "컨텐츠", "기타"};

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment item){
        items.add(item);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
