package com.mc.group3.electricbillmanage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BillsPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumTabs;

    public BillsPagerAdapter(FragmentManager fm, int mNumTabs){
        super(fm);
        this.mNumTabs = mNumTabs;
    }

    public Fragment getItem(int position) {
        System.out.println("+++++ "  + position);
        switch (position){
            case 0:
                return new Bills_History();

            case 1:
                return new Bills_Pending();

            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumTabs;
    }
}
