package com.philiday.projectapplication;

import java.util.ArrayList;
import java.lang.reflect.Type;
import android.content.Context;
import android.content.Intent;
import android.app.IntentService;
import android.preference.PreferenceManager;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//Extend IntentService//
public class ActivityIntentService extends IntentService {
    protected static final String TAG = ActivityIntentService.class.getSimpleName();
    //Call the super IntentService constructor with the name for the worker thread//
    public ActivityIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
//Define an onHandleIntent() method, which will be called whenever an activity detection update is available//

    @Override
    protected void onHandleIntent(Intent intent) {

//If data is available, then extract the ActivityRecognitionResult from the Intent//
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

         //   Log.i("mylog", "result: " + result);

//Get an array of DetectedActivity objects//

            ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        for (DetectedActivity activity : detectedActivities) {
            Log.e(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
            broadcastActivity(activity);
        }

    }

    private void broadcastActivity(DetectedActivity activity){
        Intent intent = new Intent(Constants.BROADCAST_DETECTED_ACTIVITY);
        int type = intent.getIntExtra("type", -1);
        int type1 = activity.getType();
      //  Log.i("mylog", "type: " + type);
       // Log.i("mylog", "type1: " + type1);

        int confidence1 = activity.getConfidence();

        int confidence = intent.getIntExtra("confidence", 0);
       // Log.i("mylog", "confidence: " + confidence);
      //  Log.i("mylog", "confidence1: " + confidence1);

        intent.putExtra("type", type);
        intent.putExtra("type1", type1);
        intent.putExtra("confidence", confidence);
        intent.putExtra("confidence1", confidence1);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}
