package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {
    private TextView register,forgotPassword;
    private EditText Email, Password;
    private Button signIn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        register = findViewById(R.id.register);
        Intent intent = new Intent(this, RegisterUser.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        signIn = findViewById(R.id.signIn);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        forgotPassword=findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginUser.this,ResetPassword.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please provide valid email");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Password.setError("MIN password length should be 6 characters");
            Password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    if(user.isEmailVerified()) {
                        Intent intent=new Intent(LoginUser.this,Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginUser.this, "Check your email to verify", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginUser.this, "Failed to login!please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}