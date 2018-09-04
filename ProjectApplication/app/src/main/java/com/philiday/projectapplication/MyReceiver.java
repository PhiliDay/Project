package com.philiday.projectapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/* Was used to implement the Activity Recognition API - not used in final application */


public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        int type = intent.getIntExtra("type", -1);
        int confidence = intent.getIntExtra("confidence", 0);
     //   RecordingActivity.handleUserActivity(type, confidence);
        Log.i("type", "type" + type);
        Log.i("confidnece", "confidence " + confidence);
    }
}