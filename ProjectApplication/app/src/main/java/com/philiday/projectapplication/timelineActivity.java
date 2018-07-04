package com.philiday.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class timelineActivity extends AppCompatActivity {

    String EmailHolder;
    String username;
    TextView Email;

    SQLiteHelper db;

    @Override
    //Creates the activity_timeline layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        //Assigns Email to the textview email within activity_timeline (layout)
        Email = (TextView)findViewById(R.id.email);

        ListView listRun = (ListView) findViewById(R.id.runList);
        Intent intent = getIntent();


        EmailHolder = intent.getStringExtra("Username");
       // Log.i("mytag", "help" + EmailHolder);
        db = new SQLiteHelper(this);

        ArrayList<RunDetails> runList = db.getAllRuns(EmailHolder);
        ActivitiesAdapter myAdapter = new ActivitiesAdapter(runList, this);
        listRun.setAdapter(myAdapter);

        UserDetails userList = db.displayUser(EmailHolder);

        Email.setText(userList.getEmail());

       }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Home!",
                        Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.navigation_record:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Record!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(this, RecordingActivity.class);
                intent.putExtra("Username", EmailHolder);
                Log.i("username", "email"+ EmailHolder);
                startActivity(intent);
                return true;
            case R.id.navigation_logout:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Logout!",
                        Toast.LENGTH_SHORT)
                        .show();

                Intent login = new Intent(this, LoginActivity.class);
               // intent.putExtra("Username", EmailHolder);
              //  Log.i("username", "email"+ EmailHolder);
                startActivity(login);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Same done here with a different button
    public void goToMap(View view) {
        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }

    //Ignore this
    public String getUsername(){
        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        return username;

    }
}
