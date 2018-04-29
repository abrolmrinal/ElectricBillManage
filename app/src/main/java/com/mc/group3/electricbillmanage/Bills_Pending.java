package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bills_Pending extends Fragment {

    View mBillsPendingView;

    private LinearLayout mBillsPending_linearLayout;
    private CardView mBillsPending_tempCardView;
    private LinearLayout mBillsPending_cardLinearLayout;
    private LinearLayout mBillsPending_toFrom_linearLayout;
    private TextView mBillsPending_to_textView, mBillsPending_from_textView;
    private TextView mBillsPending_amount_textView;

    private String to;
    private String from;
    private String amount;

    public Bills_Pending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBillsPendingView =  inflater.inflate(R.layout.fragment_bills_pending, container, false);

        mBillsPending_linearLayout = mBillsPendingView.findViewById(R.id.mBillsPending_linearLayout);

        ///>Retrieve data from entry
        String temp_to = "11111";
        String temp_from = "22222";
        String temp_amount = "33333";
        returnPendingEntry(temp_to, temp_from, temp_amount);
        mBillsPending_linearLayout.addView(mBillsPending_tempCardView);

        temp_to = "00000";
        temp_from = "55555";
        temp_amount = "88888";
        returnPendingEntry(temp_to, temp_from, temp_amount);
        mBillsPending_linearLayout.addView(mBillsPending_tempCardView);

        return mBillsPendingView;
    }

    private void returnPendingEntry(String to, String from, String amount){
        LinearLayout.LayoutParams generalParams_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT); //FULL VIEW
        LinearLayout.LayoutParams generalParams_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //WRAP CONTENT
        LinearLayout.LayoutParams generalParams_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //WRAP CONTENT for textViews

        ///>Create CardView
        mBillsPending_tempCardView = new CardView(mBillsPendingView.getContext());
        generalParams_2.setMargins(10, 0, 10, 20);
        mBillsPending_tempCardView.setLayoutParams(generalParams_2);
        mBillsPending_tempCardView.setRadius(50);
        mBillsPending_tempCardView.setContentPadding(2, 2, 2, 2);
        mBillsPending_tempCardView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mBillsPending_tempCardView.setCardElevation(10);
        mBillsPending_tempCardView.setMaxCardElevation(20);

        ///>Create Linear Layout inside card Layout;
        mBillsPending_cardLinearLayout = new LinearLayout(mBillsPendingView.getContext());
        mBillsPending_cardLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mBillsPending_cardLinearLayout.setLayoutParams(generalParams_1);



        ///>Create toFrom LinearLayout
        mBillsPending_toFrom_linearLayout = new LinearLayout(mBillsPendingView.getContext());
        mBillsPending_toFrom_linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mBillsPending_toFrom_linearLayout.setPadding(0, 2, 0, 2);
        mBillsPending_toFrom_linearLayout.setLayoutParams(generalParams_1);



        ///>Create To textView
        String to_string = "To: " + to;
        mBillsPending_to_textView = new TextView(mBillsPendingView.getContext());
        mBillsPending_to_textView.setLayoutParams(generalParams_3);
        mBillsPending_to_textView.setText(to_string);
        mBillsPending_to_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_to_textView.setPadding(5 , 2, 5, 2);

        ///>Create Fromm textView
        String from_string = "From: " + from;
        mBillsPending_from_textView = new TextView(mBillsPendingView.getContext());
        mBillsPending_from_textView.setLayoutParams(generalParams_3);
        mBillsPending_from_textView.setText(from_string);
        mBillsPending_from_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_from_textView.setPadding(5 , 2, 5, 2);

        ///>Create amount textView
        String amount_string = "Amount: Rs." + amount;
        mBillsPending_amount_textView = new TextView(mBillsPendingView.getContext());
        mBillsPending_amount_textView.setLayoutParams(generalParams_1);
        mBillsPending_amount_textView.setText(amount_string);
        mBillsPending_amount_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_amount_textView.setPadding(5 , 2, 5, 2);

        mBillsPending_toFrom_linearLayout.addView(mBillsPending_from_textView);
        mBillsPending_toFrom_linearLayout.addView(mBillsPending_to_textView);
        mBillsPending_cardLinearLayout.addView(mBillsPending_toFrom_linearLayout);
        mBillsPending_cardLinearLayout.addView(mBillsPending_amount_textView);
        mBillsPending_tempCardView.addView(mBillsPending_cardLinearLayout);

    }

}
