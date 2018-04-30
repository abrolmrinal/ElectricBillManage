package com.mc.group3.electricbillmanage;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Trends extends Fragment {

    private WebView webview;
    private HashMap<String,Float> map=null;
    private String curr_addr=null;

    public Dashboard_Trends() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard_trends, container, false);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
        DatabaseReference myref=fdb.getReference();
        DatabaseReference month_usage=myref.child("month_reading_addr").getRef();
        DatabaseReference my_addr=myref.child("user_data").child(firebaseUser.getUid()).child("address").getRef();

        my_addr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address_val = dataSnapshot.getValue(String.class);
                curr_addr=new String(address_val);
                changeData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        month_usage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map=new HashMap<String, Float>();

                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){

                    String key = uniqueKeySnapshot.getKey();
                    String value = (String)uniqueKeySnapshot.child("usage").getValue();
                    float f=0;
                    try
                    {
                         f = Float.valueOf(value.trim()).floatValue();
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }

                    map.put(key,f);
                }
                changeData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        webview = (WebView)view.findViewById(
                R.id.graph_trends);

        WebSettings webSettings =
                webview.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {

                JSONArray tt = generateData(10);
                int sel =4;
                JSONObject out = new JSONObject();
                try {
                    out.put("data",tt);
                    out.put("selected",sel);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // pass the array to the JavaScript function
                System.out.println("test= "+tt.toString());
                webview.loadUrl("javascript:loadData(" +
                        out.toString() + ")");
            }
        });

        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/test.html");


        return view;
    }

    public JSONObject createJObject(int factor , Float eigen, int cumu_eigen)
    {
        JSONObject entry = new JSONObject();
        try {
            entry.put("factor", factor);
            entry.put("eigenvalue",eigen);
            entry.put("cumulative_eigenvalue",cumu_eigen);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entry;
    }

    public JSONArray generateData(int count)
    {
        //creates [[{time:"",y:0},{},{}]]
        JSONArray inner=new JSONArray();

        int i;
        for(i=0;i<count;i++)
        {
            inner.put(createJObject(i+1,(i+1)*10f,(i+1)));
        }

        JSONArray outer = new JSONArray();
        outer.put(inner);


        return inner;
    }


    public LinkedHashMap<String, Float> sortHashMapByValues(
            HashMap<String, Float> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Float> sortedMap =
                new LinkedHashMap<>();

        Iterator<Float> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Float val = valueIt.next();
            Iterator<String > keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Float comp1 = passedMap.get(key);
                Float comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }


    public JSONArray generateData2(ArrayList<Float> arr)
    {
        //creates [[{time:"",y:0},{},{}]]
        JSONArray inner=new JSONArray();

        int i;
        for(i=0;i<arr.size();i++)
        {
            inner.put(createJObject(i+1,arr.get(i),(i+1)));
        }

        JSONArray outer = new JSONArray();
        outer.put(inner);


        return inner;
    }

    public void changeData() {


        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                if (map != null & curr_addr != null) {

                    int sel = 4;

                    LinkedHashMap<String ,Float> tem = sortHashMapByValues(map);

                    System.out.println(tem.toString());
                    int i=0;
                    ArrayList<Float> graph_val=new ArrayList<Float>();
                    System.out.println("name ="+curr_addr);
                    for(String key:tem.keySet())
                    {
                        if(key.equals(curr_addr))
                        {
                            sel=i;
                        }
                        i++;
                        graph_val.add(tem.get(key));

                    }

                    System.out.println("sel = "+sel);

                    System.out.println(graph_val.toString());

                    JSONArray tt = generateData2(graph_val);

                    JSONObject out = new JSONObject();
                    try {
                        out.put("data", tt);
                        out.put("selected", sel+1);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    // pass the array to the JavaScript function
                    System.out.println("test= " + tt.toString());
                    webview.loadUrl("javascript:loadData(" +
                            out.toString() + ")");

            }
        }

        });
            webview.loadUrl("file:///android_asset/" +
                    "html/test.html");


    }




}
