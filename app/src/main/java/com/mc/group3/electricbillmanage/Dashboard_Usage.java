package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xenione.digit.TabDigit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Usage extends Fragment implements Runnable {


    public Dashboard_Usage() {
        // Required empty public constructor
    }

    TabDigit tabDigit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_usage, container, false);

        ///*
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit1);
        tabDigit.setChar(1);
        //tabDigit.run();
        ViewCompat.postOnAnimationDelayed(tabDigit, this, 100);

        return view;
    }

    /*
    tabDigit1.start();
    ViewCompat.postOnAnimationDelayed(tabDigit1, this, 1000);
    */
    @Override
    public void run() {
        tabDigit.start();
        ViewCompat.postOnAnimationDelayed(tabDigit, this, 1000);
    }
}
