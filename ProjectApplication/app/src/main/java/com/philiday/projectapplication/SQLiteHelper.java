package com.philiday.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="UserDataBase";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME="user";
    public static final String TABLE_NAME_1="RunTable";


    public static final String Table_Column_ID="userId";
    public static final String Table_Column_1_Name="name";
    public static final String Table_Column_2_Email="email";
    public static final String Table_Column_3_Password="password";

    public static final String Table1_Column_ID="runId";
    public static final String Table1_Column_1_date="timeOfRun";
    public static final String Table1_Column_2_distance="distance";
    public static final String Table1_Column_3_time="time";
    public static final String Table1_Column_4_pace="pace";
    public static final String Table1_Column_5_userId="userId";
    String temp = "NOT_FOUND" ;

    // private ContentValues cValues;
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    SQLiteDatabase sqLiteDatabaseObj;


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i("tableQuery", UserDetails.CREATE_TABLE);

        database.execSQL(UserDetails.CREATE_TABLE);

        database.execSQL(RunDetails.CREATE_TABLE_2);
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
//        Cursor c = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, select, " " + SQLiteHelper.Table_Column_1_Name + "=?", new String[]{Table_Column_1_Name}, null, null, null);

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

            return rd;


    }

    public ArrayList<RunDetails> getAllRuns(String name){
        ArrayList<RunDetails> runs = new ArrayList<RunDetails>();

        String selectQuery = ("SELECT * FROM " + RunDetails.TABLE_NAME_1);

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

                runs.add(rd);
            }while (c.moveToNext());
        }
        return runs;
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
        contentValues.put(Table_Column_2_Email, user.getEmail());
        Log.i("userRow", "help" + user.getEmail());

        contentValues.put(Table_Column_3_Password, user.getPassword());
        long userRow = db.insert(UserDetails.TABLE_NAME, null, contentValues);
        Log.i("userRow", "help2" + user.getPassword());


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
                u.setEmail(c.getString((c.getColumnIndex(UserDetails.Table_Column_2_Email))));
                u.setPassword(c.getString((c.getColumnIndex(UserDetails.Table_Column_3_Password))));

                userDetailsList.add(u);
            }while(c.moveToNext());
        }
        return userDetailsList;
    }


    public UserDetails displayUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + UserDetails.TABLE_NAME + " WHERE email = '" + name +"'");

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
        ud.setEmail(c.getString(c.getColumnIndex(UserDetails.Table_Column_2_Email)));
        ud.setPassword(c.getString(c.getColumnIndex(UserDetails.Table_Column_3_Password)));

        // ud.setId(c.getString(c.getColumnIndex(UserDetails.Table_Column_ID)));


        return ud;


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

        Log.i("userId", "userId" + run);
        Log.i("userId", "userId" + run.getTime());
        Log.i("userId", "walkingDist" + run.getWalkingDist());

        long userRow = db.insert(RunDetails.TABLE_NAME_1, null, contentValues);


        if (userRow > 0) {
            Log.i("runRow", "datainserted");
        }

        return userRow;
    }

    public void deleteRun(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RunDetails.TABLE_NAME_1, RunDetails.Table1_Column_1_date + " = ?", new String[] {String.valueOf(date)});
    }




}
