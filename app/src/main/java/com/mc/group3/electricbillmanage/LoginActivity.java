package com.mc.group3.electricbillmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginEmailEdit = findViewById(R.id.loginEmailEdit);
        EditText loginPassEdit = findViewById(R.id.loginPassEdit);

        if(loginEmailEdit.getText().equals("admin") && loginPassEdit.getText().equals("password")){
            Intent intentToDash = new Intent()
        }
    }
}
