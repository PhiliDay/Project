package com.philiday.projectapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    String EmailHolder, username;
    TextView Email;
    private PopupWindow pw;
    Point p;
    Button record;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        //Assigns Email to the textview email within activity_timeline (layout)
        Email = (TextView)findViewById(R.id.welcome);

        ListView listRun = (ListView) findViewById(R.id.runList);
        Intent intent = getIntent();

        record = findViewById(R.id.save2);

        EmailHolder = intent.getStringExtra("Username");
        // Log.i("mytag", "help" + EmailHolder);
        db = new SQLiteHelper(this);

        UserDetails userList = db.displayUser(EmailHolder);

        Email.setText("Welcome " + userList.getFirstName() + "!");


    }

    public void goToCalibration(View view){
        Intent intent = new Intent(this, CalibrationActivity.class);
        intent.putExtra("Username", EmailHolder);
        startActivity(intent);
    }

    public void goToRecord(View view){
        Log.i("EmailHolder", "Emailholder" + EmailHolder);
        if(checkCalibrated(EmailHolder)) {
            Intent intent = new Intent(this, RecordingActivity.class);
            intent.putExtra("Username", EmailHolder);
            startActivity(intent);
        }
    }

    public void goToResults(View view){
        Intent intent = new Intent(this, timelineActivity.class);
        intent.putExtra("Username", EmailHolder);
        startActivity(intent);
    }

    public boolean checkCalibrated(String userId){
        db = new SQLiteHelper(this);

        boolean check = db.calCheck(userId);

        if(check == true){
            return true;
        }else if(check == false){
            showPopup(WelcomeActivity.this, p);
            return false;
        }
        return false;
    }

    private void showPopup(final Activity context, Point p) {
        int popupWidth = 600;
        int popupHeight = 600;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup_1);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_popup, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent calibrate = new Intent(getApplicationContext(), CalibrationActivity.class);
                calibrate.putExtra("Username", EmailHolder);
                startActivity(calibrate);
            }
        });
    }




}
