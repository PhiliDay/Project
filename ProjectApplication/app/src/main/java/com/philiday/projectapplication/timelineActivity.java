package com.philiday.projectapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class timelineActivity extends AppCompatActivity {

    String EmailHolder;
    String username;
    TextView Email;


    @Override

    //Creates the activity_timeline layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Assigns Email to the textview email within activity_timeline (layout)
        Email = (TextView)findViewById(R.id.email);

        //Ignore this(its to do with database stuff)
        Intent intent = getIntent();

        EmailHolder = intent.getStringExtra("Username");
        Email.setText(Email.getText().toString() + " " + EmailHolder);
        Email.setText(getUsername());
    }

    //Here is a method that links to a button to start the run - within the layout
    //there is an onClick bit and ive just added this method to it.
    //This uses Intent to put data from here into the next activity
    //The activity I want to move to (RecordingActivity) is put below
    //Log.i is just a comment that appears in the 'logcat' when run
    public void startRun(View view) {
        Intent intent = new Intent(this, RecordingActivity.class);
        intent.putExtra("Username", EmailHolder);
        Log.i("username", "email"+ EmailHolder);
        startActivity(intent);
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
