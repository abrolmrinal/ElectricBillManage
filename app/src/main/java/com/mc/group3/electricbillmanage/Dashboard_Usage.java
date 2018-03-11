package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xenione.digit.TabDigit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Usage extends Fragment implements Runnable {

    private FirebaseUser firebaseUser;

    public Dashboard_Usage() {
        // Required empty public constructor
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    TabDigit tabDigit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_usage, container, false);
        final List<TabDigit> WEEK=new ArrayList<TabDigit>();
        final List<TabDigit> MONTH=new ArrayList<TabDigit>();

        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit1);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit2);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit3);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit4);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit5);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit6);
        tabDigit.elapsedTime(300);
        WEEK.add(tabDigit);

        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit7);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit8);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit9);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit10);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit11);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit12);
        tabDigit.elapsedTime(300);
        MONTH.add(tabDigit);

        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
        DatabaseReference myref=fdb.getReference();
        System.out.println("userid="+firebaseUser.getUid());
        DatabaseReference week_ref=myref.child("live_reading").child(firebaseUser.getUid()).child("usage_week").getRef();
        DatabaseReference month_ref=myref.child("live_reading").child(firebaseUser.getUid()).child("usage_month").getRef();

        week_ref.addValueEventListener(new ValueEventListener() {
            String prev_week;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer week_val=dataSnapshot.getValue(Integer.class);
                if(week_val!=null) {
                    String week_str = Integer.toString(week_val);
                    Log.d("val="+week_str, "Value changed");
                    int len = week_str.length();
                    String temp="";
                    for(int j =len;j<6;j++)
                    {
                        temp+="0";
                    }
                    week_str=temp+week_str;
                    len=week_str.length();
                    if (len <= 6) {
                        for (int i = len - 1; i >= 0; i--) {
                            if(prev_week!=null && i<prev_week.length())
                            {
                                Log.d("curr="+Character.toString(week_str.charAt(i))+" prev="+Character.toString(prev_week.charAt(i)),"Hi");
                                if(Character.getNumericValue(week_str.charAt(i))!= Character.getNumericValue(prev_week.charAt(i)))
                                {
                                    int val=Character.getNumericValue(week_str.charAt(i));
                                    if(val==0)
                                    {
                                        WEEK.get(len - 1 - i).setChar(9);

                                    }
                                    else
                                    {
                                        WEEK.get(len - 1 - i).setChar(val - 1);

                                    }
                                    WEEK.get(len - 1 - i).start();
                                }

                            }
                            else {
                                int val=Character.getNumericValue(week_str.charAt(i));
                                if(val==0)
                                {
                                    WEEK.get(len - 1 - i).setChar(9);

                                }
                                else
                                {
                                    WEEK.get(len - 1 - i).setChar(val - 1);

                                }
                                WEEK.get(len - 1 - i).start();
                            }
                        }
                    }
                prev_week=new String(week_str);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        month_ref.addValueEventListener(new ValueEventListener() {
            String prev_month;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer month_val=dataSnapshot.getValue(Integer.class);
                if(month_val!=null) {
                    String month_str = Integer.toString(month_val);
                    Log.d("val="+month_str, "Value changed");
                    int len = month_str.length();
                    String temp="";
                    for(int j =len;j<6;j++)
                    {
                        temp+="0";
                    }
                    month_str=temp+month_str;
                    len=month_str.length();
                    if (len <= 6) {
                        for (int i = len - 1; i >= 0; i--) {
                            if(prev_month!=null && i<prev_month.length())
                            {
                                Log.d("curr="+Character.toString(month_str.charAt(i))+" prev="+Character.toString(prev_month.charAt(i)),"Hi");
                                if(Character.getNumericValue(month_str.charAt(i))!= Character.getNumericValue(prev_month.charAt(i)))
                                {
                                    int val=Character.getNumericValue(month_str.charAt(i));
                                    if(val==0)
                                    {
                                        MONTH.get(len - 1 - i).setChar(9);

                                    }
                                    else
                                    {
                                        MONTH.get(len - 1 - i).setChar(val - 1);

                                    }
                                    MONTH.get(len - 1 - i).start();
                                }

                            }
                            else {
                                int val=Character.getNumericValue(month_str.charAt(i));
                                if(val==0)
                                {
                                    MONTH.get(len - 1 - i).setChar(9);

                                }
                                else
                                {
                                    MONTH.get(len - 1 - i).setChar(val - 1);

                                }
                                MONTH.get(len - 1 - i).start();
                            }
                        }
                    }
                    prev_month=new String(month_str);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

    /*
    tabDigit1.start();
    ViewCompat.postOnAnimationDelayed(tabDigit1, this, 1000);
    */
    @Override
    public void run() {
        //tabDigit.start();
        //ViewCompat.postOnAnimationDelayed(tabDigit, this, 1000);
    }
}
