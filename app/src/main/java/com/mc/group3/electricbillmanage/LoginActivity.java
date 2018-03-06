package com.mc.group3.electricbillmanage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEdit;
    private EditText loginPassEdit;
    private Button loginButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onStart(){
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intentToLauncher = new Intent(getApplicationContext(), LauncherActivity.class);
            startActivity(intentToLauncher);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEdit = findViewById(R.id.loginEmailEdit);
        loginPassEdit = findViewById(R.id.loginPassEdit);
        loginButton = findViewById(R.id.loginLoginButton);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginButtonListener(View v){
        String email = loginEmailEdit.getText().toString();
        String password = loginPassEdit.getText().toString();

        if(email.equals("") && password.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyEmailandPass), Toast.LENGTH_SHORT).show();
            loginPassEdit.setText("");
        }
        else if(email.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyEmailField), Toast.LENGTH_SHORT).show();
            loginPassEdit.setText("");
        }
        else if(password.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyPasswordField), Toast.LENGTH_SHORT).show();
            loginPassEdit.setText("");
        }
        else if(password.length() < 8){
            Toast.makeText(getApplicationContext(), getString(R.string.ShortPassword), Toast.LENGTH_SHORT).show();
            loginPassEdit.setText("");
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intentToLauncher = new Intent(getApplicationContext(), LauncherActivity.class);
                                startActivity(intentToLauncher);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, R.string.IncorrectEmailOrPass, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

/*    public void loginForgotPassListener(View v){
        Intent intentToForgotPass = new Intent(this, ForgotPassword.class);
        startActivity(intentToForgotPass);
    }*/

    public void loginSignupListener(View v){
        Intent intentToSignup = new Intent(this, SignupActivity.class);
        startActivity(intentToSignup);
    }
    public void resetPasswordListener(View v){
        Intent intentToResetPassword = new Intent(this, ResetPasswordActivity.class);
        startActivity(intentToResetPassword);

    }
}
