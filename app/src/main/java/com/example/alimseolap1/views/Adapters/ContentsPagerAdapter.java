package com.example.alimseolap1.views.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.alimseolap1.views.Fragment.AllFragment;
import com.example.alimseolap1.views.Fragment.SettingsFragment;
import com.example.alimseolap1.views.Fragment.SortFragment;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;



    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {

        super(fm);

        this.mPageCount = pageCount;

    }



    @Override

    public Fragment getItem(int position) {

        switch (position) {

            case 0:

                AllFragment allFragment = new AllFragment();

                return allFragment;



            case 1:

                SortFragment sortFragment = new SortFragment();

                return sortFragment;



            case 2:

                SettingsFragment settingsFragment = new SettingsFragment();

                return settingsFragment;




            default:

                return null;

        }

    }



    @Override

    public int getCount() {

        return mPageCount;

    }

}