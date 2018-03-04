package com.mc.group3.electricbillmanage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent home_intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(home_intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }


}
