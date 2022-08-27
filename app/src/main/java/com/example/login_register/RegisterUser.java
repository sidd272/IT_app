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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    private TextView banner;
    private Button button;
    Task<Void> Firebase;
    private EditText FullName, Age, Email, Password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        banner = findViewById(R.id.banner);
        FullName = findViewById(R.id.fullname);
        Age = findViewById(R.id.age);
        button = findViewById(R.id.signIn);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Intent intent = new Intent(this, LoginUser.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


    }

    private void registerUser() {
        String email = Email.getText().toString().trim();
        String fullname = FullName.getText().toString().trim();
        String age = Age.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (fullname.isEmpty()) {
            FullName.setError("Full name is required");
            FullName.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            Age.setError("Age is required");
            Age.requestFocus();
            return;
        }

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


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullname, age, email);
                            Firebase = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Failed to register! Try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}




