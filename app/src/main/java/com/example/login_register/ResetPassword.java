package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
EditText enterEmail;
Button reset;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        enterEmail=findViewById(R.id.enterEmail);
        reset=findViewById(R.id.reset);
        auth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email=enterEmail.getText().toString().trim();
        if(email.isEmpty()){
            enterEmail.setError("Email is required");
        enterEmail.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            enterEmail.setError("Please provide valid email");
            enterEmail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ResetPassword.this, "Something wrong happened please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}