package com.philiday.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CalibrationActivity extends AppCompatActivity implements SensorEventListener{

    Button begin, finish;
    TextView mTextField;
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

    SQLiteHelper sqLiteHelper;
    String userId;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        begin = (Button) findViewById(R.id.begin);
        finish = (Button) findViewById(R.id.finish);
        mTextField = (TextView) findViewById(R.id.mTextField);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sqLiteHelper = new SQLiteHelper(this);

        Intent intent = getIntent();

         userId = intent.getStringExtra("Username");

        begin.setEnabled(true);
        finish.setEnabled(false);

        begin.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                clickStart();
            }
        });

        finish.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                clickSave();
            }
        });


    }

    public void clickStart(){
        //start countdown
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                finish.setEnabled(true);
                mTextField.setText("done!");
            }

        }.start();
    }

    public void clickSave(){

        changingActivity();
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        Log.i("userId", "userId" + userId);
        CalibrationDetails cal = new CalibrationDetails(userId, averageX, averageY, averageZ, varianceX, varianceY, varianceZ, maxX, maxY, maxZ, minX, minY, minZ, Q1X, Q3X, Q1Y, Q3Y, Q1Z, Q3Z);
        sqLiteHelper.getWritableDatabase();
        long insertingCalibration = sqLiteHelper.createCalibration(cal);
        Intent intent2 = new Intent(this, CalibrationRunActivity.class);
        intent2.putExtra("Username", userId);
        startActivity(intent2);

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
        Log.i("CalWalkaverageX", "averageX" + averageX);
        Log.i("CalWalkaverageY", "averageY" + averageY);
        Log.i("CalWalkaverageZ", "averageZ" + averageZ);
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

        Log.i("CalWalkQ1Y", "Q1Y" + Q1Y);
        Log.i("CalWalkQ3Y", "Q3Y" + Q3Y);
        Log.i("CalWalkQ1X", "Q1X" + Q1X);
        Log.i("CalWalkQ3X", "Q3X" + Q3X);

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
        Log.i("collection","collection max" + values);

    }

    public int maxList(ArrayList<Integer> values){
        sortList(values);
        Integer i = Collections.max(values);

        Log.i("collection","collection max" + i);
        return i;
    }

    public int minList(ArrayList<Integer> values){
        sortList(values);
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




}
