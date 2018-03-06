package com.mc.group3.electricbillmanage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button resetButton;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = (EditText)findViewById(R.id.resetEmail);
        resetButton = (Button)findViewById(R.id.resetForgotPasswordButton);

        auth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String emailText = email.getText().toString().trim();
                if(TextUtils.isEmpty(emailText)){
                    Toast.makeText(getApplication(), "Enter your regitered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "We have sent instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
