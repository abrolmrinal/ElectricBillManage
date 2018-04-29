package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import android.view.Gravity;

public class Bills_History extends Fragment {

    View mBillsHistoryView;

    private LinearLayout mBillsHistory_linearLayout;
    private CardView mBillsHistory_tempCardView;
    private LinearLayout mBillsHistory_cardLinearLayout;
    private LinearLayout mBillsHistory_toFrom_linearLayout;
    private TextView mBillsHistory_to_textView, mBillsHistory_from_textView;
    private TextView mBillsHistory_amount_textView;

    private String to;
    private String from;
    private String amount;


    public Bills_History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBillsHistoryView =  inflater.inflate(R.layout.fragment_bills_history, container, false);

        mBillsHistory_linearLayout = mBillsHistoryView.findViewById(R.id.mBillsHistory_linearLayout);

        ///>Retrieve data from entry
        String temp_to = "11111";
        String temp_from = "22222";
        String temp_amount = "33333";
        returnHistoryEntry(temp_to, temp_from, temp_amount);
        mBillsHistory_linearLayout.addView(mBillsHistory_tempCardView);

        temp_to = "00000";
        temp_from = "55555";
        temp_amount = "88888";
        returnHistoryEntry(temp_to, temp_from, temp_amount);
        mBillsHistory_linearLayout.addView(mBillsHistory_tempCardView);

        return mBillsHistoryView;

    }

    private void returnHistoryEntry(String to, String from, String amount){
        LayoutParams generalParams_1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); //FULL VIEW
        LayoutParams generalParams_2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); //WRAP CONTENT
        LayoutParams generalParams_3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); //WRAP CONTENT for textViews

        ///>Create CardView
        mBillsHistory_tempCardView = new CardView(mBillsHistoryView.getContext());
        generalParams_2.setMargins(10, 0, 10, 20);
        mBillsHistory_tempCardView.setLayoutParams(generalParams_2);
        mBillsHistory_tempCardView.setRadius(50);
        mBillsHistory_tempCardView.setContentPadding(2, 2, 2, 2);
        mBillsHistory_tempCardView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mBillsHistory_tempCardView.setCardElevation(10);
        mBillsHistory_tempCardView.setMaxCardElevation(20);

        ///>Create Linear Layout inside card Layout;
        mBillsHistory_cardLinearLayout = new LinearLayout(mBillsHistoryView.getContext());
        mBillsHistory_cardLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mBillsHistory_cardLinearLayout.setLayoutParams(generalParams_1);



        ///>Create toFrom LinearLayout
        mBillsHistory_toFrom_linearLayout = new LinearLayout(mBillsHistoryView.getContext());
        mBillsHistory_toFrom_linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mBillsHistory_toFrom_linearLayout.setPadding(0, 2, 0, 2);
        mBillsHistory_toFrom_linearLayout.setLayoutParams(generalParams_1);



        ///>Create To textView
        String to_string = "To: " + to;
        mBillsHistory_to_textView = new TextView(mBillsHistoryView.getContext());
        mBillsHistory_to_textView.setLayoutParams(generalParams_3);
        mBillsHistory_to_textView.setText(to_string);
        mBillsHistory_to_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsHistory_to_textView.setPadding(5 , 2, 5, 2);

        ///>Create Fromm textView
        String from_string = "From: " + from;
        mBillsHistory_from_textView = new TextView(mBillsHistoryView.getContext());
        mBillsHistory_from_textView.setLayoutParams(generalParams_3);
        mBillsHistory_from_textView.setText(from_string);
        mBillsHistory_from_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsHistory_from_textView.setPadding(5 , 2, 5, 2);

        ///>Create amount textView
        String amount_string = "Amount: Rs." + amount;
        mBillsHistory_amount_textView = new TextView(mBillsHistoryView.getContext());
        mBillsHistory_amount_textView.setLayoutParams(generalParams_1);
        mBillsHistory_amount_textView.setText(amount_string);
        mBillsHistory_amount_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsHistory_amount_textView.setPadding(5 , 2, 5, 2);

        mBillsHistory_toFrom_linearLayout.addView(mBillsHistory_from_textView);
        mBillsHistory_toFrom_linearLayout.addView(mBillsHistory_to_textView);
        mBillsHistory_cardLinearLayout.addView(mBillsHistory_toFrom_linearLayout);
        mBillsHistory_cardLinearLayout.addView(mBillsHistory_amount_textView);
        mBillsHistory_tempCardView.addView(mBillsHistory_cardLinearLayout);

    }

}
