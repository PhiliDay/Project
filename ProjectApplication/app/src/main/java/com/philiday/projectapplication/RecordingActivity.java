package com.philiday.projectapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RecordingActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    Button start, stop, save;
    TextView startime, timetaken, startlocation, endlocation, currentLoc,  pace, endtime, distance, currentPace, tv, walkingPace, runningPace;
    TextView walkingDis, runningDis;

    long stime; // start time in milliseconds
    long etime; // end time in milliseconds
    long dtime; // duration in milliseconds

    double speed=0;
    double dist = 0;
    double walkingDist = 0;
    double runningDist = 0;
    double walkPace = 0;
    double runPace = 0;
     String latestLocationString;
    static String startLocationString;
    String walkingDistanceHolder;

    Location startLocation;
    Location latestLocation;

    Timer t = new Timer();
    public int seconds = 0;
    public int minutes = 0;
    public int hour = 0;

    public static final String DETECTED_ACTIVITY = ".DETECTED ACTIVITY";
    private ActivityRecognitionClient mActivityRecognitionClient;
    private ActivitiesAdapter mAdapter;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        setUpLocation();

        mContext = this;

        ListView detectedActivitiesListView = (ListView) findViewById(R.id.activities_listview);

        ArrayList<DetectedActivity> detectedActivities = ActivityIntentService.detectedActivitiesFromJson(
                PreferenceManager.getDefaultSharedPreferences(this).getString(
                        DETECTED_ACTIVITY, ""));

        mAdapter = new ActivitiesAdapter(this, detectedActivities);
        detectedActivitiesListView.setAdapter(mAdapter);
        mActivityRecognitionClient = new ActivityRecognitionClient(this);

        startime = (TextView) findViewById(R.id.startime);
        distance = (TextView) findViewById(R.id.distance);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        save = (Button) findViewById(R.id.save);
        endtime = (TextView) findViewById(R.id.endtime);
        timetaken = (TextView) findViewById(R.id.timetaken);
        distance = (TextView) findViewById(R.id.distance);
        startlocation = (TextView) findViewById(R.id.starting);
        endlocation = (TextView) findViewById(R.id.endLocation);
        currentLoc = (TextView) findViewById(R.id.updating);
        pace = (TextView) findViewById(R.id.pace);
        currentPace = (TextView) findViewById(R.id.currentPace);
        tv = (TextView) findViewById(R.id.timer);
        walkingPace = (TextView) findViewById(R.id.walkingPace);
        walkingDis = (TextView) findViewById(R.id.walkingDis);
        runningDis = (TextView) findViewById(R.id.runningDis);


        stop.setEnabled(false); // stop button is disabled
        save.setEnabled(false);

        // Click and start journey
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                stop.setEnabled(true);
                startime.setText("starttime");
                endtime.setText("endtime");
                timetaken.setText("timetaken");
                distance.setText("distance");
                startlocation.setText("startlocation");
                endlocation.setText("endlocation");
                currentLoc.setText("currentlocation");
                pace.setText("pace");
                currentPace.setText("currentPace");
//                walkingPace.setText("walkingPace");
                walkingDis.setText("walkingDis");
                runningDis.setText("runningDis");


                //Get start time
                Calendar cl = Calendar.getInstance();
                stime = cl.getTimeInMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                startime.setText(sdf.format(cl.getTime()));

                Log.i("myTag", "startLocationString" + startLocationString);
                if (startLocationString != null) {
                    startlocation.setText(startLocationString);
                    Log.i("myTag", ":" + startLocationString);

                }


            }
        });

        //Click and stop journey
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setEnabled(false);
                start.setEnabled(true);
                save.setEnabled(true);

                tv.setText("0:00:00");

                // Get end time
                Calendar cl = Calendar.getInstance();
                etime = cl.getTimeInMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                endtime.setText(sdf.format(cl.getTime()));

                // Get the duration
                dtime =etime -stime;
                long hh = TimeUnit.MILLISECONDS.toHours(dtime);
                long mn = TimeUnit.MILLISECONDS.toMinutes(dtime) - hh *60;
                long s = TimeUnit.MILLISECONDS.toSeconds(dtime) - hh *60 * 60 - mn * 60;
                timetaken.setText(hh+" h(s), " + mn +" mn(s) " + s + "s");


                //Get end location
                if(latestLocationString!=null) {
                    endlocation.setText(latestLocationString);

                 //   dist = latestLocation.distanceTo(startLocation);
                }
                else{
                    dist = 0;
                }

                distance.setText(getDistance(dist));

                //Finding out the pace that you completed your walk. Need to do 0 otherwise you get infinity
                if(walkingDist == 0){
                    walkingPace.setText("distanceiszero");
                }else {
                    long h = TimeUnit.MILLISECONDS.toHours(dtime);
                    long n = TimeUnit.MILLISECONDS.toMinutes(dtime) - h *60;
                    long ss = TimeUnit.MILLISECONDS.toSeconds(dtime) - h *60 * 60 - n * 60;

                    double seconds = ss*0.016667;

                    ///Need to add here what happens if i get to the hour mark? - * 60 cause otherwise it just adds 2
                    double total = h+(n*60)+seconds;

                    double mileage = getMiles(walkingDist);

                    walkPace = total / walkingDist;
                    Log.e("Result a: ", String.valueOf(dtime));
                    Log.e("Result s: ", String.valueOf(ss));
                    Log.e("Result t: ", String.valueOf(total));
                    Log.e("Result b: ", String.valueOf(mileage));
                    Log.e("Result c: ", String.valueOf(walkPace));

                    walkingPace.setText("" + walkPace);
                }

//                if(runningDist == 0){
//                    runningPace.setText("");
//                }else{
//                    runPace = dtime / runningDist;
//                    runningPace.setText("" + runningPace);
//                }

            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordingActivity.this, SummaryActivity.class);

                intent.putExtra(startLocationString, walkingDistanceHolder);
                startActivity(intent);
            }
        });

        }


    //Converts numeric degrees to radians
    private Double toRadians(Double value) {
        return value * Math.PI / 180;
    }

    public void onLocationChanged(Location location) {

        if (location != null) {
            if (startLocation == null && stop.isEnabled()) {
                Log.v("mytag", "LOCATION NULL");
                startLocation = location;
                latestLocation = location;
                LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
                startLocationString = "Coordinate: " + myCoordinates;
                startlocation.setText(startLocationString);
                //Puts in my start location as current loc -- this is later updated
                currentLoc.setText(startLocationString);
                dist = 0;
            }


            //While the user is still running get the distance
            if(stop.isEnabled()) {
                double activitySpeed = location.getSpeed();
                //Add here also if gyroscope is this then walking


                //Radius of the earth in km: 6367km
                double Rad = 6368;
                //Find the distance between two points (lang & long) - Haversine formula
                double dlong = toRadians(location.getLongitude() - latestLocation.getLongitude());
                double dlat = toRadians(location.getLatitude() - latestLocation.getLatitude());
                double a =
                        Math.pow(Math.sin(toRadians(dlat) / 2.0), 2)
                                + Math.cos(toRadians(latestLocation.getLatitude()))
                                * Math.cos(toRadians(location.getLatitude()))
                                * Math.pow(Math.sin(toRadians(dlong) / 2.0), 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double d = Rad * c;

                if(activitySpeed < 1.4){

                walkingDist = walkingDist + d;
                walkingDis.setText(getDistance(walkingDist));
                } else{
                        runningDist = runningDist + d;
                        runningDis.setText(getDistance(runningDist));
                }


//                //Radius of the earth in km: 6367km
//                double Rad = 6368;
//                //Find the distance between two points (lang & long) - Haversine formula
//                double dlong = toRadians(location.getLongitude() - latestLocation.getLongitude());
//                double dlat = toRadians(location.getLatitude() - latestLocation.getLatitude());
//                double a =
//                        Math.pow(Math.sin(toRadians(dlat) / 2.0), 2)
//                                + Math.cos(toRadians(latestLocation.getLatitude()))
//                                * Math.cos(toRadians(location.getLatitude()))
//                                * Math.pow(Math.sin(toRadians(dlong) / 2.0), 2);
//                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//                double d = Rad * c;
//                dist = dist + d;
//                distance.setText(getDistance(dist));
//
//                latestLocation = location;
//                LatLng latestCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
//                latestLocationString = "Latest coordinate: " + latestCoordinates;
//             //   currentLoc.setText(latestLocationString);


                // updating the max speed
                double newSpeed = location.getSpeed();
                if (newSpeed > speed) {
                    speed = newSpeed;
                    DecimalFormat df = new DecimalFormat("#.##");
                    pace.setText(df.format(speed) + " m/s");
                }

                //just the speed
                double currentSpeed = location.getSpeed();
                speed = currentSpeed * 26.8224;
                DecimalFormat df = new DecimalFormat("#.##");
                currentPace.setText(df.format(speed) + "min/mile");
            }
        }
//
//        }

    }
    public void onProviderDisabled(String provider) {
        // Code to do something if location provider is disabled e.g. display error
    }

    public void onProviderEnabled(String provider) {
        // Code to do something if location provider becomes available e.g check if it’s a  more useful provider
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Code to do something if location provider status changes..
    }


    // custom method
    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                5,
                this);
    }

    /**
     * Register for the updates when Activity is in foreground i.e. only use up the battery power if you have to..
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5,
                this);

//        PreferenceManager.getDefaultSharedPreferences(this)
//                .registerOnSharedPreferenceChangeListener(this);
     //   updateDetectedActivitiesList();
    }


    /**
     * Stop the updates when Activity is paused i.e. save your battery
     */
    @Override
    protected void onPause() {
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.removeUpdates(this);
    }

    public String getDistance(double dist){
        //dist - convert from m to miles.
        String finalDistance = "";
        double d = 0;
        String unit = "miles";

        d = dist * 0.6214; //Conversion of m to miles

        DecimalFormat df = new DecimalFormat("#.###");
        finalDistance = df.format(d) + " " + unit;

        return finalDistance;
    }

    public double getMiles(double dist){
        //dist - convert from m to miles.
        double d = 0;
       // String unit = "miles";

        d = dist * 0.6214; //Conversion of m to miles

      //  DecimalFormat df = new DecimalFormat("#.###");

        return d;
    }

    public void goToSummaryPage(View view) {
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

//    public void requestUpdatesHandler(View view) {
////Set the activity detection interval. I’m using 3 seconds//
//        Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
//                3000,
//                getActivityDetectionPendingIntent());
//        task.addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void result) {
//                updateDetectedActivitiesList();
//            }
//        });
//    }
    //Get a PendingIntent//
//    private PendingIntent getActivityDetectionPendingIntent() {
////Send the activity data to our DetectedActivitiesIntentService class//
//        Intent intent = new Intent(this, ActivityIntentService.class);
//        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//    }
    //Process the list of activities//
//    protected void updateDetectedActivitiesList() {
//        ArrayList<DetectedActivity> detectedActivities = ActivityIntentService.detectedActivitiesFromJson(
//                PreferenceManager.getDefaultSharedPreferences(mContext)
//                        .getString(DETECTED_ACTIVITY, ""));
//
//        mAdapter.updateActivities(detectedActivities);
//    }

}