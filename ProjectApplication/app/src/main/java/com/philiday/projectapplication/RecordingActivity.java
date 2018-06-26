package com.philiday.projectapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionClient;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.model.LatLng;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class RecordingActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    Button start, stop, save;
    TextView startime, timetaken, startlocation, endlocation, currentLoc,  pace, endtime, distance, currentPace, tv, walkingPace, username, runningPace, txtActivity, txtConfidence;
    TextView walkingDis, runningDis;

    long stime; // start time in milliseconds
    long etime; // end time in milliseconds
    long dtime; // duration in milliseconds

    double total = 0;
    double totalTime=0;
    double h= 0;
    double n= 0;
    double ss = 0;
    double hh = 0;
    double mn = 0;
    double s = 0;

    int type;
    int confidence;


    double speed=0;
    double dist = 0;
    double walkingDist = 0;
    double runningDist = 0;
    double walkPace = 0;
    double runPace = 0;
     String latestLocationString;
    static String startLocationString;

    String formattedDate;
    String ymd;
    Location startLocation;
    Location latestLocation;

    Timer t = new Timer();
    public int seconds = 0;
    public int minutes = 0;
    public int hour = 0;

    private String TAG = RecordingActivity.class.getSimpleName();

   // public static final String DETECTED_ACTIVITY = ".DETECTED ACTIVITY";
  //  private ActivityRecognitionClient mActivityRecognitionClient;
  //  private ActivitiesAdapter mAdapter;
  //  private Context mContext;

    String db_username;
    SQLiteHelper db;

    BroadcastReceiver broadcastReceiver;

    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

//        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()){
//            requestPermissions(PERMISSIONS, PERMISSION_ALL);
//        } else setUpLocation();
       // askPermission();
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        } else setUpLocation();
     //   setUpLocation();

        //This gets the username from the login and puts it in the db_username
        Intent in = getIntent();
        db_username = in.getStringExtra("Username");
        Log.i("mytag", "username" + db_username);
        db = new SQLiteHelper(this);

        //startime = (TextView) findViewById(R.id.startime);
        distance = (TextView) findViewById(R.id.distance);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        save = (Button) findViewById(R.id.save);
       // endtime = (TextView) findViewById(R.id.endtime);
        timetaken = (TextView) findViewById(R.id.timetaken);
        distance = (TextView) findViewById(R.id.distance);
        startlocation = (TextView) findViewById(R.id.starting);
       // endlocation = (TextView) findViewById(R.id.endLocation);
       //
        // currentLoc = (TextView) findViewById(R.id.updating);
        pace = (TextView) findViewById(R.id.pace);
        currentPace = (TextView) findViewById(R.id.currentPace);
        tv = (TextView) findViewById(R.id.timer);
        walkingPace = (TextView) findViewById(R.id.walkingPace);
        runningPace = (TextView) findViewById(R.id.runningPace);

        walkingDis = (TextView) findViewById(R.id.walkingDis);
        runningDis = (TextView) findViewById(R.id.runningDis);
        username = (TextView) findViewById(R.id.username);

        txtActivity = findViewById(R.id.txt_activity);
        txtConfidence = findViewById(R.id.txt_confidence);

        stop.setEnabled(false); // stop button is disabled
        save.setEnabled(false);


        if (!isLocationEnabled()){
            Log.v("mytag", "No location");
         }

//        BottomNavigationView item = findViewById(R.id.navigation_bin);
//        setIcon.item= R.drawable.bin;



      //  navigation.getMenu().findItem(R.id.navigation_record).getIcon().setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);


        // Click and start journey
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                stop.setEnabled(true);

                //Take this away when actually delivering the application
//                startime.setText("starttime");
           //     endtime.setText("endtime");
                timetaken.setText("timetaken");
                distance.setText("distance");
                startlocation.setText("startlocation");
               // endlocation.setText("endlocation");
              //  currentLoc.setText("currentlocation");
                pace.setText("pace");
                currentPace.setText("currentPace");
                walkingPace.setText("walkingPace");
                runningPace.setText("runningPace");
                walkingDis.setText("walkingDis");
                runningDis.setText("runningDis");
              //  txtActivity.setText("txtActivity");
              //  txtConfidence.setText("txtConfidence");

                //Get start time
                Calendar cl = Calendar.getInstance();
                stime = cl.getTimeInMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
              //  startime.setText(sdf.format(cl.getTime()));

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
              //  endtime.setText(sdf.format(cl.getTime()));

                // Get the duration
                dtime =etime -stime;
                 hh = TimeUnit.MILLISECONDS.toHours(dtime);
                 mn = TimeUnit.MILLISECONDS.toMinutes(dtime) - hh *60;
                 s = TimeUnit.MILLISECONDS.toSeconds(dtime) - hh *60 * 60 - mn * 60;
              //  totalTime = hh+mn+s;
                timetaken.setText(hh+" h(s), " + mn +" mn(s) " + s + "s");

                //Get the date
                 ymd = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss", Locale.getDefault()).format(new Date());


                //Get end location
                if(latestLocationString!=null) {
                //    endlocation.setText(latestLocationString);

                 //   dist = latestLocation.distanceTo(startLocation);
                }
                else{
                    dist = 0;
                }

                distance.setText(getDistance(dist));
               // walkingDist = 1609.34;

                //Finding out the pace that you completed your walk. Need to do 0 otherwise you get infinity
                if(walkingDist == 0){
                    walkingPace.setText("distanceiszero");
                }else {
                     h = TimeUnit.MILLISECONDS.toHours(dtime);
                     n = TimeUnit.MILLISECONDS.toMinutes(dtime) - h *60;
                     ss = TimeUnit.MILLISECONDS.toSeconds(dtime) - h *60 * 60 - n * 60;

           //         double seconds = ss*0.016667;

                    ///Need to add here what happens if i get to the hour mark? - * 60 cause otherwise it just adds 2
                  //   total = h+n+ss;

                    double mileage = getMiles(walkingDist);
                    walkPace = (total / mileage)/60;
                   // walkPace = walkPace/60;
                    Log.e("Result a: ", String.valueOf(dtime));
                    Log.e("Result s: ", String.valueOf(ss));
                    Log.e("Result t: ", String.valueOf(total));
                    Log.e("Result b: ", String.valueOf(mileage));
                    Log.e("Result c: ", String.valueOf(walkPace));

                    walkingPace.setText("" + walkPace);
                }

                if(runningDist == 0){
                    runningPace.setText("rP");
                }else{
                    runPace = dtime / runningDist;
                    runningPace.setText("" + runningPace);
                }

            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordingActivity.this, SummaryActivity.class);

                String distanc = getValue(dist);
               // String totalTime2 = getValue(totalTime);
                String hoursTaken = getValue(hh);
                String minutesTaken = getValue(mn);
                String secondsTaken = getValue(s);
                String calendarDate = ymd;

               // Log.v("mytag", "totalTime"+ totalTime2);
                Log.v("mytag", "distanc"+ distanc);
                intent.putExtra("distance", distanc);
                //intent.putExtra("time", totalTime2);
                intent.putExtra("timeOfRun", calendarDate);
                intent.putExtra("Username", db_username);
                intent.putExtra("hours", hoursTaken);
                intent.putExtra("minutes", minutesTaken);
                intent.putExtra("seconds", secondsTaken);
                startActivity(intent);
            }
        });



        //SHOULD TELL ME HOW WHETHER IM RUNNING OR WALKING !!!
        broadcastReceiver = new BroadcastReceiver() {

            @Override

            public void onReceive(Context context, Intent intent) {
                Log.i("mytag", "broadcast2");

                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {

                    int type1 = intent.getIntExtra("type1", -1);
                    int confidence1 = intent.getIntExtra("confidence1", 0);

                    Log.i("mytag", "type11" + type1);
                    Log.i("mytag", "confidence22" + confidence1);

                    handleUserActivity(type1, confidence1);

                }
            }
        };

        startTracking();


    }

    public String getValue(Double value){
        String item = String.valueOf(value);
        return item;
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
             //   currentLoc.setText(startLocationString);
                dist = 0;
            }




            //While the user is still running get the distance
            if(stop.isEnabled()) {
                double activitySpeed = location.getSpeed();
                //Add here also if gyroscope is this then walking

                double dist = distance(location.getLatitude(), location.getLongitude(), latestLocation.getLatitude(), latestLocation.getLongitude());

                if(activitySpeed < 1.4){

                walkingDist = walkingDist + dist;
                walkingDis.setText(getDistance(walkingDist));
                } else{
                        runningDist = runningDist + dist;
                        runningDis.setText(getDistance(runningDist));
                }

                // updating the max speed
                double newSpeed = location.getSpeed();
                findSpeed(newSpeed, speed);
                DecimalFormat df = new DecimalFormat("#.##");
                pace.setText(df.format(speed) + " m/s");

                //just the speed
                double currentSpeed = location.getSpeed();
                speed = currentSpeed * 26.8224;
                DecimalFormat df1 = new DecimalFormat("#.##");
                currentPace.setText(df1.format(speed) + "min/mile");
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

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY));

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
        Log.i("distance", "d" + d);

        return d;
    }

    public void goToSummaryPage(View view) {
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

//    public void broadcastIntent(View view){
//        Intent intent = new Intent();
//        intent.setAction("com.tutorialspoint.CUSTOM_INTENT");
//        sendBroadcast(intent);
//    }

    public void handleUserActivity(int type, int confidence) {
        String label = getString(R.string.activity_unknown);
        String conLabel = "";
    //    Log.v("mytag", "label"+ label);

        switch (type) {
            case DetectedActivity.RUNNING: {
                label = getString(R.string.activity_running);
                Log.v("mytag", "activityRun"+ label);
             //   txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);
                break;
            }
            case DetectedActivity.STILL: {
                label = getString(R.string.activity_still);
                conLabel = Integer.toString(confidence);
                Log.v("mytag", "activityStill"+ label);
              //  txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);
                break;
            }

            case DetectedActivity.WALKING: {
                label = getString(R.string.activity_walking);
                Log.v("mytag", "activityWalk"+ label);
           //     txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);

                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = getString(R.string.activity_unknown);
                Log.v("mytag", "activityUnknown"+ label);
            //    txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);

                break;
            }
        }

        Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);

        if (confidence > Constants.CONFIDENCE) {
            txtActivity.setText(label);
            txtConfidence.setText(conLabel);
        }
    }

    private boolean isLocationEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isPermissionGranted() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("mylog", "Permission granted");
            return true;
        } else {
            Log.v("mylog", "Permission not granted");
            return false;
        }
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        //Radius of the earth in km: 6371km
        double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lon2-lon1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = earthRadius * c;


        return d;
    }

    private static double findSpeed(double newSpeed, double speed)
    {
        if (newSpeed > speed) {
            speed = newSpeed;
        }
        return speed;
    }

    private void askPermission(){
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        } else setUpLocation();
    }

    private void startTracking() {
        Intent intent1 = new Intent(RecordingActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent1);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Home!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent home = new Intent(this, timelineActivity.class);
                startActivity(home);
                return true;
            case R.id.navigation_record:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Record!",
                        Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.navigation_logout:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Logout!",
                        Toast.LENGTH_SHORT)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
