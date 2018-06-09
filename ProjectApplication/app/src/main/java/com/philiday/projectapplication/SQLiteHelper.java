package com.philiday.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;


public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME="UserTable";
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

    // private ContentValues cValues;
    //private SQLiteDatabase dataBase = null;

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {


        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
                +Table_Column_ID+" INTEGER PRIMARY KEY, "
                + Table_Column_1_Name+" VARCHAR, "
                +Table_Column_2_Email+" VARCHAR, "
                +Table_Column_3_Password+" VARCHAR)";

        String CREATE_TABLE_2="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_1+" ("
               +Table1_Column_ID+" INTEGER PRIMARY KEY,"
                +Table1_Column_1_date+"TIMESTAMP,"
                +Table1_Column_2_distance+"INTEGER,"
                +Table1_Column_3_time+"INTEGER,"
             //   +Table1_Column_4_pace+"INTEGER NOT NULL,"
                +Table1_Column_5_userId+"INTEGER NOT NULL, FOREIGN KEY (" +Table1_Column_5_userId+ ") REFERENCES " + TABLE_NAME + "(" + Table_Column_ID + "));";

        database.execSQL(CREATE_TABLE);
//Need to create a different function to create the table when you are in the app? Maybe for now make all not null?
        database.execSQL(CREATE_TABLE_2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);

        onCreate(db);

    }

    public int updateRecord(String email, String password) {

        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues cValues = new ContentValues();

        //cValues.put(Table_Column_1_Name, name);
        cValues.put(Table_Column_2_Email, email);
        cValues.put(Table_Column_3_Password,password);
//    Update data from database table
        long insertchek=dataBase.update(SQLiteHelper.TABLE_NAME, cValues,
                Table_Column_2_Email+"='"+email+"' AND "+Table_Column_3_Password+"='"+password+"'", null);
        dataBase.close();
        return (int)insertchek;
    }

    public long insert(String table,ContentValues cv,String col)
    {
        SQLiteDatabase dataBase = getWritableDatabase();
        long a=dataBase.insert(table,col,cv);
        return a;
    }




}
