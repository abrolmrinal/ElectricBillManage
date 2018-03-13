package com.mc.group3.electricbillmanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {


    public UserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_user_profile, container, false);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
        DatabaseReference myref=fdb.getReference();
        DatabaseReference phone_ref=myref.child("user_data").child(firebaseUser.getUid()).child("phone_no").getRef();
        DatabaseReference address_ref=myref.child("user_data").child(firebaseUser.getUid()).child("address").getRef();


        final TextView phone_no = (TextView) view.findViewById(R.id.phone_number_field);
        TextView email_id = (TextView) view.findViewById(R.id.emai_field);
        TextView uname = (TextView) view.findViewById(R.id.Name_field);
        final TextView address = (TextView) view.findViewById(R.id.address_field);

        uname.setText(firebaseUser.getDisplayName());
        email_id.setText(firebaseUser.getEmail());

        phone_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long num=dataSnapshot.getValue(Long.class);
                phone_no.setText(Long.toString(num));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        address_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address_val=dataSnapshot.getValue(String.class);
                address.setText(address_val);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

}
