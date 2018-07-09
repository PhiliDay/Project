package com.philiday.projectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.DoubleBuffer;
import java.text.DecimalFormat;

import static com.philiday.projectapplication.SQLiteHelper.TABLE_NAME_1;
import static com.philiday.projectapplication.SQLiteHelper.Table1_Column_5_userId;

public class SummaryActivity extends AppCompatActivity {

    String dista, time1, timeOfRun, userId, hours, minutes, seconds, walkingDist, ranDist, walkHours, walkMinutes, walkSeconds, runHours, runMinutes, runSeconds, totalPace, runningPace, walkingPace;

    TextView distance, time, date, username, walkTime, runTime, walkDist, runDist, overallPace, oRunningPace, oWalkingPace;
    TextView startLocation;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        sqLiteHelper = new SQLiteHelper(this);

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

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //distance = intent.getExtras(RecordingActivity);
        DecimalFormat distaf = new DecimalFormat("#.###");
        //   dista1 = distaf.format(dista);

        dista = (String) b.get("distance");
        time1 = (String) b.get("time");
        timeOfRun = (String) b.get("timeOfRun");
        userId = (String) b.get("Username");
        hours = (String) b.get("hours");
        minutes = (String) b.get("minutes");
        seconds = (String) b.get("seconds");
        walkingDist = (String) b.get("walkedDist");
        ranDist = (String) b.get("ranDist");
        date.setText(timeOfRun);
        distance.setText(dista);
        username.setText(userId);

        walkDist.setText(walkingDist);
        runDist.setText(ranDist);

        walkHours = (String) b.get("walkHoursTaken");
        walkMinutes = (String) b.get("walkMinutesTaken");
        walkSeconds = (String) b.get("walkSecondsTaken");

        runHours = (String) b.get("runHoursTaken");
        runMinutes = (String) b.get("runMinutesTaken");
        runSeconds = (String) b.get("runSecondsTaken");
      //  time.setText(hours + minutes + seconds);
        Log.i("dista", "dista" + dista);
        Log.i("username1", "username1" + userId);
        Log.i("hours", "hours " + hours);
        Log.i("minutes", "minutes" + minutes);
        Log.i("walkingDist", "walkingDist1" + walkingDist);

        Log.i("runningTime", "runningTime" + runMinutes + "mn(s)" + runSeconds + "s");
        Log.i("walkingTime", "walkingTime" + walkMinutes + "mn(s)" + walkSeconds + "s");

        dista = dista.replaceAll("miles","");

        walkingDist = walkingDist.replaceAll("miles","");


        ranDist = ranDist.replaceAll("miles","");


        //Only displays the relevant information - maybe make different TextViews to ensure correct positioning?
        if (hours.equals("0.0") && minutes.equals("0.0")) {

            totalPace = Double.toString((Double.parseDouble(seconds)/60)/ Double.parseDouble(dista));

            overallPace.setText(totalPace);
            time.setText(seconds + "s");
        } else if (hours.equals("0.0")) {

            totalPace = Double.toString(Double.parseDouble(minutes)+ (Double.parseDouble(seconds)/60) / (Double.parseDouble(dista)));
            overallPace.setText(totalPace);
            time.setText(minutes + "mn(s)" + seconds + "s");
        } else {

            totalPace = Double.toString(Double.parseDouble(hours)+Double.parseDouble(minutes)+(Double.parseDouble(seconds)/60) / (Double.parseDouble(dista)));
            overallPace.setText(totalPace);
            time.setText(hours + "h(s), " + minutes + "mn(s) " + seconds + "s");
        }

        if (walkHours.equals("0.0") && walkMinutes.equals("0.0")) {
            walkingPace = Double.toString((Double.parseDouble(walkSeconds)/60)/ Double.parseDouble(walkingDist));
            oWalkingPace.setText(walkingPace);
            walkTime.setText(walkSeconds + "s");
        } else if (walkHours.equals("0.0")) {
            walkingPace = Double.toString(Double.parseDouble(walkMinutes)+ (Double.parseDouble(walkSeconds)/60) / (Double.parseDouble(walkingDist)));
            oWalkingPace.setText(walkingPace);

            walkTime.setText(walkMinutes + "mn(s)" + walkSeconds + "s");
        } else {
            walkingPace = Double.toString(Double.parseDouble(walkHours)+Double.parseDouble(walkMinutes)+(Double.parseDouble(walkSeconds)/60) / (Double.parseDouble(walkingDist)));
            oWalkingPace.setText(walkingPace);

            walkTime.setText(walkHours + "h(s), " + walkMinutes + "mn(s) " + walkSeconds + "s");
        }

        if (runHours.equals("0.0") && runMinutes.equals("0.0")) {
            runningPace = Double.toString((Double.parseDouble(runSeconds)/60)/ Double.parseDouble(ranDist));
            oRunningPace.setText(runningPace);
            runTime.setText(runSeconds + "s");
        } else if (runHours.equals("0.0")) {
            runningPace = Double.toString(Double.parseDouble(runMinutes)+ (Double.parseDouble(runSeconds)/60) / (Double.parseDouble(ranDist)));
            oRunningPace.setText(runningPace);

            runTime.setText(runMinutes + "mn(s)" + runSeconds + "s");
        } else {
            runningPace = Double.toString(Double.parseDouble(runHours)+Double.parseDouble(runMinutes)+(Double.parseDouble(runSeconds)/60) / (Double.parseDouble(ranDist)));
            oRunningPace.setText(runningPace);
            runTime.setText(runHours + "h(s), " + runMinutes + "mn(s) " + runSeconds + "s");
        }

        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        RunDetails run = new RunDetails(userId, timeOfRun, dista, hours + "h(s), " + minutes + "mn(s) " + seconds + "s", walkingDist, ranDist, walkHours + "h(s), " + walkMinutes + "mn(s) " + walkSeconds + "s",runHours + "h(s), " + runMinutes + "mn(s) " + runSeconds + "s", totalPace, walkingPace, runningPace );
        sqLiteHelper.getWritableDatabase();
        long insertingRun = sqLiteHelper.createRun(run);

        ImageView btnSearch= (ImageView) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteRun(timeOfRun);
                goToTimeline(v);
            }
        });

    }

    //JUST TO SEE WHETHER THE DATABASE WAS WORKING CORRECTLY
    public static boolean columnExistsInTable(SQLiteDatabase db, String table, String columnToCheck) {
        Cursor cursor = null;
        try {
            //query a row. don't acquire db lock
            cursor = db.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);

            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1
            if (cursor.getColumnIndex(columnToCheck) != -1) {
                Log.i("mytag", "right:" + columnToCheck);

                //great, the column exists
                return true;
            }else {
                Log.i("mytag", "column:" + columnToCheck);
                return false;
            }

        } catch (SQLiteException Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.
            return false;
        } finally {
            //close the db  if you no longer need it
            if (db != null) db.close();
            //close the cursor
            if (cursor != null) cursor.close();
        }
    }

    //HERE I NEED TO TAKE THE DISTANCE AND TIME TAKEN FROM THE ACTIVITY RECORDING AND CALCULATE THE PACE OVERALL. USE BUNDLE 2 DO THIS
    public void goToTimeline(View view) {
        Intent intent = new Intent(this, timelineActivity.class);
        intent.putExtra("Username", userId);
        startActivity(intent);
    }




}
