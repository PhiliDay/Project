package com.philiday.projectapplication;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Home!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent home = new Intent(this, timelineActivity.class);
                startActivity(home);
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



    //Here is a method that links to a button to start the run - within the layout
    //there is an onClick bit and ive just added this method to it.
    //This uses Intent to put data from here into the next activity
    //The activity I want to move to (RecordingActivity) is put below
    //Log.i is just a comment that appears in the 'logcat' when run
//    public void startRun(View view) {
//        Intent intent = new Intent(this, RecordingActivity.class);
//        intent.putExtra("Username", EmailHolder);
//        Log.i("username", "email"+ EmailHolder);
//        startActivity(intent);
//    }

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
