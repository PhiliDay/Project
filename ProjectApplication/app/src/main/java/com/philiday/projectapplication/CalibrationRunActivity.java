package com.philiday.projectapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

import static com.philiday.projectapplication.RecordingActivity.PERMISSIONS;
import static com.philiday.projectapplication.RecordingActivity.PERMISSION_ALL;

public class CalibrationRunActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

        Button start, save;
        ArrayList<Integer> xValuesAccelerometer = new ArrayList<>();
        ArrayList<Integer> yValuesAccelerometer = new ArrayList<>();
        ArrayList<Integer> zValuesAccelerometer = new ArrayList<>();
        private boolean mInitialized;
        private final float NOISE = (float) 2.0;

        private float deltaX = 0;
        private float deltaY = 0;
        private float deltaZ = 0;
        private float mLastX, mLastY, mLastZ;
        double averageX;
        double averageY;
        double averageZ;
        double varianceX;
        double varianceY;
        double varianceZ;
        double minX;
        double minY;
        double minZ;
        double maxX;
        double maxY;
        double maxZ;
        double Q1X;
        double Q3X;
        double Q1Y;
        double Q3Y;
        double Q1Z;
        double Q3Z;
        float lat_a;
        float lon_b;
    float latitude;
    float longitude;
    BroadcastReceiver broadcastReceiver2;


    SQLiteHelper sqLiteHelper;
        String userId;
        TextView mTextField;
        private SensorManager mSensorManager;
        private Sensor mAccelerometer;
        LocationManager locationManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calibration_run);


            if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()){
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
            }else {setUpLocation();}

            sqLiteHelper = new SQLiteHelper(this);
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            start = (Button) findViewById(R.id.start);
            save = (Button) findViewById(R.id.finish);

            Intent intent = getIntent();
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


            userId = intent.getStringExtra("Username");
            mTextField = (TextView) findViewById(R.id.mTextField);

            start.setEnabled(true);
            save.setEnabled(false);

            start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    clickStart();
                }
            });

            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    clickSave();
                }
            });
        }

    public void clickStart(){
        //start countdown
        lat_a = latitude;
        lon_b = longitude;
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                save.setEnabled(true);
                mTextField.setText("done!");
            }

        }.start();
    }

        public void clickSave(){

            double lat_a1 = latitude;
            double lon_b1 = longitude;
            double d = RecordingActivity.distance(lat_a, lon_b, lat_a1, lon_b1);
            Log.i("Distance", "Distance" + d);
            double differenceTime = (double) 20000/3600000;

            double Speed = getMiles(d) / differenceTime;
            Log.i("Speed", "Speed" + Speed);

            changingActivity();
            sqLiteHelper = new SQLiteHelper(getApplicationContext());
            Log.i("Username", "username 1" + userId);
            CalibrationDetails calRun = new CalibrationDetails(userId, averageX, averageY, averageZ, varianceX, varianceY, varianceZ, maxX, maxY, maxZ, minX, minY, minZ, Q1X, Q3X, Q1Y, Q3Y, Q1Z, Q3Z, Speed);
            sqLiteHelper.getWritableDatabase();
            long insertingCalibration = sqLiteHelper.createRunCalibration(calRun);

            Intent intent = new Intent(this, timelineActivity.class);
            intent.putExtra("Username", userId);
            startActivity(intent);
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

        public void changingActivity(){
            float accelerometer = getActivityAce(mLastX, mLastY, mLastZ);
            averageX = calculateAverage(xValuesAccelerometer);
            averageY = calculateAverage(yValuesAccelerometer);
            averageZ = calculateAverage(zValuesAccelerometer);
            varianceX = variance(xValuesAccelerometer);
            varianceY = variance(yValuesAccelerometer);
            varianceZ = variance(zValuesAccelerometer);
            Log.i("averageX", "averageX" + averageX);
            Log.i("averageY", "averageY" + averageY);
            Log.i("averageZ", "averageZ" + averageZ);
//        Log.i("varianceX", "varianceX" + varianceX);
//        Log.i("varianceY", "varianceY" + varianceY);
//        Log.i("varianceZ", "varianceZ" + varianceZ);
            sortList(xValuesAccelerometer);
            sortList(yValuesAccelerometer);
            sortList(zValuesAccelerometer);

            maxX = maxList(xValuesAccelerometer);
            maxY = maxList(yValuesAccelerometer);
            maxZ = maxList(zValuesAccelerometer);

            minX = minList(xValuesAccelerometer);
            minY = minList(yValuesAccelerometer);
            minZ = minList(zValuesAccelerometer);

            Q1X =  calculateAverageInt(averageX, minX);
            Q3X = calculateAverageInt(maxX, averageX);

            Q1Y = calculateAverageInt(averageY, minY);
            Q3Y = calculateAverageInt(maxY, averageY);

            Q1Z = calculateAverageInt(averageZ, minZ);
            Q3Z = calculateAverageInt(maxZ, averageZ);

            Log.i("CalRunQ1Y", "Q1Y" + Q1Y);
            Log.i("CalRunQ3Y", "Q3Y" + Q3Y);
            Log.i("CalRunQ1X", "Q1X" + Q1X);
            Log.i("CalRunQ3X", "Q3X" + Q3X);
            double finalY = Q1Y + Q3Y;

            double diffY = Q3Y - Q1Y;
            double diffX = Q3X - Q3Y;

            float accelerometer_bounds = 20;
            float gyrometer = 0;
            float gyrometer_bounds =0;
            float stable_bounds = 0;

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

        public int maxList(ArrayList<Integer> values){
            return Collections.max(values);
        }

        public int minList(ArrayList<Integer> values){
            return Collections.min(values);
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


    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);

        //&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        Toast.makeText(getApplicationContext(),
                "Please wait for GPS",
                Toast.LENGTH_SHORT)                        .show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);



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

    //Location
    public void onProviderDisabled(String provider) {
        // Code to do something if location provider is disabled e.g. display error
    }

    public void onProviderEnabled(String provider) {
        // Code to do something if location provider becomes available e.g check if it’s a  more useful provider
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Code to do something if location provider status changes..
    }

    public void onLocationChanged(Location location){


        Toast.makeText(getApplicationContext(),
                "GPS Found",
                Toast.LENGTH_SHORT)                        .show();

        latitude = (float) location.getLatitude();
        longitude = (float) location.getLongitude();

    }

    public double getMiles(double dist){
        //dist - convert from m to miles.
        double d = 0;
        // String unit = "miles";

        d = dist * 0.6214; //Conversion of m to miles
        Log.i("distance", "d" + d);

        return d;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Home!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra("Username", userId);
                Log.i("username", "email"+ userId);
                startActivity(intent);
                return true;
            case R.id.navigation_record:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Record!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent intent2 = new Intent(this, RecordingActivity.class);
                intent2.putExtra("Username", userId);
                Log.i("username", "email"+ userId);
                startActivity(intent2);
                return true;
            case R.id.navigation_logout:
                Toast.makeText(getApplicationContext(),
                        "Correctly Identified Logout!",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    }


