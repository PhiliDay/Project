package com.philiday.projectapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    String walkingDistanceHolder;
    TextView startLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        startLocation = (TextView)findViewById(R.id.startLoc);


        Intent intent = getIntent();




        walkingDistanceHolder = intent.getStringExtra(RecordingActivity.startLocationString);
        startLocation.setText(startLocation.getText().toString() + walkingDistanceHolder);

    }

    //HERE I NEED TO TAKE THE DISTANCE AND TIME TAKEN FROM THE ACTIVITY RECORDING AND CALCULATE THE PACE OVERALL. USE BUNDLE 2 DO THIS


}
