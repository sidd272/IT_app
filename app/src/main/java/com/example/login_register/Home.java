package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        Intent intent = new Intent(this, MainActivity.class);
        Intent intent1 = new Intent(this, Passwords.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent1);
            }
        });
    }
}