package com.philiday.projectapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class timelineActivity extends AppCompatActivity {

    String EmailHolder;
    TextView Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Email = (TextView)findViewById(R.id.email);

        Intent intent = getIntent();

        EmailHolder = intent.getStringExtra("Useremail");
        Email.setText(Email.getText().toString() + " " + EmailHolder);
    }

    public void startRun(View view) {
        Intent intent = new Intent(this, RecordingActivity.class);
        intent.putExtra("Username", EmailHolder);
        Log.i("username", "email"+ EmailHolder);
        startActivity(intent);
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }
}
