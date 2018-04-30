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
        final List<TabDigit> TOTAL_VALUE = new ArrayList<TabDigit>();
        final List<TabDigit> TODAY=new ArrayList<TabDigit>();
        final List<TabDigit> MONTH=new ArrayList<TabDigit>();

        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit13);
        tabDigit.setBackgroundColor(getResources().getColor(R.color.colorWT));
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit14);
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit15);
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit16);
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit17);
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit18);
        tabDigit.elapsedTime(700);
        TOTAL_VALUE.add(tabDigit);

        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit1);
        tabDigit.setBackgroundColor(getResources().getColor(R.color.colorWT));
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit2);
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit3);
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit4);
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit5);
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit6);
        tabDigit.elapsedTime(700);
        TODAY.add(tabDigit);

        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit7);
        tabDigit.setBackgroundColor(getResources().getColor(R.color.colorWT));
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit8);
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit9);
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit10);
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit11);
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);
        tabDigit = (TabDigit) view.findViewById(R.id.tabDigit12);
        tabDigit.elapsedTime(700);
        MONTH.add(tabDigit);

        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
        DatabaseReference myref=fdb.getReference();
        System.out.println("userid="+firebaseUser.getUid());
        /*DatabaseReference week_ref=myref.child("live_reading").child(firebaseUser.getUid()).child("usage_week").getRef();
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
        });*/


        DatabaseReference getCurrUserAddrRef = fdb.getReference();
        getCurrUserAddrRef.child("user_data").child(firebaseUser.getUid()).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.getValue(String.class);

                DatabaseReference total_ref = FirebaseDatabase.getInstance().getReference();
                total_ref.child("live_reading_addr").child(address).child("usage").addValueEventListener(new ValueEventListener() {
                    String prev_total;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String total_val_string = dataSnapshot.getValue(String.class);
                        Float total_val_float = Float.parseFloat(total_val_string);
                        String total_val_round  = Float.toString(Math.round(total_val_float * 10.0f) / 10.0f);
                        total_val_round = total_val_round.replace(".", "");
                        int total_val = Integer.parseInt(total_val_round);
                        if(total_val != -1) {
                            String total_str = Integer.toString(total_val);
                            Log.d("val="+total_str, "Value changed");
                            int len = total_str.length();
                            String temp="";
                            for(int j =len;j<6;j++)
                            {
                                temp+="0";
                            }
                            total_str=temp+total_str;
                            len=total_str.length();
                            if (len <= 6) {
                                for (int i = len - 1; i >= 0; i--) {
                                    if(prev_total!=null && i<prev_total.length())
                                    {
                                        Log.d("curr="+Character.toString(total_str.charAt(i))+" prev="+Character.toString(prev_total.charAt(i)),"Hi");
                                        if(Character.getNumericValue(total_str.charAt(i))!= Character.getNumericValue(prev_total.charAt(i)))
                                        {
                                            int val=Character.getNumericValue(total_str.charAt(i));
                                            if(val==0)
                                            {
                                                TOTAL_VALUE.get(len - 1 - i).setChar(9);

                                            }
                                            else
                                            {
                                                TOTAL_VALUE.get(len - 1 - i).setChar(val - 1);

                                            }
                                            TOTAL_VALUE.get(len - 1 - i).start();
                                        }

                                    }
                                    else {
                                        int val=Character.getNumericValue(total_str.charAt(i));
                                        if(val==0)
                                        {
                                            TOTAL_VALUE.get(len - 1 - i).setChar(9);

                                        }
                                        else
                                        {
                                            TOTAL_VALUE.get(len - 1 - i).setChar(val - 1);

                                        }
                                        TOTAL_VALUE.get(len - 1 - i).start();
                                    }
                                }
                            }
                            prev_total=new String(total_str);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference daily_ref = FirebaseDatabase.getInstance().getReference();
                daily_ref.child("day_reading_addr").child(address).child("usage").addValueEventListener(new ValueEventListener() {
                    String prev_daily;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String daily_val_string = dataSnapshot.getValue(String.class);
                        Float daily_val_float = Float.parseFloat(daily_val_string);
                        String daily_val_round  = Float.toString(Math.round(daily_val_float * 10.0f) / 10.0f);
                        daily_val_round = daily_val_round.replace(".", "");
                        int daily_val = Integer.parseInt(daily_val_round);
                        if(daily_val != -1) {
                            String daily_str = Integer.toString(daily_val);
                            Log.d("val="+daily_str, "Value changed");
                            int len = daily_str.length();
                            String temp="";
                            for(int j =len;j<6;j++)
                            {
                                temp+="0";
                            }
                            daily_str=temp+daily_str;
                            len=daily_str.length();
                            if (len <= 6) {
                                for (int i = len - 1; i >= 0; i--) {
                                    if(prev_daily!=null && i<prev_daily.length())
                                    {
                                        Log.d("curr="+Character.toString(daily_str.charAt(i))+" prev="+Character.toString(prev_daily.charAt(i)),"Hi");
                                        if(Character.getNumericValue(daily_str.charAt(i))!= Character.getNumericValue(prev_daily.charAt(i)))
                                        {
                                            int val=Character.getNumericValue(daily_str.charAt(i));
                                            if(val==0)
                                            {
                                                TODAY.get(len - 1 - i).setChar(9);

                                            }
                                            else
                                            {
                                                TODAY.get(len - 1 - i).setChar(val - 1);

                                            }
                                            TODAY.get(len - 1 - i).start();
                                        }

                                    }
                                    else {
                                        int val=Character.getNumericValue(daily_str.charAt(i));
                                        if(val==0)
                                        {
                                            TODAY.get(len - 1 - i).setChar(9);

                                        }
                                        else
                                        {
                                            TODAY.get(len - 1 - i).setChar(val - 1);

                                        }
                                        TODAY.get(len - 1 - i).start();
                                    }
                                }
                            }
                            prev_daily=new String(daily_str);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference month_ref = FirebaseDatabase.getInstance().getReference();
                month_ref.child("month_reading_addr").child(address).child("usage").addValueEventListener(new ValueEventListener() {
                    String prev_month;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String month_val_string = dataSnapshot.getValue(String.class);
                        Float month_val_float = Float.parseFloat(month_val_string);
                        String month_val_round  = Float.toString(Math.round(month_val_float * 10.0f) / 10.0f);
                        month_val_round = month_val_round.replace(".", "");
                        int week_val = Integer.parseInt(month_val_round);
                        if(week_val != -1) {
                            String month_str = Integer.toString(week_val);
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
