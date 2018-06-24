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

import static com.philiday.projectapplication.SQLiteHelper.TABLE_NAME_1;
import static com.philiday.projectapplication.SQLiteHelper.Table1_Column_5_userId;

public class SummaryActivity extends AppCompatActivity {

    String walkingDistanceHolder;
    String timeHolder, dista, time1, timeOfRun, userId, runId;


    TextView distance, time, date, username;
    TextView startLocation;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        sqLiteHelper = new SQLiteHelper(this);

        startLocation = (TextView)findViewById(R.id.startLoc);
        distance = (TextView)findViewById(R.id.distance);
        time = (TextView)findViewById(R.id.time);
        date = (TextView)findViewById(R.id.date);
        username = (TextView)findViewById(R.id.username);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        //distance = intent.getExtras(RecordingActivity);
         dista = (String)b.get("distance");
         time1 = (String)b.get("time");
         timeOfRun = (String)b.get("timeOfRun");
         userId = (String)b.get("Username");
         Log.i("username1", "username1" + userId);

         date.setText(timeOfRun);
        distance.setText(dista);
        time.setText(time1);
        username.setText(userId);
      //  startLocation.setText(startLocation.getText().toString() + walkingDistanceHolder);
      //  SQLiteDataBaseBuild();
      //  SQLiteTableBuild();
//        columnExistsInTable(sqLiteDatabaseObj, TABLE_NAME_1, SQLiteHelper.Table1_Column_1_date);
//        columnExistsInTable(sqLiteDatabaseObj, TABLE_NAME_1, SQLiteHelper.Table1_Column_ID);

        InsertDataIntoSQLiteDatabase();

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_1 + "(" + SQLiteHelper.Table1_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table1_Column_1_date + " VARCHAR(100) NOT NULL UNIQUE, " + SQLiteHelper.Table1_Column_2_distance + " INTEGER NOT NULL, " + SQLiteHelper.Table1_Column_3_time + " INTEGER NOT NULL," +
                SQLiteHelper.Table1_Column_5_userId+" INTEGER NOT NULL, FOREIGN KEY (" + SQLiteHelper.Table1_Column_5_userId+ ") REFERENCES " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + "));");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase() {
        long a;
        Cursor cursor = null;


        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
    //Need to make total time insert into the database - managed to get it to display on the screen * SUCCESSFULLY TRANSFERRED*

            ContentValues contentValues = new ContentValues();
       //     contentValues.put("runId", runId);
        contentValues.put("userId", userId);
        contentValues.put("timeOfRun", timeOfRun);
        contentValues.put("distance", dista);
            contentValues.put("time", time1);
            a = sqLiteHelper.insert(TABLE_NAME_1, contentValues, SQLiteHelper.Table1_Column_ID);

            if (a > 0) {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabaseObj.close();

    }

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
