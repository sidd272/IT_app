package com.example.login_register;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Passwords extends AppCompatActivity {

    static ArrayList<String> passwords = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_password)
        {
            Intent intent = new Intent(getApplicationContext(), PasswordEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords  );

        ListView listView = (ListView)findViewById(R.id.listView2);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.sid.passwords", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("passwords", null);

        if(set == null)
        {
            passwords.add("Example Password");
        }

        else
        {
            passwords = new ArrayList<>(set);         // to bring all the already stored data in the set to the notes ArrayList
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, passwords);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), PasswordEditorActivity.class);
                intent.putExtra("passID", position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {

                new AlertDialog.Builder(Passwords.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                passwords.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.sid.passwords", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(Passwords.passwords);
                                sharedPreferences.edit().putStringSet("passwords", set).apply();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;               // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
            }
        });
    }
}