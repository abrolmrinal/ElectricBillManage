package com.mc.group3.electricbillmanage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private EditText signupUsernameEdit;
    private EditText signupEmailEdit;
    private EditText signupPassEdit;
    private Button signupButton;

    private FirebaseAuth firebaseAuth;

    private final String EXTRA_MESSAGE = "com.mc.group3.electricbillmanage.username";
    private final String SIGNUP = "com.mc.group3.electricbillmanage.signup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsernameEdit = findViewById(R.id.signupUsernameEdit);
        signupEmailEdit = findViewById(R.id.signupEmailEdit);
        signupPassEdit = findViewById(R.id.signupPassEdit);
        signupButton = findViewById(R.id.signupButton);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signupButtonListener(View v){
        String username = signupUsernameEdit.getText().toString();
        String email = signupEmailEdit.getText().toString();
        String password = signupPassEdit.getText().toString();

        if(email.equals("") && password.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyEmailandPass), Toast.LENGTH_SHORT).show();
            signupPassEdit.setText("");
        }
        else if(email.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyEmailField), Toast.LENGTH_SHORT).show();
            signupPassEdit.setText("");
        }
        else if(password.equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.EmptyPasswordField), Toast.LENGTH_SHORT).show();
            signupPassEdit.setText("");
        }
        else if(password.length() < 8){
            Toast.makeText(getApplicationContext(), getString(R.string.ShortPassword), Toast.LENGTH_SHORT).show();
            signupPassEdit.setText("");
        }
        else{
            //Save username to a SharedPreferences file
            SharedPreferences SP = getApplicationContext().getSharedPreferences(
                    getString(R.string.sharedPrefFile1), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = SP.edit();
            editor.putString(EXTRA_MESSAGE, username);
            editor.apply();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intentToLauncher = new Intent(SignupActivity.this, LauncherActivity.class);
                                intentToLauncher.putExtra(EXTRA_MESSAGE, SIGNUP);
                                startActivity(intentToLauncher);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), R.string.SignupFailed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
