package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Bills extends Fragment {


    public Bills() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mBillsView = inflater.inflate(R.layout.fragment_bills, container, false);

        TabLayout mBills_tabLayout = (TabLayout)mBillsView.findViewById(R.id.mbills_tabLayout);

        mBills_tabLayout.addTab(mBills_tabLayout.newTab().setText(R.string.bills_history_string));
        mBills_tabLayout.addTab(mBills_tabLayout.newTab().setText(R.string.bills_pending_string));

        mBills_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mBills_viewPager = (ViewPager)mBillsView.findViewById(R.id.mbills_viewPager);
        final BillsPagerAdapter mBills_pagerAdapter = new BillsPagerAdapter(getActivity().getSupportFragmentManager(), mBills_tabLayout.getTabCount());

        mBills_viewPager.setAdapter(mBills_pagerAdapter);
        mBills_viewPager.setCurrentItem(0);

        mBills_viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mBills_tabLayout)
        );

        mBills_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBills_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mBillsView;
    }

}
