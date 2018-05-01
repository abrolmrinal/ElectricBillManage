package com.mc.group3.electricbillmanage;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



import org.json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Analysis extends Fragment {

    private WebView webview;
    private DatabaseReference mDatabase;
    View view;
    Button time;
    int flag=0;
    List<String> send_data ;
    BarChart barchart;
    BarData barData;
    String curr_addr;

    public Dashboard_Analysis() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean attachToRoot = false;
        view = inflater.inflate(
                R.layout.fragment_dashboard_analysis,
                container,
                attachToRoot);

        time = view.findViewById(R.id.timeline);
        send_data = new ArrayList<>();
        barchart = view.findViewById(R.id.barchat);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0)
                    flag=1;
                else if(flag==1)
                {
                    flag=0;
                    send_data=new ArrayList<>();
                }



                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("meter_reading").child(curr_addr).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String fetch_data = dataSnapshot.getValue().toString();

                        String[] fetch = fetch_data.split(",");
                        Arrays.sort(fetch);
                        if(fetch[(fetch.length)-1].contains("}") || fetch[(fetch.length)-1].contains("{"))
                        {

                            int luv = fetch[(fetch.length)-1].indexOf('}');
                            int luv2 = fetch[(fetch.length)-1].indexOf('{');
                            if(luv!=-1)
                            {
                                StringBuilder sbb = new StringBuilder(fetch[(fetch.length)-1]);
                                sbb.deleteCharAt(luv);
                                fetch[(fetch.length)-1]=sbb.toString();
                            }
                            if(luv2!=-1)
                            {
                                StringBuilder sbb = new StringBuilder(fetch[(fetch.length)-1]);
                                sbb.deleteCharAt(luv2);
                                fetch[(fetch.length)-1]=sbb.toString();
                            }
                        }

                        Arrays.sort(fetch);

                        for(int i=0;i<fetch.length;i++)
                        {
                            Log.d("MMMMMMMMMMMM", "onDataChange: ");

                            if(i==0)
                            {
                                String first_data = fetch[i];
                                StringBuilder sb = new StringBuilder(first_data);
                                sb.deleteCharAt(0);
                                String resultString = sb.toString();
                                if(resultString!=null)
                                    send_data.add(resultString);



                            }
                            else if(i==fetch.length-1)
                            {
                                String first_data = fetch[i];
                                StringBuilder sb = new StringBuilder(first_data);
                                sb.deleteCharAt(first_data.length()-1);
                                String resultString = sb.toString();
                                send_data.add(resultString);
                            }
                            else
                            {
                                String center_data = fetch[i];
                                send_data.add(center_data);
                            }
                        }

                        if(flag==0)
                        {
                            barData = new BarData(getxValue(),getBarValues());
                            barchart.setData(barData);
                            barchart.animateXY(3000,3000);
                            barchart.invalidate();

                        }
                        else
                        {

                            barData = new BarData(getxValuenew(),getBarValuesnew());
                            barchart.setData(barData);
                            barchart.animateXY(3000,3000);
                            barchart.invalidate();
                        }

                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(flag==0)
                {
                    barData = new BarData(getxValue(),getBarValues());
                    barchart.setData(barData);
                    barchart.animateXY(3000,3000);
                    barchart.invalidate();
                }
                else
                {
                    Log.d("FAIL", "getxValuenew: " + send_data);
                    barData = new BarData(getxValuenew(),getBarValuesnew());
                    barchart.setData(barData);
                    barchart.animateXY(3000,3000);
                    barchart.invalidate();
                }

            }
        });

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference my_addr=mDatabase.child("user_data").child(firebaseUser.getUid()).child("address").getRef();
        send_data=new ArrayList<>();

        my_addr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address_val = dataSnapshot.getValue(String.class);
                curr_addr=new String(address_val);
                mDatabase.child("meter_reading").child(curr_addr).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String fetch_data = dataSnapshot.getValue().toString();
                        String[] fetch = fetch_data.split(",");
                        Arrays.sort(fetch,0,fetch.length);
                        if(fetch[(fetch.length)-1].contains("}") || fetch[(fetch.length)-1].contains("{"))
                        {

                            int luv = fetch[(fetch.length)-1].indexOf('}');
                            int luv2 = fetch[(fetch.length)-1].indexOf('{');
                            if(luv!=-1)
                            {
                                StringBuilder sbb = new StringBuilder(fetch[(fetch.length)-1]);
                                sbb.deleteCharAt(luv);
                                fetch[(fetch.length)-1]=sbb.toString();
                            }
                            if(luv2!=-1)
                            {
                                StringBuilder sbb = new StringBuilder(fetch[(fetch.length)-1]);
                                sbb.deleteCharAt(luv2);
                                fetch[(fetch.length)-1]=sbb.toString();
                            }
                        }

                        Arrays.sort(fetch,0,fetch.length);


                        Log.d("CRLLLLL", "onDataChange: " + fetch[fetch.length-1]);

                        for(int i=0;i<fetch.length;i++)
                        {
                            if(i==0)
                            {
                                String first_data = fetch[i];
                                StringBuilder sb = new StringBuilder(first_data);
                                sb.deleteCharAt(0);
                                String resultString = sb.toString();
                                send_data.add(resultString);

                            }
                            else if(i==fetch.length-1)
                            {
                                Log.d("EROOR", "onDataChange: " + fetch[i]);
                                String first_data = fetch[i];
                                StringBuilder sb = new StringBuilder(first_data);
                                if(first_data.contains("}") || first_data.contains("{"))
                                {

                                    int luv = first_data.indexOf('}');
                                    int luv2 = first_data.indexOf('{');
                                    if(luv!=-1)
                                    {
                                        StringBuilder sbb = new StringBuilder(first_data);
                                        sbb.deleteCharAt(luv);
                                        first_data=sbb.toString();
                                    }
                                    if(luv2!=-1)
                                    {
                                        StringBuilder sbb = new StringBuilder(first_data);
                                        sbb.deleteCharAt(luv2);
                                        first_data=sbb.toString();
                                    }
                                }
                                send_data.add(first_data);
                            }
                            else
                            {
                                String center_data = fetch[i];
                                if(center_data.contains("}") || center_data.contains("{"))
                                {

                                    int luv = center_data.indexOf('}');
                                    int luv2 = center_data.indexOf('{');
                                    if(luv!=-1)
                                    {
                                        StringBuilder sbb = new StringBuilder(center_data);
                                        sbb.deleteCharAt(luv);
                                        center_data=sbb.toString();
                                    }
                                    if(luv2!=-1)
                                    {
                                        StringBuilder sbb = new StringBuilder(center_data);
                                        sbb.deleteCharAt(luv2);
                                        center_data=sbb.toString();
                                    }
                                }
                                send_data.add(center_data);

                            }
                        }

                        barData = new BarData(getxValue(),getBarValues());
                        barchart.setData(barData);
                        barchart.animateXY(3000,3000);
                        barchart.invalidate();
                        barchart.getAxisLeft().setTextSize(10.0f);
                        barchart.getAxisLeft().setSpaceBottom(0.5f);
                        barchart.getAxisRight().setEnabled(false);
                        barchart.setBackgroundColor(Color.rgb(255,255,255));
                        barchart.setDescription("");


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




        barData = new BarData(getxValue(),getBarValues());
        barchart.setData(barData);
        barchart.animateXY(3000,3000);
        barchart.invalidate();
        barchart.getAxisLeft().setTextSize(10.0f);
        barchart.getAxisLeft().setSpaceBottom(0.5f);
        barchart.getAxisRight().setEnabled(false);
        barchart.setBackgroundColor(Color.rgb(255,255,255));
        barchart.setDescription("");
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BarDataSet> getBarValues() {

        ArrayList<BarDataSet> barDataSets;
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        Log.d("IN BAR VALUES" ,"getBarValues: " + send_data);
//        send_data.sort();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String temp = dtf.format(now).toString();
        String array[] = temp.split(" ");
        String array1[] = array[0].split("/");
        String current_date = "30";


        for(int i=0,j=0;i<send_data.size();i++)
        {


            String data = send_data.get(i).trim();
            String arrayy[] = data.split("=");
            String d = arrayy[0].split(" ")[0].split("-")[2];
            if(arrayy[1].contains("}"))
            {

                int luv = arrayy[1].indexOf('}');
                StringBuilder sb = new StringBuilder(arrayy[1]);
                sb.deleteCharAt(luv);
                arrayy[1]=sb.toString();

            }
            float value = 0.0f;
            if(current_date.equalsIgnoreCase(d)) {
                value = Float.valueOf(arrayy[1]);
                Log.d("CHUT", "getBarValues: " + d);
                BarEntry barEntry =  new BarEntry(value,j++);
                barEntries.add(barEntry);

            }

        }


        BarDataSet barDataSet = new BarDataSet(barEntries,"Units consumed per hour (KWH) ");
        barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        return barDataSets;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getxValue() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String temp = dtf.format(now).toString();
        String array[] = temp.split(" ");
        String array1[] = array[0].split("/");
        String current_date = "30";
        Log.d("Current data", "getxValue: " + current_date);
        ArrayList<String> xValue = new ArrayList<>();
        for(int i=0;i<send_data.size();i++)
        {

            String data = send_data.get(i).trim();

            String arrayy[] = data.split("=");
            String d = arrayy[0].split(" ")[0].split("-")[2];
            if(current_date.equalsIgnoreCase(d))
                xValue.add(arrayy[0]);

        }

        Log.d("NORMAK", "getxValue: " + xValue);
        return xValue;
    }

    public List<BarDataSet> getBarValuesnew() {

        ArrayList<BarDataSet> barDataSets;
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        String dataa = send_data.get(0).trim();
        String arrayyy[] = dataa.split("=");
        String first_date_occurence = arrayyy[0].split(" ")[0].split("-")[2];

        for(int i=0,j=0;i<send_data.size();i++)
        {
            String data = send_data.get(i).trim();
            String arrayy[] = data.split("=");
            String d = arrayy[0].split(" ")[0].split("-")[2];
            int check = i;
            while(d.equalsIgnoreCase(first_date_occurence))
            {
                check++;
                if(check < send_data.size())
                {
                    data = send_data.get(check).trim();
                    String arrayyyy[] = data.split("=");
                    d = arrayyyy[0].split(" ")[0].split("-")[2];
                }else
                {
                    break;
                }

            }
            i=check-1;



            ///////////////////////////

            String dataaa = send_data.get(i);
            String array[] = dataaa.split("=");
            float value = Float.valueOf(array[1]);
            BarEntry barEntry =  new BarEntry(value,j++);
            barEntries.add(barEntry);
        }


        BarDataSet barDataSet = new BarDataSet(barEntries,"Units consumed per day for the current week (KWH) ");
        barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        return barDataSets;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getxValuenew() {
        String dataa = send_data.get(0).trim();
        String arrayyy[] = dataa.split("=");
        String first_date_occurence = arrayyy[0].split(" ")[0].split("-")[2];
        Collections.sort(send_data);
        Log.d("FIRSTACOOUR", "getxValuenew: " + send_data);
        ArrayList<String> xValue = new ArrayList<>();
        for(int i=0;i<send_data.size();i++)
        {
            String data = send_data.get(i).trim();
            String arrayy[] = data.split("=");
            String d = arrayy[0].split(" ")[0].split("-")[2];
            Log.d("NEXT OCCUR", "getxValuenew: " + d);
            int check = i;
            while(d.equalsIgnoreCase(first_date_occurence))
            {
                check++;
                if(check < send_data.size())
                {
                    data = send_data.get(check).trim();
                    String arrayyyy[] = data.split("=");
                    d = arrayyyy[0].split(" ")[0].split("-")[2];
                }else
                {
                    break;
                }

            }
            i=check-1;
            data = send_data.get(i).trim();
            String array[] = data.split("=");
            if(array[0].contains("}") || array[0].contains("{"))
            {

                int luv = array[0].indexOf('}');
                int luv2 = array[0].indexOf('{');
                if(luv!=-1)
                {
                    StringBuilder sbb = new StringBuilder(array[0]);
                    sbb.deleteCharAt(luv);
                    array[0]=sbb.toString();
                }
                if(luv2!=-1)
                {
                    StringBuilder sbb = new StringBuilder(array[0]);
                    sbb.deleteCharAt(luv2);
                    array[0]=sbb.toString();
                }
            }

//            Log.d("NONONON", "getxValuenew: " + array[0]);
            xValue.add(array[0]);
//            String dat = send_data.get(check).trim();
//            String arra[] = dat.split("=");
//            String first_date_occurenc = arra[0].split(" ")[0].split("-")[2];
//            first_date_occurence=first_date_occurenc;
        }

        Log.d("NEW XVAL", "getxValuenew: " + xValue);
        return xValue;
    }
}
