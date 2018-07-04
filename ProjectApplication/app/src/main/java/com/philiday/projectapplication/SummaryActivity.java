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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import static com.philiday.projectapplication.SQLiteHelper.TABLE_NAME_1;
import static com.philiday.projectapplication.SQLiteHelper.Table1_Column_5_userId;

public class SummaryActivity extends AppCompatActivity {

    String dista, time1, timeOfRun, userId, hours, minutes, seconds, walkingDist, ranDist;

    TextView distance, time, date, username;
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
      //  time.setText(hours + minutes + seconds);

        Log.i("username1", "username1" + userId);
        Log.i("hours", "hours " + hours);
        Log.i("minutes", "minutes" + minutes);
        Log.i("walkingDist", "walkingDist1" + walkingDist);


        //Only displays the relevant information - maybe make different TextViews to ensure correct positioning?
        if (hours.equals("0.0") && minutes.equals("0.0")) {
            time.setText(seconds + "s");
        } else if (hours.equals("0.0")) {
            time.setText(minutes + "mn(s)" + seconds + "s");
        } else {
            time.setText(hours + "h(s), " + minutes + "mn(s) " + seconds + "s");
        }

        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        RunDetails run = new RunDetails(userId, timeOfRun, dista, hours + "h(s), " + minutes + "mn(s) " + seconds + "s", walkingDist, ranDist);
        sqLiteHelper.getWritableDatabase();
        long insertingRun = sqLiteHelper.createRun(run);

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
