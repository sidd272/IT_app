package com.example.login_register;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class PasswordEditorActivity extends AppCompatActivity {

    int passID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        passID = intent.getIntExtra("passID", -1);

        if( passID != -1)
        {
            editText.setText(Passwords.passwords.get(passID));
        }

        else
        {
            Passwords.passwords.add("");                // as initially, the note is empty
            passID= Passwords.passwords.size() - 1;
            Passwords.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Passwords.passwords.set(passID, String.valueOf(s));
                Passwords.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.sid.passwords", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>( Passwords.passwords);
                sharedPreferences.edit().putStringSet("passwords", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }
}