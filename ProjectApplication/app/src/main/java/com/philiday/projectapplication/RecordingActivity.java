package com.philiday.projectapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecordingActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    Button start, stop, save;
    TextView startime, timetaken, startlocation, endlocation, currentLoc,  maxspeed, endtime, distance;
    String interimlocation;
    Spinner mode;
    String db_mode;
    double db_startlat;
    double db_startlong;
    String db_interimlocation;
    double db_endlat;
    double db_endlong;
    double db_maxspeed;
    String db_duration;
    String db_distance;

    long stime; // start time in milliseconds
    long etime; // end time in milliseconds
    long dtime; // duration in milliseconds

    double speed=0;
    double dist = 0;
    String latestLocationString;
    String startLocationString;

    Location startLocation;
    Location latestLocation;
    Location interimLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        setUpLocation();


        Bundle bundle = getIntent().getExtras();

        //   db = new MyDBManager(this);

        mode = (Spinner) findViewById(R.id.mode);
        db_mode = "Walking"; // To store default mode

        startime = (TextView) findViewById(R.id.startime);
        distance = (TextView) findViewById(R.id.distance);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        endtime = (TextView) findViewById(R.id.endtime);
        timetaken = (TextView) findViewById(R.id.timetaken);
        distance = (TextView) findViewById(R.id.distance);
        startlocation = (TextView) findViewById(R.id.starting);
        endlocation = (TextView) findViewById(R.id.endLocation);
        currentLoc = (TextView) findViewById(R.id.updating);




        stop.setEnabled(false); // stop button is disabled

        // Create a list of strings for the Spinner
        List<String> list = new ArrayList<String>();
        list.add("Walking");


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

        // Click and start journey
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                stop.setEnabled(true);
                startime.setText("");
                endtime.setText("");
                timetaken.setText("");
                distance.setText("");
                startlocation.setText("");
                endlocation.setText("");
                currentLoc.setText("");


                //Get start time
                Calendar cl = Calendar.getInstance();
                stime = cl.getTimeInMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                startime.setText(sdf.format(cl.getTime()));

                if (startLocationString != null) {
                    startlocation.setText(startLocationString);
                }


            }
        });

        //Click and stop journey
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setEnabled(false);
                start.setEnabled(true);

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

                if(latestLocationString!=null) {
                    endlocation.setText(latestLocationString);

                    //dist = latestLocation.distanceTo(startLocation);
                }
                else{
                    dist = 0;
                }

            }
        });

    }

    private Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public void onLocationChanged(Location location) {

//        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
//        startLocationString = "Coordinate" + myCoordinates;
//        startlocation.setText(startLocationString);

        double oldDist =0;
        if (location != null) {
            if (startLocation == null && stop.isEnabled()) {
                Log.v("mytag", "LOCATION NULL");
                startLocation = location;
                latestLocation = location;
//                interimLocation = location;
                LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
                startLocationString = "Coordinate" + myCoordinates;
                startlocation.setText(startLocationString);
                dist = 0;
            }
        }

//            //if the stop button is not enabled ==> the user is still doing his journey, then update the location and the max speed.
//            if(stop.isEnabled()) {
//                double dlong = toRad(location.getLongitude() - latestLocation.getLongitude());
//                double dlat = toRad(location.getLatitude() - latestLocation.getLatitude());
//                double a =
//                        Math.pow(Math.sin(toRad(dlat) / 2.0), 2)
//                                + Math.cos(toRad(latestLocation.getLatitude()))
//                                * Math.cos(toRad(location.getLatitude()))
//                                * Math.pow(Math.sin(toRad(dlong) / 2.0), 2);
//                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//                double d = 6367 * c;
//                dist = dist + d;
//                distance.setText((int)dist);
//
//                latestLocation = location;
//                latestLocationString =  "\n" + "Long: "+ location.getLongitude() + "\n" + "Lat: "+location.getLatitude();
//                currentLoc.setText(latestLocationString);
//
//                if(oldDist < dist + 0.005){
//                    oldDist = dist;
//                    interimLocation = location;
//                    if(interimlocation==null){
//                        interimlocation= "{"+ "Long:"+ location.getLongitude() + "," + "Lat:"+location.getLatitude()+"}";
//
//                    }else {
//                        interimlocation = interimlocation + "{" + "Long:" + location.getLongitude() + "," + "Lat:" + location.getLatitude() + "}";
//                    }
//                }
//                //updating the max speed
////                double newSpeed = location.getSpeed();
////                if (newSpeed > speed) {
////                    speed = newSpeed;
////                    DecimalFormat df = new DecimalFormat("#.##");
////                    maxspeed.setText(df.format(speed) + " m/s");
////                }
//            }
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
    }


    /**
     * Stop the updates when Activity is paused i.e. save your battery
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.removeUpdates(this);
    }

}