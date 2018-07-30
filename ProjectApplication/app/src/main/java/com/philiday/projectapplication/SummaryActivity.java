package com.philiday.projectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;

import static com.philiday.projectapplication.SQLiteHelper.TABLE_NAME_1;
import static com.philiday.projectapplication.SQLiteHelper.Table1_Column_5_userId;

public class SummaryActivity extends AppCompatActivity {

    String dista, time1, timeOfRun, userId, hours, minutes, seconds, walkingDist, ranDist, overallTime, runningTime, walkingTime, walkHours, walkMinutes, walkSeconds, runHours, runMinutes, runSeconds, totalPace, runningPace, walkingPace;

    TextView distance, time, date, username, walkTime, runTime, walkDist, runDist, overallPace, oRunningPace, oWalkingPace;
    TextView startLocation;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    byte[] screenshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        sqLiteHelper = new SQLiteHelper(this);

        initViews();

        Log.i("dista2", "dista2 " + dista + "!");
        //Only displays the relevant information - maybe make different TextViews to ensure correct positioning?
        calculateValues();

        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        RunDetails run = new RunDetails(userId, timeOfRun, dista, hours + "h," + minutes + "min" + seconds + "s", walkingDist, ranDist, walkHours + "h," + walkMinutes + "min" + walkSeconds + "s",runHours + "h," + runMinutes + "min" + runSeconds + "s", totalPace, walkingPace, runningPace, screenshot);
        sqLiteHelper.getWritableDatabase();
        long insertingRun = sqLiteHelper.createRun(run);


        deleteRun();
    }

    private void deleteRun(){
        ImageView btnSearch= (ImageView) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteRun(timeOfRun);
                goToHome(v);
            }
        });

    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("Username", userId);
        startActivity(intent);
    }

    private void initViews(){
        startLocation = (TextView) findViewById(R.id.startLoc);
        distance = (TextView) findViewById(R.id.distance);
        time = (TextView) findViewById(R.id.time);
        date = (TextView) findViewById(R.id.date);
        username = (TextView) findViewById(R.id.username);
        walkTime = (TextView) findViewById(R.id.walkTime);
        runTime = (TextView) findViewById(R.id.runTime);
        walkDist = (TextView) findViewById(R.id.walkDistance);
        runDist = (TextView) findViewById(R.id.runDistance);
        overallPace = (TextView) findViewById(R.id.overallPace);
        oWalkingPace = (TextView) findViewById(R.id.oWalkingPace);
        oRunningPace = (TextView) findViewById(R.id.oRunningPace);

        date.setText(timeOfRun);
        distance.setText(dista);
        username.setText(userId);

        walkDist.setText(walkingDist);
        runDist.setText(ranDist);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        dista = (String) b.get("distance");
        time1 = (String) b.get("time");
        timeOfRun = (String) b.get("timeOfRun");
        userId = (String) b.get("Username");
        hours = (String) b.get("hours");
        minutes = (String) b.get("minutes");
        seconds = (String) b.get("seconds");
        walkingDist = (String) b.get("walkedDist");
        ranDist = (String) b.get("ranDist");
        screenshot = b.getByteArray("mapPhoto");
        walkHours = (String) b.get("walkHoursTaken");
        walkMinutes = (String) b.get("walkMinutesTaken");
        walkSeconds = (String) b.get("walkSecondsTaken");
        runHours = (String) b.get("runHoursTaken");
        runMinutes = (String) b.get("runMinutesTaken");
        runSeconds = (String) b.get("runSecondsTaken");

        Log.i("dista", "dista" + dista);
        Log.i("username1", "username1" + userId);
        Log.i("hours", "hours " + hours);
        Log.i("minutes", "minutes" + minutes);
        Log.i("walkingDist", "walkingDist1" + walkingDist);
        Log.i("runningTime", "runningTime" + runMinutes + "mn(s)" + runSeconds + "s");
        Log.i("walkingTime", "walkingTime" + walkMinutes + "mn(s)" + walkSeconds + "s");

        dista = dista.replaceAll("miles", "");
        walkingDist = walkingDist.replaceAll("miles","");
        ranDist = ranDist.replaceAll("miles","");

    }

    private void setPace(TextView type, String pace){
        type.setText(pace);
    }

    private void calculateValues(){
        DecimalFormat df = new DecimalFormat("#.00");

        if (hours.equals("0.0") && minutes.equals("0.0")) {
            if(dista == "0 ") {
                totalPace = df.format(Double.toString(0));
            }else {
                totalPace = df.format(Double.toString((Double.parseDouble(seconds) / 60) / Double.parseDouble(dista)));
            }
            setPace(overallPace, totalPace);
            time.setText(seconds + "s");
        } else if (hours.equals("0.0")) {
            if(dista == "0 ") {
                totalPace = "0";
            }else{
                totalPace = df.format(Double.toString((((Double.parseDouble(minutes) * 60) + Double.parseDouble(seconds)) / 60) / (Double.parseDouble(dista))));
            }
            setPace(overallPace, totalPace);
            time.setText(minutes + "mn(s)" + seconds + "s");
        } else {
            if(dista == "0 ") {
                totalPace = "0";
            }else{
                totalPace = df.format(Double.toString((Double.parseDouble(hours) * 60) + ((Double.parseDouble(minutes)) + (Double.parseDouble(seconds) / 60) / (Double.parseDouble(dista)))));
            }
            setPace(overallPace, totalPace);

            time.setText(hours + "h(s), " + minutes + "mn(s) " + seconds + "s");
        }

        if (walkHours.equals("0.0") && walkMinutes.equals("0.0")) {
            if(walkingDist != "0 ") {
                walkingPace = df.format(Double.toString((Double.parseDouble(walkSeconds) / 60) / Double.parseDouble(walkingDist)));
            }else{
                walkingPace = "0";
            }
            setPace(oWalkingPace, walkingPace);
            walkTime.setText(walkSeconds + "s");
        } else if (walkHours.equals("0.0")) {
            if(walkingDist != "0 "){
                walkingPace = df.format(Double.toString(Double.parseDouble(walkMinutes)+ (Double.parseDouble(walkSeconds)/60) / (Double.parseDouble(walkingDist))));
            }else{
                walkingPace = "0";
            }
            setPace(oWalkingPace, walkingPace);

            walkTime.setText(walkMinutes + "mn(s)" + walkSeconds + "s");
        } else {
            if(walkingDist != "0 "){
                walkingPace = df.format(Double.toString(Double.parseDouble(walkHours)+Double.parseDouble(walkMinutes)+(Double.parseDouble(walkSeconds)/60) / (Double.parseDouble(walkingDist))));

            }else{
                walkingPace = "0";
            }

            setPace(oWalkingPace, walkingPace);

            walkTime.setText(walkHours + "h(s), " + walkMinutes + "mn(s) " + walkSeconds + "s");
        }

        if (runHours.equals("0.0") && runMinutes.equals("0.0")) {
            if(ranDist != "0 ") {
                runningPace = df.format(Double.toString((Double.parseDouble(runSeconds) / 60) / Double.parseDouble(ranDist)));
            }else{
                runningPace = "0";
            }
            setPace(oRunningPace, runningPace);
            runTime.setText(runSeconds + "s");
        } else if (runHours.equals("0.0")) {
            if(ranDist != "0 ") {
                runningPace = df.format(Double.toString(Double.parseDouble(runMinutes) + (Double.parseDouble(runSeconds) / 60) / (Double.parseDouble(ranDist))));
            }else{
                ranDist = "0";
            }
            setPace(oRunningPace, runningPace);

            runTime.setText(runMinutes + "mn(s)" + runSeconds + "s");
        } else {
            if (ranDist != "0 ") {
                runningPace = df.format(Double.toString(Double.parseDouble(runHours) + Double.parseDouble(runMinutes) + (Double.parseDouble(runSeconds) / 60) / (Double.parseDouble(ranDist))));
            }else{
                runningPace = "0";
            }
            setPace(oRunningPace, runningPace);
            runTime.setText(runHours + "h(s), " + runMinutes + "mn(s) " + runSeconds + "s");
        }
    }






}
