package com.whysly.alimseolap1.views.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.whysly.alimseolap1.views.Fragment.NegFragment;
import com.whysly.alimseolap1.views.Fragment.PosFragment;


/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class SmooliderAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SmooliderAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PosFragment tab1 = new PosFragment();
                return tab1;
            case 1:
                NegFragment tab2 = new NegFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}