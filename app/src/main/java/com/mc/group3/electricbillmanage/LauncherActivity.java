package com.mc.group3.electricbillmanage;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //TODO will default to dashboard() if orientation changed
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container,new Dashboard());
        ft1.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
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
                            case R.id.nav_logout:
                                firebaseAuth.signOut();
                                Intent intentToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intentToLogin);
                                finish();
                        }
                        return true;
                    }
                });
        /*
        ****************************************
        *      TODO Change this after login
        * **************************************
        View header= navigationView.getHeaderView(0);
        TextView uname=(TextView)header.findViewById(R.id.username);
        uname.setText();
        TextView emailid=(TextView)header.findViewById(R.id.emailid);
        emailid.setText();
        */
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
