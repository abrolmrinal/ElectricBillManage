package com.mc.group3.electricbillmanage;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HomeActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;

    private SliderAdapter sliderAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSlideViewPager = findViewById(R.id.slideviewpager);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);


    }

    public void LoginFunction(View view)
    {
        Intent intentToLogin = new Intent(this, LoginActivity.class);
        startActivity(intentToLogin);
    }
}
