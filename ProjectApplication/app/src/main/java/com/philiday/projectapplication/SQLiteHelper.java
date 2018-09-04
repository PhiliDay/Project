package com.philiday.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;

import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/* Main class used to access the databases. Contains all the queries needed.*/

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="UserDataBase";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME="user";
    public static final String TABLE_NAME_1="RunTable";


    public static final String Table_Column_ID="userId";
    public static final String Table1_Column_5_userId="userId";

    // private ContentValues cValues;
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    SQLiteDatabase sqLiteDatabaseObj;
    private static SQLiteHelper instance;



    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i("tableQuery", UserDetails.CREATE_TABLE);

        database.execSQL(UserDetails.CREATE_TABLE);

        database.execSQL(RunDetails.CREATE_TABLE_2);

        database.execSQL(CalibrationDetails.CREATE_TABLE);

        database.execSQL(CalibrationDetails.CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS "+ RunDetails.TABLE_NAME_1);
        database.execSQL("DROP TABLE IF EXISTS "+ UserDetails.TABLE_NAME);
        onCreate(database);

    }

    public long insert(String table,ContentValues cv,String col)
    {

        SQLiteDatabase dataBase = getWritableDatabase();
        long id=dataBase.insert(table,col,cv);
        return id;
    }

    public void getUserDetails(String name){
        String[] select = {Table_Column_ID};
        Log.i("mytag", "help1" + name);
        SQLiteDatabase database = getWritableDatabase();

        Cursor c =  database.query(TABLE_NAME, select,
                Table_Column_ID + " = \"" + name + "\"" ,
                null, null, null, null, null);

        Log.v("MYDB","Table TABLE_NAME has " +
                Integer.toString(c.getCount()) +
                " rows");

        for (int i=0; i < c.getColumnCount(); i++) {
            Log.v("MYDB", "Table TABLE_NAME has a column named " +
                    c.getColumnName(i)
            );
        }
      //  return c;
    }

    public RunDetails displayRun(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + RunDetails.TABLE_NAME_1);

        Log.i("query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);


        Log.v("MYDB", "Table1 TABLE_NAME has " +
                Integer.toString(c.getCount()) +
                " rows");

        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.v("MYDB", "Table1 TABLE_NAME has a column named " +
                    c.getColumnName(i)
            );
        }

        if (c != null)
            c.moveToFirst();
            Log.i("hello", "hello");
            RunDetails rd = new RunDetails();
            rd.setDistance(c.getString(c.getColumnIndex(RunDetails.Table1_Column_2_distance)));
            rd.setDate(c.getString(c.getColumnIndex(RunDetails.Table1_Column_1_date)));
            rd.setTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_3_time)));
            rd.setWalkingDist(c.getString(c.getColumnIndex(RunDetails.Table1_Column_6_walkingDist)));
            rd.setRanDist(c.getString(c.getColumnIndex(RunDetails.Table1_Column_7_ranDist)));
            rd.setWalkingTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_8_walkingTime)));
            rd.setRunningTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_9_runningTime)));
            rd.setOverallPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_10_overallPace)));
            rd.setWalkingPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_11_walkingPace)));
            rd.setRunningPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_12_runningPace)));
            rd.setImage(c.getBlob(1));

            return rd;


    }

    public ArrayList<RunDetails> getAllRuns(String name){
        ArrayList<RunDetails> runs = new ArrayList<RunDetails>();

        String selectQuery = ("SELECT * FROM " + RunDetails.TABLE_NAME_1 + " WHERE " + RunDetails.Table1_Column_5_userId + " = '" + name + "'" );

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                RunDetails rd = new RunDetails();
                rd.setUserId(c.getString(c.getColumnIndex(RunDetails.Table1_Column_5_userId)));
                rd.setDistance(c.getString(c.getColumnIndex(RunDetails.Table1_Column_2_distance)));
                rd.setDate(c.getString(c.getColumnIndex(RunDetails.Table1_Column_1_date)));
                rd.setTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_3_time)));
                rd.setWalkingDist(c.getString(c.getColumnIndex(RunDetails.Table1_Column_6_walkingDist)));
                rd.setRanDist(c.getString(c.getColumnIndex(RunDetails.Table1_Column_7_ranDist)));
                rd.setWalkingTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_8_walkingTime)));
                rd.setRunningTime(c.getString(c.getColumnIndex(RunDetails.Table1_Column_9_runningTime)));
                rd.setOverallPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_10_overallPace)));
                rd.setWalkingPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_11_walkingPace)));
                rd.setRunningPace(c.getString(c.getColumnIndex(RunDetails.Table1_Column_12_runningPace)));
                rd.setImage(c.getBlob(1));

                runs.add(rd);
            }while (c.moveToNext());
        }
        return runs;
    }

    public CalibrationDetails getCalibration(String userId, String tableName){
        String selectQuery = ("SELECT * FROM " + tableName + " WHERE " + CalibrationDetails.Table3_Column_UserId + " = '" + userId + "'");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();
            CalibrationDetails cal = new CalibrationDetails();
            cal.setUserId(c.getString(c.getColumnIndex(CalibrationDetails.Table3_Column_UserId)));
            cal.setAverageX(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column1_averageX)));
            cal.setAverageY(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column2_averageY)));
            cal.setAverageZ(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column3_averageZ)));
            cal.setVarianceX(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column4_varianceX)));
            cal.setVarianceY(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column5_varianceY)));
            cal.setVarianceZ(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column6_varianceZ)));
            cal.setMaxX(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column7_maxX)));
            cal.setMaxY(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column8_maxY)));
            cal.setMaxZ(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column9_maxZ)));
            cal.setMinX(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column10_minX)));
            cal.setMinY(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column11_minY)));
            cal.setMinX(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column12_minZ)));
            cal.setQ1X(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column13_Q1X)));
            cal.setQ3X(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column14_Q3X)));
            cal.setQ1Y(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column15_Q1Y)));
            cal.setQ3Y(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column16_Q3Y)));
            cal.setQ1z(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column17_Q1Z)));
            cal.setQ3Z(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column18_Q3Z)));
            cal.setSpeed(c.getDouble(c.getColumnIndex(CalibrationDetails.Table3_Column19_speed)));

            return cal;

    }

    public UserDetails getName(String userId){
        String selectQuery = ("SELECT * FROM " + UserDetails.TABLE_NAME + " WHERE email = '" + userId + "'");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToLast();
        UserDetails user = new UserDetails();
        user.setFirstName(c.getString(c.getColumnIndex(UserDetails.Table_Column_1_Name)));

        return user;

    }



    public void closeDb(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen())
            db.close();
    }

    public long createUserInDatabase(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();


       // String userID = createAUserId(user.getId());

        ContentValues contentValues = new ContentValues();

     //   contentValues.put(Table1_Column_5_userId, user.getId());
        contentValues.put(UserDetails.Table_Column_2_Email, user.getEmail());
        Log.i("userRow", "help" + user.getEmail());
        contentValues.put(UserDetails.Table_Column_3_Password, user.getPassword());
        contentValues.put(UserDetails.Table_Column_1_Name, user.getFirstName());
        long userRow = db.insert(UserDetails.TABLE_NAME, null, contentValues);
        Log.i("userRow", "help2" + user.getPassword());
        Log.i("userRow", "help2" + user.getFirstName());


        if (userRow > 0) {
            // String selectQuery = ("SELECT * FROM " + UserDetails.TABLE_NAME);

            // Cursor c = db.rawQuery(selectQuery, null);
            Log.i("userRow", "datainserted");
        }

    return userRow;
        // }
    }


    public List<UserDetails> getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();

        String selectQuery = "SELECT * FROM " + UserDetails.TABLE_NAME;

        Log.i("Tag", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                UserDetails u = new UserDetails();
                u.setFirstName(c.getString(c.getColumnIndex(UserDetails.Table_Column_1_Name)));
                u.setEmail(c.getString((c.getColumnIndex(UserDetails.Table_Column_2_Email))));
                u.setPassword(c.getString((c.getColumnIndex(UserDetails.Table_Column_3_Password))));

                userDetailsList.add(u);
            }while(c.moveToNext());
        }
        return userDetailsList;
    }


    public UserDetails displayUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + UserDetails.TABLE_NAME + " WHERE email = '" + name +"' ");

        Log.i("query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);


        Log.v("MYDB", "Table1 TABLE_NAME has " +
                Integer.toString(c.getCount()) +
                " rows");

        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.v("MYDB", "Table1 TABLE_NAME has a column named " +
                    c.getColumnName(i)
            );
        }
        Log.d("Count",String.valueOf(c.getCount()));
        if(c.getCount() > 0){
// get values from cursor here
        }

        if(c.getCount() == 0){
            Log.i("user", "user does not exist");
        }

        if (c != null)
            c.moveToFirst();
        Log.i("hello", "hello");
        UserDetails ud = new UserDetails();
        if(isEmailExists(name) == true) {
            ud.setFirstName(c.getString(c.getColumnIndex(UserDetails.Table_Column_1_Name)));
            ud.setEmail(c.getString(c.getColumnIndex(UserDetails.Table_Column_2_Email)));
            ud.setPassword(c.getString(c.getColumnIndex(UserDetails.Table_Column_3_Password)));

            // ud.setId(c.getString(c.getColumnIndex(UserDetails.Table_Column_ID)));
        }

        return ud;


    }

    public boolean calCheck(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT count(*) FROM " + CalibrationDetails.TABLE_NAME_3 + " WHERE " + CalibrationDetails.Table3_Column_UserId + " = '" + name + "'" ;
        Cursor c = db.rawQuery(selectQuery, null);

        c.moveToFirst();

        int count = c.getInt(0);

        if(count > 0){
            return true;
        }else{
            return false;
        }
    }





    // Insert data into SQLite database method.
    public long createRun(RunDetails run) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", run.getUserId());
        contentValues.put("timeOfRun", run.getDate());
        contentValues.put("distance", run.getDistance());
        contentValues.put("time", run.getTime());
        contentValues.put("walkingDist", run.getWalkingDist());
        contentValues.put("ranDist", run.getRanDist());
        contentValues.put("walkingTime", run.getWalkingTime());
        contentValues.put("runningTime", run.getRunningTime());
        contentValues.put("overallPace", run.getOverallPace());
        contentValues.put("walkingPace", run.getWalkingPace());
        contentValues.put("runningPace", run.getRunningPace());
        contentValues.put("image", run.getImage());

        long userRow = db.insert(RunDetails.TABLE_NAME_1, null, contentValues);


        if (userRow > 0) {
            Log.i("runRow", "datainserted");
        }

        return userRow;
    }

    public long createCalibration(CalibrationDetails cal){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cV = new ContentValues();
        cV.put("userId", cal.getUserId());
        cV.put("averageX", cal.getAverageX());
        cV.put("averageY", cal.getAverageY());
        cV.put("averageZ", cal.getAverageZ());
        cV.put("varianceX", cal.getVarianceX());
        cV.put("varianceY", cal.getVarianceY());
        cV.put("varianceZ", cal.getVarianceZ());
        cV.put("maxX", cal.getMaxX());
        cV.put("maxY", cal.getMaxY());
        cV.put("maxZ", cal.getMaxZ());
        cV.put("minX", cal.getMinX());
        cV.put("minY", cal.getMinY());
        cV.put("minZ", cal.getMinZ());
        cV.put("Q1X", cal.getQ1X());
        cV.put("Q3X", cal.getQ3X());
        cV.put("Q1Y", cal.getQ1Y());
        cV.put("Q3Y", cal.getQ3Y());
        cV.put("Q1Z", cal.getQ1Z());
        cV.put("Q3Z", cal.getQ3Z());
        cV.put("Speed", cal.getSpeed());

        long calRow = db.insert(CalibrationDetails.TABLE_NAME_3, null, cV);

        if(calRow > 0){
            Log.i("calRow", "calRowInserted");
        }

        return calRow;

    }

    public long createRunCalibration(CalibrationDetails cal){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cV = new ContentValues();
        cV.put("userId", cal.getUserId());
        cV.put("averageX", cal.getAverageX());
        cV.put("averageY", cal.getAverageY());
        cV.put("averageZ", cal.getAverageZ());
        cV.put("varianceX", cal.getVarianceX());
        cV.put("varianceY", cal.getVarianceY());
        cV.put("varianceZ", cal.getVarianceZ());
        cV.put("maxX", cal.getMaxX());
        cV.put("maxY", cal.getMaxY());
        cV.put("maxZ", cal.getMaxZ());
        cV.put("minX", cal.getMinX());
        cV.put("minY", cal.getMinY());
        cV.put("minZ", cal.getMinZ());
        cV.put("Q1X", cal.getQ1X());
        cV.put("Q3X", cal.getQ3X());
        cV.put("Q1Y", cal.getQ1Y());
        cV.put("Q3Y", cal.getQ3Y());
        cV.put("Q1Z", cal.getQ1Z());
        cV.put("Q3Z", cal.getQ3Z());
        cV.put("Speed", cal.getSpeed());

        long calRow = db.insert(CalibrationDetails.TABLE_NAME_4, null, cV);

        if(calRow > 0){
            Log.i("calRow", "calRowInserted");
        }

        return calRow;

    }

    public void deleteRun(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RunDetails.TABLE_NAME_1, RunDetails.Table1_Column_1_date + " = ?", new String[] {String.valueOf(date)});
    }

    public boolean isEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserDetails.TABLE_NAME, new String[]{UserDetails.Table_Column_2_Email, UserDetails.Table_Column_3_Password},
                UserDetails.Table_Column_2_Email + "=?",
                new String[]{email}, null, null, null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount()>0){
            return true;
        }
        return false;
    }
}
