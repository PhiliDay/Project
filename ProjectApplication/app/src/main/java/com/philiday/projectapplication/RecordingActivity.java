package com.philiday.projectapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Vibrator;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.ActivityRecognitionClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.hardware.Sensor.TYPE_GYROSCOPE;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;


public class RecordingActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback, SensorEventListener {

    private LocationManager locationManager;

    Button start, stop, save;
    TextView startime, timetaken, startlocation, endlocation, currentLoc,  pace, endtime, distance, currentPace, tv, walkingPace, username, runningPace, txtActivity, txtConfidence;
    TextView walkingDis, runningDis;
    int count;
    Intent intent;
    private float mLastX, mLastY, mLastZ;
    MarkerOptions mo1;
    double time=0;
    SensorManager _sensorManager;
    TextView _sensorTextView;
    long stime;
    long walkstime;
    long runstime;// start time in milliseconds
    long etime, walketime, runetime; // end time in milliseconds
    long dtime, walkTime, runTime; // duration in milliseconds
    long MillisecondTime;
    long startTime;
    long walkTimeBuff, walkingTime;
    long runTimeBuff, runningTime;
    byte[] bArray;
    ArrayList<Integer> xValuesAccelerometer = new ArrayList<>();
    ArrayList<Integer> yValuesAccelerometer = new ArrayList<>();
    ArrayList<Integer> zValuesAccelerometer = new ArrayList<>();

    double total = 0;
    double h= 0;
    double n= 0;
    double ss = 0;
    double hh = 0;
    double mn = 0;
    double s = 0;
    double rh = 0;
    double rmn = 0;
    double rs = 0;
    double wh = 0;
    double wmn = 0;
    double ws = 0;

    double averageXWalk;
    double averageYWalk;
    double averageZWalk;

    double varianceXWalk;
    double varianceYWalk;
    double varianceZWalk;

    double averageXRun;
    double averageYRun;
    double averageZRun;

    double varianceXRun;
    double varianceYRun;
    double varianceZRun;

    double SpeedWalk;
    double SpeedRun;

    float accelerometer;
    double averageX;
    double averageY;
    double averageZ;
    double varianceX;
    double varianceY;
    double varianceZ;


    double maxX;
    double maxY;
    double maxZ;

    double minX;
    double minY;
    double minZ;

    double Q1X;
    double Q3X;

    double Q1Y;
    double Q3Y;

    double Q1Z;
    double Q3Z;

String totalDistance;
String whms;
String rhms;
String ohms;

    int type;
    int confidence;

    private Location previousLocation;
    MarkerOptions mo;
    Marker marker;
    private GoogleMap mMap;

    String walkedDist;
    String ranDist;
    String totalDist;
    double speed=0;
    double dist = 0;
    double walked = 0;
    double ran = 0;

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
    Spinner mode;
    String db_mode;
    String db_result;
    Chronometer runStart;
    Chronometer walkStart;
    long timeWhenStopped = 0;
    long timeWhenStoppedRun = 0;

    private String TAG = RecordingActivity.class.getSimpleName();
    private Button btn;
    String finalActivity = "";
    String finalConfidence;
   // public static final String DETECTED_ACTIVITY = ".DETECTED ACTIVITY";
  //  private ActivityRecognitionClient mActivityRecognitionClient;
  //  private ActivitiesAdapter mAdapter;
  //  private Context mContext;

    private SensorManager sensorManager;
    private Vibrator v;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private float vibrateThreshold = 0;

    private ArrayList<LatLng> points; //added
    Polyline line; //added

    String db_username;
    SQLiteHelper db;

    boolean isRun = false;

    BroadcastReceiver broadcastReceiver;

    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    LocationListener listener;

    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private final float NOISE = (float) 2.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }else {setUpLocation();}

        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        points = new ArrayList<LatLng>(); //added

        List<String> list = new ArrayList<String>();
        list.add("Walking");
        list.add("Running");
        mode = (Spinner) findViewById(R.id.mode);

        btn = (Button) findViewById(R.id.runorwalk);
        btn.setBackgroundResource(R.drawable.walk);
        btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
        db_result = "Walking";
        getWalkTime();

        initViews();
        btn.setText("Walking");
        totalDistance = getDistance(dist);
        ranDist = getDistance(ran);
        walkedDist = getDistance(walked);

        ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(dataadapter);


        mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isSpinnerInitial = true;

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                db_mode = mode.getSelectedItem().toString();

                Toast.makeText(parent.getContext(),
                        parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();

            }

            public void onNothingSelected(AdapterView<?> paren) {

            }

        });




        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mo = new MarkerOptions().position(new LatLng(0, 0)).title("Current location");

        //This gets the username from the login and puts it in the db_username
        Intent in = getIntent();
        db_username = in.getStringExtra("Username");
        Log.i("mytag", "username" + db_username);
        db = new SQLiteHelper(this);


        stop.setEnabled(false); // stop button is disabled
        save.setEnabled(false);
        save.setVisibility(View.INVISIBLE);


        if (!isLocationEnabled()){
            Log.v("mytag", "No location");
            Toast.makeText(getApplicationContext(),
                    "Please Enable GPS",
                    Toast.LENGTH_SHORT).show();
         }


        // Click and start journey
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStart();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(isRun){
                    db_result = "Running";
                    v.setBackgroundResource(R.drawable.run);
                    btn.setText("Running");
                }else{
                    db_result = "Walking";
                    v.setBackgroundResource(R.drawable.walk);
                    btn.setText("Walking");
                }
                isRun = !isRun;
            }
        });

        LabeledSwitch labeledSwitch = findViewById(R.id.switches);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                if (isOn) {
                    clickStart();

                } else if (!isOn) {
                    clickStop();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStop();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave(v);


            }
        });


        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //Log.i("mytag", "broadcast2");
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {

                    int type1 = intent.getIntExtra("type1", -1);
                    int confidence1 = intent.getIntExtra("confidence1", 0);

                //    Log.i("mytag", "type11" + type1);
                  //  Log.i("mytag", "confidence22" + confidence1);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //  mo1 = new MarkerOptions().position(new LatLng(0, 0)).title("Current location");

        marker = mMap.addMarker(mo);
        Toast.makeText(getApplicationContext(),
                "Map Ready!",
                Toast.LENGTH_SHORT)
                .show();
        //marker = mMap.addMarker(mo1);


    }


    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng myCoordinates = new LatLng(latitude, longitude);
        points.add(myCoordinates); //added
        redrawLine(); //added

            if (location != null) {

                if (startLocation == null) {
                    Log.v("mytag", "LOCATION NULL");
                    startLocation = location;
                    latestLocation = location;
                    Log.v("mytag", "Latest Location" + latestLocation);

                    //Moves the map to the current location
                    marker.setPosition(myCoordinates);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 14));

                    startLocationString = "Coordinate: " + myCoordinates;
                    startlocation.setText(startLocationString);
                    currentLoc.setText(startLocationString);
                    dist = 0;
                    speed=0;

                }
                Toast.makeText(getApplicationContext(),
                        "Location4!",
                        Toast.LENGTH_SHORT)
                        .show();
                //While the user is still running get the distance
                //Maybe change this stop.isEnabled()??? - to if toggle = lets go
                if (stop.isEnabled()) {
                    Toast.makeText(getApplicationContext(),
                            "Location5!",
                            Toast.LENGTH_SHORT)
                            .show();
                    //speed = (double) location.getSpeed();
                   // if(speed > 0) {
                        //Add here also if gyroscope is this then walking
                        Log.i("speed", "speed: " + speed);

                        double averageSpeed = speed;
                        //
                        marker.setPosition(myCoordinates);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 14));
                        Log.i("mytag", "location: " + location);
                         long prevTime = latestLocation.getTime();
                         Log.i("timeLatestLocation ", " timeLatestLocation" + prevTime);
                         long currentTime = location.getTime();
                    Log.i("timeLocation ", " timeLocation" + currentTime);

                    // This is exactly what you want
                          long diffTime = currentTime - prevTime ;
                          Log.i("timeBetweenLocation", "timeBetweenLocation" + diffTime);
                        double d = distance(location.getLatitude(), location.getLongitude(), latestLocation.getLatitude(), latestLocation.getLongitude());
                        latestLocation = location;
                    comparingActivites();

                    speed = getMiles(d) / diffTime;
                    Log.i("speed", "speed" + speed);
                    Log.i("walkSpeed", "walkSpeed" + SpeedWalk);
                    Log.i("runSpeed", "runSpeed" + SpeedRun);

                    changingActivity();
                    xValuesAccelerometer.clear();
                    yValuesAccelerometer.clear();
                    zValuesAccelerometer.clear();
                        //decidingActivity();

                        if (finalActivity.equals("maybeWalking")) {
                            // if (averageSpeed < 2.5) {
                            Log.i("finalActivityWalking", "finalActivityWalking1 " + finalActivity);

                            //  if(db_result.equals("Walking")){
                            //  walkStartTime();
                            getWalkTime();
                            walkTime = walketime - stime;

                            Log.i("walktime", "walktime1" + walkTime);
                            wh = TimeUnit.MILLISECONDS.toHours(walkTime);
                            wmn = TimeUnit.MILLISECONDS.toMinutes(walkTime) - wh * 60;
                            ws = TimeUnit.MILLISECONDS.toSeconds(walkTime) - wh * 60 * 60 - wmn * 60;
                            Log.i("walktime", "walktime" + walketime);
                            Log.i("walktime", "walking" + wh + " h(s), " + wmn + " mn(s) " + ws + "s");

                            whms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(walkTime),
                                    TimeUnit.MILLISECONDS.toMinutes(walkTime) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(walkTime) % TimeUnit.MINUTES.toSeconds(1));

                        //    walked += walkingDist + d;
                            walked += d;
                            walkedDist = getDistance(walked);
                            Log.i("walkedDist", "walkedDist" + walkedDist);
                            walkingDis.setText(walkedDist);
                            double walkingSpeed = location.getSpeed();
                            Toast.makeText(getApplicationContext(),
                                    "Walking!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            //  }
                        }
                        if (finalActivity.equals("maybeRunning")) {
                            //if (averageSpeed > 2.5) {
                            Log.i("finalActivityRunning", "finalActivityRunning1 " + finalActivity);

                            //  if(db_result.equals("Running")){
                            //   runStartTime();

                            getRunTime();
                            runTime = runetime - stime;
                            runTime -= walkTime;

                            Log.i("runTime", "runTime" + runTime);
                            rh = TimeUnit.MILLISECONDS.toHours(runTime);
                            rmn = TimeUnit.MILLISECONDS.toMinutes(runTime) - rh * 60;
                            rs = TimeUnit.MILLISECONDS.toSeconds(runTime) - rh * 60 * 60 - rmn * 60;
                            Log.i("runtime", "runtime" + runetime);
                            Log.i("runtime", "runtime2" + runstime);

                            Log.i("runtime", "running" + rh + " h(s), " + rmn + " mn(s) " + rs + "s");
                            rhms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(runTime),
                                    TimeUnit.MILLISECONDS.toMinutes(runTime) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(runTime) % TimeUnit.MINUTES.toSeconds(1));


                           // ran += runningDist + d;
                            ran += d;
                            ranDist = getDistance(ran);
                            Log.i("randDist", "ranDist" + ranDist);
                            runningDis.setText(ranDist);
                            double runningSpeed = location.getSpeed();
                            Toast.makeText(getApplicationContext(),
                                    "Running!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        Log.i("maybeRunning", "maybeRunning but not fast speed " + speed);

                        //}
                        Log.i("maybeWalking", "maybeWalking but too fast speed " + speed);


                        if (finalActivity.equals("maybeStill") && !location.hasSpeed()) {
                            Log.i("finalActivityStill", "finalActivityStill1 " + finalActivity);
                            Toast.makeText(getApplicationContext(),
                                    "Still!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }

                        //  Log.i("mytag", "dist: " + dist);
                        Log.i("mytag", "latestLocation-location: " + latestLocation);
                        totalDistance = getDistance(walked + ran);
                        Log.i("totalDistance", "totalDistance" + totalDistance);
                        if (totalDistance.equals("null miles")) {
                            totalDistance = "0.0 miles";
                        }
                   // }

//                    // updating the max speed
//                    double newSpeed = location.getSpeed();
//                    findSpeed(newSpeed, speed);
//                    DecimalFormat df = new DecimalFormat("#.##");
//                    pace.setText(df.format(speed) + " m/s");
//
//                    //just the speed
//                    double currentSpeed = location.getSpeed();
//                    speed = currentSpeed * 26.8224;
//                    DecimalFormat df1 = new DecimalFormat("#.##");
//                    currentPace.setText(df1.format(speed) + "min/mile");
                }
            }
//
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        Toast.makeText(getApplicationContext(),
                "Found location 3",
                Toast.LENGTH_SHORT)                        .show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 15, this);



    }

    /**
     * Register for the updates when Activity is in foreground i.e. only use up the battery power if you have to..
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                15,
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
        mSensorManager.unregisterListener(this);

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


    public void handleUserActivity(int type, int confidence) {
        String label = getString(R.string.activity_unknown);
        String conLabel = "";
    //    Log.v("mytag", "label"+ label);

        switch (type) {
            case DetectedActivity.RUNNING: {
                label = getString(R.string.activity_running);
           //     Log.v("mytag", "activityRun"+ label);
             //   txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);
                break;
            }
            case DetectedActivity.STILL: {
                label = getString(R.string.activity_still);
                conLabel = Integer.toString(confidence);
              //  Log.v("mytag", "activityStill"+ label);
              //  txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);
                break;
            }

            case DetectedActivity.WALKING: {
                label = getString(R.string.activity_walking);
             //   Log.v("mytag", "activityWalk"+ label);
           //     txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);

                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = getString(R.string.activity_unknown);
               // Log.v("mytag", "activityUnknown"+ label);
            //    txtActivity.setText("Type: " + label);
              //  txtConfidence.setText("Confidence: " + confidence);

                break;
            }
        }

      //  Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);

        if (confidence > Constants.CONFIDENCE) {
            txtActivity.setText(label);
       //     finalActivity = String.valueOf(label);
        //    Log.i("finalActivity", "finalActivity3" + finalActivity);
//            txtConfidence.setText(confidence);
          //  finalConfidence = confidence;
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

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        //Radius of the earth in km: 6371km
        int earthRadius = 6371; // miles (or 6371.0 kilometers)
        double dLat = deg2rad(lat2-lat1);
        double dLng = deg2rad(lon2-lon1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = earthRadius * c;

        //without this - returns km
        double meterConversion = 1609;

        return d ;
    }

    private static double deg2rad(double deg){
        return deg * (Math.PI/180);
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
        }
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
                home.putExtra("Username", db_username);
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
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickStart() {

            save.setVisibility(View.INVISIBLE);
            boolean gpsEnabled = false;
            start.setEnabled(false);
            stop.setEnabled(true);



            //Take this away when actually delivering the application
//                startime.setText("starttime");
            //     endtime.setText("endtime");
            timetaken.setText("timetaken");
            distance.setText("distance");
            startlocation.setText("startlocation");
            // endlocation.setText("endlocation");
            currentLoc.setText("currentlocation");
            pace.setText("pace");
            currentPace.setText("currentPace");
            walkingPace.setText("walkingPace");
            runningPace.setText("runningPace");
            walkingDis.setText("walkingDis");
            runningDis.setText("runningDis");
            //  txtActivity.setText("txtActivity");
            //  txtConfidence.setText("txtConfidence");
            //Get start time
            calendarTime();
            //Other way of doing start time
            startTime();

            startTime = SystemClock.uptimeMillis();





            Log.i("myTag", "startLocationString" + startLocationString);
            startLocation(startLocationString);

    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }



    private void clickSave(View v){
             intent = new Intent(RecordingActivity.this, SummaryActivity.class);

        final GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                Bitmap image = snapshot;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("mapPhoto", byteArray);
            }
        };
            String distanc = getValue(dist);

            String walkHoursTaken = getValue(wh);
            String walkMinutesTaken = getValue(wmn);
            String walkSecondsTaken = getValue(ws);
            Log.i("dista", "dista" + distanc);

            if(walkHoursTaken.equals("null")){walkHoursTaken = "0.0";}
             if (walkMinutesTaken.equals("null")) {walkMinutesTaken = "0.0";}
             if(walkHoursTaken.equals("null")){walkHoursTaken = "0.0";}



        intent.putExtra("walkHoursTaken", walkHoursTaken);
            intent.putExtra("walkMinutesTaken", walkMinutesTaken);
            intent.putExtra("walkSecondsTaken", walkSecondsTaken);
          //  intent.putExtra("walkingTime", whms);

            String runHoursTaken = getValue(rh);
            String runMinutesTaken = getValue(rmn);
            String runSecondsTaken = getValue(rs);

        if(runSecondsTaken.equals("null")){runSecondsTaken = "0.0";}
        if (runMinutesTaken.equals("null")) {runMinutesTaken = "0.0";}
        if(runHoursTaken.equals("null")){runHoursTaken = "0.0";}

            intent.putExtra("runHoursTaken", runHoursTaken);
            intent.putExtra("runMinutesTaken", runMinutesTaken);
            intent.putExtra("runSecondsTaken", runSecondsTaken);
         //   intent.putExtra("runningTime", rhms);

            // String totalTime2 = getValue(totalTime);
            String hoursTaken = getValue(hh);
            String minutesTaken = getValue(mn);
            String secondsTaken = getValue(s);
            String calendarDate = ymd;

       // if(totalDistance.equals("null")){totalDistance = "0.0 miles"; }
            String finalDistance = totalDistance;

        // Log.v("mytag", "totalTime"+ totalTime2);
            Log.v("mytag", "distanc"+ finalDistance);
            Log.v("mytag", "walkedDist"+ walkedDist);
            Log.v("mytag", "ranDist"+ ranDist);


            intent.putExtra("ranDist", ranDist);
            intent.putExtra("walkedDist", walkedDist);

            intent.putExtra("distance", totalDistance);
            //intent.putExtra("time", totalTime2);
            intent.putExtra("timeOfRun", calendarDate);
            intent.putExtra("Username", db_username);
            intent.putExtra("hours", hoursTaken);
            intent.putExtra("minutes", minutesTaken);
            intent.putExtra("seconds", secondsTaken);
         //   intent.putExtra("overallTime", ohms);
         //   intent.putExtra("walkedDist", walkedDist);
            intent.putExtra("ranDist", ranDist);

            startActivity(intent);
        }


        private void startTime(){
            Chronometer duration = (Chronometer) findViewById(R.id.chronometer);
            stime = System.currentTimeMillis();
            duration.setBase(SystemClock.elapsedRealtime());
            duration.start();
        }

    private void walkStartTime(){
        Chronometer duration = (Chronometer) findViewById(R.id.chronometer);
        walkstime = System.currentTimeMillis();
        duration.setBase(SystemClock.elapsedRealtime());
        duration.start();
    }

    private void runStartTime(){
      //  Chronometer duration = (Chronometer) findViewById(R.id.chronometer);
        runstime = System.currentTimeMillis();
        //duration.setBase(SystemClock.elapsedRealtime());
       // duration.start();
    }

        private void stopTime(){
            Chronometer duration = (Chronometer) findViewById(R.id.chronometer);
            duration.stop();
        }

        //Is this being used?
        private void calendarTime(){
            Calendar cl = Calendar.getInstance();
            stime = cl.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        }

        private void startLocation(String startLocationString){
            if(startLocationString != null) {
             startlocation.setText(startLocationString);
                Log.i("myTag", ":" + startLocationString);

            }
        }

        private void getEndTime(){
            Calendar cl = Calendar.getInstance();
            etime = cl.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        }

        private void getRunTime(){
        Calendar cl = Calendar.getInstance();
        runetime = cl.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        }

        private void getWalkTime(){
        Calendar cl = Calendar.getInstance();
        walketime = cl.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        }

        private void redrawLine(){

            //mMap.clear();  //clears all Markers and Polylines

            PolylineOptions options = new PolylineOptions().width(5).color(Color.GREEN).geodesic(true);
            for (int i = 0; i < points.size(); i++) {
                LatLng point = points.get(i);
                options.add(point);
            }
           // addMarker(); //add Marker in current position
            line = mMap.addPolyline(options); //add Polyline
        }

        private void clickStop(){
            save.setVisibility(View.VISIBLE);

            stop.setEnabled(false);
            start.setEnabled(true);
            save.setEnabled(true);

            tv.setText("0:00:00");

            //Stop the timer
            stopTime();

            // Get end time
            getEndTime();

            // Get the duration
            dtime =etime -stime;
            hh = TimeUnit.MILLISECONDS.toHours(dtime);
            mn = TimeUnit.MILLISECONDS.toMinutes(dtime) - hh *60;
            s = TimeUnit.MILLISECONDS.toSeconds(dtime) - hh *60 * 60 - mn * 60;
            //  totalTime = hh+mn+s;
            timetaken.setText(hh+" h(s), " + mn +" mn(s) " + s + "s");

            //Get the date
            ymd = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm", Locale.getDefault()).format(new Date());

            //Get end location
            if(latestLocationString!=null) {
                //    endlocation.setText(latestLocationString);


                //   dist = latestLocation.distanceTo(startLocation);
            }
            else{
                dist = 0;
            }

            h = TimeUnit.MILLISECONDS.toHours(dtime);
            n = TimeUnit.MILLISECONDS.toMinutes(dtime) - h *60;
            ss = TimeUnit.MILLISECONDS.toSeconds(dtime) - h *60 * 60 - n * 60;

            ohms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(dtime),
                    TimeUnit.MILLISECONDS.toMinutes(dtime) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(dtime) % TimeUnit.MINUTES.toSeconds(1));

            double mileage = getMiles(walked);
            walkPace = (total / mileage)/60;
            // walkPace = walkPace/60;
            Log.e("Result a: ", String.valueOf(dtime));
            Log.e("Result s: ", String.valueOf(ss));
            Log.e("Result t: ", String.valueOf(total));
            Log.e("Result b: ", String.valueOf(mileage));
            Log.e("Result c: ", String.valueOf(walkPace));

            walkingPace.setText("" + walkPace);
            //   }

            if(ran == 0){
                runningPace.setText("rP");
            }else{
                runPace = dtime / ran;
                runningPace.setText("" + runningPace);
            }

        }

        private void initViews(){
            walkStart = (Chronometer) findViewById(R.id.chronometer);
            runStart = (Chronometer) findViewById(R.id.chronometer);

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
            currentLoc = (TextView) findViewById(R.id.updating);
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
        }





    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String sensorName = event.sensor.getName();

        float x = event.values[0];
        int xVal = (int) x;
        float y = event.values[1];
        int yVal = (int) y;
        float z = event.values[2];
        int zVal = (int) z;
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;

            mInitialized = true;
        } else {
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);

            if (deltaX < NOISE) deltaX = (float) 0.0;
            if (deltaY < NOISE) deltaY = (float) 0.0;
            if (deltaZ < NOISE) deltaZ = (float) 0.0;

            int xDel = (int) deltaX;
            int yDel = (int) deltaY;
            int zDel = (int) deltaZ;
            mLastX = x;
            mLastY = y;
            mLastZ = z;


            xValuesAccelerometer.add(xVal);
            yValuesAccelerometer.add(yVal);
            zValuesAccelerometer.add(zVal);

//            xValuesAccelerometer.add(xDel);
//            yValuesAccelerometer.add(yDel);
//            zValuesAccelerometer.add(zDel);

//        Log.i("myLastX", "myLastX " + mLastX);
//            Log.i("myLastY", "myLastY " + mLastY);
//            Log.i("myLastZ", "myLastZ " + mLastZ);
//            Log.i("deltaX", "deltaX " +deltaX);
//            Log.i("deltaY", "deltaY " + deltaY);
//            Log.i("deltaZ", "deltaZ " + deltaZ);


        }
    }

    private double calculateAverage(ArrayList<Integer> values){
        Integer sum = 0;
        if(!values.isEmpty()){
            for(Integer value: values){
                sum += value;
            }
            return sum.doubleValue() / values.size();
        }
        return sum;
    }

    private double calculateAverageInt(double max, double min){

            return (max + min)/2;

    }

        public float getActivityAce(float xValue, float yValue, float zValue){

           float acc_ave = myPow(((myPow(xValue, 2))+(myPow(yValue, 2))+(myPow(zValue, 2))), 0.5f);

           return acc_ave;
        }

    public float myPow(float x, float p) {
        double dblResult = Math.pow(x, p);
        float floatResult = (float)dblResult; // <-- Change to something safe. It may easily overflow.
        return floatResult;
    }

    public void changingActivity() {
        float accelerometer = getActivityAce(mLastX, mLastY, mLastZ);
        double averageX = calculateAverage(xValuesAccelerometer);
        double averageY = calculateAverage(yValuesAccelerometer);
        double averageZ = calculateAverage(zValuesAccelerometer);
        double varianceX = variance(xValuesAccelerometer);
        double varianceY = variance(yValuesAccelerometer);
        double varianceZ = variance(zValuesAccelerometer);
        Log.i("averageX", "averageX" + averageX);
        Log.i("averageY", "averageY" + averageY);
        Log.i("averageZ", "averageZ" + averageZ);
//        Log.i("varianceX", "varianceX" + varianceX);
//        Log.i("varianceY", "varianceY" + varianceY);
//        Log.i("varianceZ", "varianceZ" + varianceZ);
        sortList(xValuesAccelerometer);
        sortList(yValuesAccelerometer);
        sortList(zValuesAccelerometer);

        double maxX = xValuesAccelerometer.get(0);
        double minX = xValuesAccelerometer.get(0);


        double minY = yValuesAccelerometer.get(0);
        double maxY = yValuesAccelerometer.get(0);

        double minZ = zValuesAccelerometer.get(0);
        double maxZ = zValuesAccelerometer.get(0);

        findMinMax(xValuesAccelerometer, minX, maxX);
        findMinMax(yValuesAccelerometer, minY, maxY);
        findMinMax(zValuesAccelerometer, minZ, maxZ);


        double Q1X = calculateAverageInt(averageX, minX);
        double Q3X = calculateAverageInt(maxX, averageX);

        double Q1Y = calculateAverageInt(averageY, minY);
        double Q3Y = calculateAverageInt(maxY, averageY);

        double Q1Z = calculateAverageInt(averageZ, minZ);
        double Q3Z = calculateAverageInt(maxZ, averageZ);

        Log.i("Q1Y", "Q1Y" + Q1Y);
        Log.i("Q3Y", "Q3Y" + Q3Y);
        Log.i("Q1X", "Q1X" + Q1X);
        Log.i("Q3X", "Q3X" + Q3X);

        double finalY = Q1Y + Q3Y;

        double diffY = Q3Y - Q1Y;
        double diffX = Q3X - Q3Y;

        float accelerometer_bounds = 20;
        float gyrometer = 0;
        float gyrometer_bounds = 0;
        float stable_bounds = 0;

        if (varianceX < 1 && varianceX > -1){
            finalActivity = "maybeStill";
            Log.i("finalAct", "finalAct" + finalActivity);
        }else if(((averageX > Q1Y && averageX < Q3Y) || (averageY > Q1X && averageY < Q3X)) && Q3Y > 5) {
            finalActivity = "maybeRunning";
            Log.i("finalAct", "finalAct" + finalActivity);
        }else if(minX > maxY && Q3Y < 5){
            finalActivity = "maybeWalking";
            Log.i("finalAct", "finalAct" + finalActivity);
        }
    }

    public void decidingActivity(){
        if(maxY < minX){
            finalActivity = "maybeWalking";
        }
    }


    public double variance(ArrayList<Integer> values){
        double sumDiffsSquared = 0.0;
        double avg = calculateAverage(values);
        for(int value: values){
            double diff = value - avg;
            diff *= diff;
            sumDiffsSquared += diff;

        }
        return sumDiffsSquared / (values.size()-1);
    }

    public void sortList(ArrayList<Integer> values){
        Collections.sort(values);
    }

    public void maxList(ArrayList<Integer> values){
         Collections.max(values);
    }

    public void minList(ArrayList<Integer> values){
         Collections.min(values);
    }

    public void comparingActivites() {
        db = new SQLiteHelper(this);
        CalibrationDetails calWalk = db.getCalibration(db_username, CalibrationDetails.TABLE_NAME_3);

        CalibrationDetails calRun = db.getCalibration(db_username, CalibrationDetails.TABLE_NAME_4);

        averageXWalk = calWalk.getAverageX();
        averageYWalk = calWalk.getAverageY();
        averageZWalk = calWalk.getAverageZ();

        varianceXWalk = calWalk.getVarianceX();
        varianceYWalk = calWalk.getVarianceY();
        varianceZWalk = calWalk.getVarianceZ();

        averageXRun = calRun.getAverageX();
        averageYRun = calRun.getAverageY();
        averageZRun = calRun.getAverageZ();

        varianceXRun = calRun.getVarianceX();
        varianceYRun = calRun.getVarianceY();
        varianceZRun = calRun.getVarianceZ();

        SpeedWalk = calWalk.getSpeed();
        SpeedRun = calRun.getSpeed();
    }

    public void decidingActivityComparison(){
            comparingActivites();

        if(((averageX > Q1Y && averageX < Q3Y) || (averageY > Q1X && averageY < Q3X)) && Q3Y > 5){
            finalActivity = "maybeRunning";
            Log.i("finalAct", "finalAct" + finalActivity);
        }else if(Q3Y < 5){
            finalActivity = "maybeWalking";
            Log.i("finalAct", "finalAct" + finalActivity);
        }else if (varianceX < 1 && varianceX > -1){
            finalActivity = "maybeStill";
            Log.i("finalAct", "finalAct" + finalActivity);
        }

    }

    public void findMinMax(ArrayList<Integer> values, double min, double max){
        for(int i: values){
            if(i < min) min = i;
            if(i > max) max = i;
        }
    }




}

