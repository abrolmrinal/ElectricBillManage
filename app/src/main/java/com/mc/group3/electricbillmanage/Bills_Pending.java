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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bills_Pending extends Fragment {

    View mBillsPendingView;

    private LinearLayout mBillsPending_linearLayout;
    private CardView mBillsPending_tempCardView;
    private LinearLayout mBillsPending_cardLinearLayout;
    private LinearLayout mBillsPending_toFrom_linearLayout;
    private TextView mBillsPending_to_textView, mBillsPending_from_textView, mBillsPending_empty_textView;
    private TextView mBillsPending_amount_textView;
    private TextView mBillsPending_isPaid_textView;

    private String to;
    private String from;
    private String amount;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;

    public Bills_Pending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBillsPendingView =  inflater.inflate(R.layout.fragment_bills_pending, container, false);

        mBillsPending_linearLayout = mBillsPendingView.findViewById(R.id.mBillsPending_linearLayout);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        

        DatabaseReference refToUserAddress = mFirebaseDatabase.getReference();
        refToUserAddress.child("user_data").child(mFirebaseUser.getUid()).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.getValue(String.class);
                DatabaseReference refToUserBillPending = mFirebaseDatabase.getReference();
                refToUserBillPending.child("bill_pending").child(address).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(int i = 1; i <= 12; i++) {
                            System.out.println("+++++++++++++++ curr_child: " + i);
                            if(dataSnapshot.hasChild(Integer.toString(i))){
                                System.out.println("+++++++++++++++ found child");
                                String temp_to = dataSnapshot.child(Integer.toString(i)).child("end_date").getValue(String.class);
                                String temp_from = dataSnapshot.child(Integer.toString(i)).child("from_date").getValue(String.class);
                                String temp_amount = Float.toString(Math.round(dataSnapshot.child(Integer.toString(i)).child("amount").getValue(Float.class) * 100.0f)/ 100.0f);
                                String isPaid = dataSnapshot.child(Integer.toString(i)).child("isPaid").getValue(String.class);

                                returnPendingEntry(temp_to, temp_from, temp_amount, isPaid);
                                mBillsPending_linearLayout.addView(mBillsPending_tempCardView);
                            }

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

        return mBillsPendingView;
    }

    private void returnPendingEntry(String to, String from, String amount, String isPaid){
        LinearLayout.LayoutParams generalParams_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT); //FULL VIEW
        LinearLayout.LayoutParams generalParams_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //WRAP CONTENT
        LinearLayout.LayoutParams generalParams_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //WRAP CONTENT for textViews

        ///>Create CardView
        mBillsPending_tempCardView = new CardView(mBillsPendingView.getContext());
        generalParams_2.setMargins(15, 5, 15, 15);
        mBillsPending_tempCardView.setLayoutParams(generalParams_2);
        mBillsPending_tempCardView.setRadius(10);
        mBillsPending_tempCardView.setContentPadding(2, 2, 2, 2);
        mBillsPending_tempCardView.setCardBackgroundColor(getResources().getColor(R.color.colorGreyCardBG));
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
        generalParams_3.setMargins(3, 3, 3, 3);
        mBillsPending_to_textView.setLayoutParams(generalParams_3);
        mBillsPending_to_textView.setText(to_string);
        mBillsPending_to_textView.setTextSize(18);
        mBillsPending_to_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_to_textView.setTextColor(getResources().getColor(R.color.dashboardTheme));
        mBillsPending_to_textView.setPadding(5 , 2, 5, 2);

        String empty_string = "                              ";
        mBillsPending_empty_textView = new TextView(mBillsPendingView.getContext());
        generalParams_3.setMargins(3, 3, 3, 3);
        generalParams_1.weight = 1;
        mBillsPending_empty_textView.setLayoutParams(generalParams_3);
        mBillsPending_empty_textView.setText(empty_string);
        mBillsPending_empty_textView.setTextSize(18);
        mBillsPending_empty_textView.setTextSize(18);
        mBillsPending_empty_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_empty_textView.setPadding(5 , 2, 5, 2);

        ///>Create Fromm textView
        String from_string = "From: " + from;
        mBillsPending_from_textView = new TextView(mBillsPendingView.getContext());
        generalParams_3.setMargins(3, 3, 3, 3);
        mBillsPending_from_textView.setLayoutParams(generalParams_3);
        mBillsPending_from_textView.setText(from_string);
        mBillsPending_from_textView.setTextSize(18);
        mBillsPending_from_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_from_textView.setTextColor(getResources().getColor(R.color.dashboardTheme));
        mBillsPending_from_textView.setPadding(5 , 2, 5, 2);

        ///>Create amount textView
        String amount_string = "Amount: Rs." + amount;
        mBillsPending_amount_textView = new TextView(mBillsPendingView.getContext());
        generalParams_1.setMargins(3, 3, 3, 6);
        mBillsPending_amount_textView.setLayoutParams(generalParams_1);
        mBillsPending_amount_textView.setText(amount_string);
        mBillsPending_amount_textView.setTextSize(18);
        mBillsPending_amount_textView.setTextColor(getResources().getColor(R.color.dashboardTheme));
        mBillsPending_amount_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_amount_textView.setPadding(5 , 2, 5, 2);

        String paid_string = "Paid: " + isPaid;
        mBillsPending_isPaid_textView = new TextView(mBillsPendingView.getContext());
        generalParams_1.setMargins(3, 3, 3, 6);
        mBillsPending_isPaid_textView.setLayoutParams(generalParams_1);
        mBillsPending_isPaid_textView.setText(paid_string);
        mBillsPending_isPaid_textView.setTextSize(18);
        mBillsPending_isPaid_textView.setTextColor(getResources().getColor(R.color.dashboardTheme));
        mBillsPending_isPaid_textView.setGravity(Gravity.CENTER_VERTICAL);
        mBillsPending_isPaid_textView.setPadding(5 , 2, 5, 2);

        mBillsPending_toFrom_linearLayout.addView(mBillsPending_from_textView);
        mBillsPending_toFrom_linearLayout.addView(mBillsPending_empty_textView);
        mBillsPending_toFrom_linearLayout.addView(mBillsPending_to_textView);
        mBillsPending_cardLinearLayout.addView(mBillsPending_toFrom_linearLayout);
        mBillsPending_cardLinearLayout.addView(mBillsPending_amount_textView);
        mBillsPending_cardLinearLayout.addView(mBillsPending_isPaid_textView);
        mBillsPending_tempCardView.addView(mBillsPending_cardLinearLayout);

    }

}
