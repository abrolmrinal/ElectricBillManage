package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Usage extends Fragment {


    public Dashboard_Usage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_usage, container, false);
    }

    /*
    tabDigit1.start();
    ViewCompat.postOnAnimationDelayed(tabDigit1, this, 1000);

    @Override
    public void run() {
        tabDigit1.start();
        ViewCompat.postOnAnimationDelayed(tabDigit1, this, 1000);
     */

}
