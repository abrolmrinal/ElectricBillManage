package com.mc.group3.electricbillmanage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LauncherActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    private final String EXTRA_MESSAGE = "com.mc.group3.electricbillmanage";
    private final String USER_NAME_VALUE = "com.mc.group3.electricbillmanage.username";
    private final String MOBILE_VALUE = "com.mc.group3.electricbillmanage.mobile";
    private final String ADDR_VALUE = "com.mc.group3.electricbillmanage.address";
    private final String SIGNUP = "com.mc.group3.electricbillmanage.signup";
    private final String LOGIN = "com.mc.group3.electricbillmanage.login";

    private String recv_username;
    private long recv_mobile;
    private String recv_address;

    TextView userEmail;
    TextView userName;

    @Override
    protected void onStart(){
        super.onStart();
        if(firebaseUser == null){
            Intent intentToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentToLogin);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userEmail = findViewById(R.id.emailid);
        userName = findViewById(R.id.username);

//        Window win = getWindow();
//        win.setStatusBarColor(16777215);

        String callingActivity = getIntent().getStringExtra(EXTRA_MESSAGE);
        if(callingActivity != null){
            if(callingActivity.equals(SIGNUP)){

                ///> Update username of new user
                SharedPreferences SP = getApplicationContext().getSharedPreferences(
                        getString(R.string.sharedPrefFile1), Context.MODE_PRIVATE);
                recv_username = SP.getString(USER_NAME_VALUE, getString(R.string.sharedPrefDefaultUsername));
                recv_mobile = SP.getLong(MOBILE_VALUE, 0);
                recv_address = SP.getString(ADDR_VALUE, "addr/addr");
                UserProfileChangeRequest newProfile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(recv_username).build();

                firebaseUser.updateProfile(newProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Remove this before submission
                        System.out.println("\n+++++++++++++++++++++\n \nADDED USERNAME\n \n++++++++++++++++++++++\n");
                    }
                });

                String uid = firebaseUser.getUid();
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LauncherActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ///> Add user details to database
                DatabaseReference dRef_userdata = firebaseDatabase.getReference();
                String pushInUserData = dRef_userdata.child("user_data").push().getKey();
                dRef_userdata.child("user_data").child(pushInUserData).setValue(uid);
                dRef_userdata.child("user_data").child(uid).child("address").setValue(recv_address);
                dRef_userdata.child("user_data").child(uid).child("phone_no").setValue(recv_mobile);

//                ///> Add new user to database and initialise values to zero
//                DatabaseReference databaseReference = firebaseDatabase.getReference();
//                String pushedUID = databaseReference.child("live_reading").push().getKey();
//                databaseReference.child("live_reading").child(pushedUID).setValue(uid);
//                databaseReference.child("live_reading").child(uid).child("usage_week").setValue(111111);
//                databaseReference.child("live_reading").child(uid).child("usage_month").setValue(111111);

            }

            else if(callingActivity.equals(LOGIN)){
                if(!firebaseUser.isEmailVerified()){
                    firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LauncherActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    firebaseUser.reload();
                }
            }
        }

//        String uid=firebaseUser.getUid();
//        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
//        DatabaseReference myref=fdb.getReference();
//        DatabaseReference tt=myref.child("users").child(uid).child("usage_week").getRef();
//
//        tt.addValueEventListener(new ValueEventListener() {
//                                     @Override
//                                     public void onDataChange(DataSnapshot dataSnapshot) {
//                                         String outp;
//                                         Integer i=dataSnapshot.getValue(Integer.class);
//                                         outp=Integer.toString(i);
//                                         Toast.makeText(getApplicationContext(), outp+":"+Integer.toString(outp.length()), Toast.LENGTH_SHORT).show();
//
//                                     }
//
//                                     @Override
//                                     public void onCancelled(DatabaseError databaseError) {
//
//                                     }
//                                 });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //TODO will default to dashboard() if orientation changed
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container,new Dashboard());
        ft1.commit();

        final NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_dashboard:
                                ft.replace(R.id.fragment_container, new Dashboard());
                                ft.commit();
                                break;
                            case R.id.nav_bill:
                                ft.replace(R.id.fragment_container,new Bills());
                                ft.commit();
                                break;
                            case R.id.nav_setting:
                                ft.replace(R.id.fragment_container,new Settings());
                                ft.commit();
                                break;
                            case R.id.nav_logout:
                                firebaseAuth.signOut();
                                Intent intentToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intentToLogin);
                                finish();

                        }
                        return true;
                    }
                });
//        ****************************************
//        *      TODO Change this after login
//        * **************************************
        if(firebaseUser != null){
            View header= navigationView.getHeaderView(0);
            TextView uname=(TextView)header.findViewById(R.id.username);
            uname.setText(firebaseUser.getDisplayName());
            TextView emailid=(TextView)header.findViewById(R.id.emailid);
            emailid.setText(firebaseUser.getEmail());
            navigationView.invalidate();
        }
        View header=navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                int size = navigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new UserProfile());
                ft.commit();
            }
        });

        System.out.println("starting service");
        startService(new Intent(this, FirebaseBackgroundService.class));
//

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
