package com.philiday.projectapplication;

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
import android.widget.TextView;
import android.widget.Toast;

public class timelineActivity extends AppCompatActivity {

    String EmailHolder;
    String username;
    TextView Email;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper db;
Cursor cursor;


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
        Log.i("mytag", "help" + EmailHolder);
        db = new SQLiteHelper(this);

        gettingDatabase(EmailHolder);
        // c = db.getUserDetails(EmailHolder);

//            String user1 = c.getString(0);
//            Log.i("mytag", "why?" + user1
//            );
//        while (c.moveToNext()) {
//
//            if (c.isFirst()) {
//
//                c.moveToFirst();
//                String user = c.getString(c.getColumnIndex(SQLiteHelper.Table_Column_ID));
//                Log.i("mytag", "?" + user);
//                Email.setText(Email.getText().toString() + " " + user);
//
//            }
//        }
        // get the user details from the database
//        Log.i("mytag", "why?" + c);
//        if(c == null){
//            Log.i("mytag", "WHYYY?");
//
//        }
          //  String user = c.getString(2);




            // Email.setText(getUsername());


    }

    public void gettingDatabase(String name){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = db.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{name}, null, null, null);
        Log.v("MYDB","Table TABLE_NAME has " +
                Integer.toString(cursor.getCount()) +
                " rows");

        for (int i=0; i < cursor.getColumnCount(); i++) {
            Log.v("MYDB", "Table TABLE_NAME has a column named " +
                    cursor.getColumnName(i)
            );
        }
        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();
                String user = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Email));

                // If Email is already exists then Result variable value set as Email Found.
                Log.i("mytag", "?"  + user);
                Email.setText(Email.getText().toString() + " " + user);

                // Closing cursor.
                cursor.close();
            }
        }


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
                home.putExtra("username", EmailHolder);
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
