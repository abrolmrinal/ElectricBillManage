package com.mc.group3.electricbillmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEdit;
    private EditText loginPassEdit;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEdit = findViewById(R.id.loginEmailEdit);
        loginPassEdit = findViewById(R.id.loginPassEdit);
    }

    public void loginButtonListener(View v){
        if(loginEmailEdit.getText().toString().equals(getString(R.string.user_email)) && loginPassEdit.getText().toString().equals(getString(R.string.tempPasswordString))){
            Intent intentToLaunch = new Intent(this, LauncherActivity.class);
            startActivity(intentToLaunch);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.LoginFailToast), Toast.LENGTH_LONG).show();
            loginEmailEdit.setText("");
            loginPassEdit.setText("");
        }
    }
}
