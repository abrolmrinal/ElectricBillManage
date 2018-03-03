package com.mc.group3.electricbillmanage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;



public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Dashboard_Trends();
            case 1:
                return new Dashboard_Usage();
            case 2:
                return new Dashboard_Analysis();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}