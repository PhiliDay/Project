package com.philiday.projectapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="UserDataBase";
    private static final int DATABASE_VERSION = 1;

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

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {


//        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
//                +Table_Column_ID+" INTEGER PRIMARY KEY, "
//                + Table_Column_1_Name+" VARCHAR, "
//                +Table_Column_2_Email+" VARCHAR, "
//                +Table_Column_3_Password+" VARCHAR)";
//
//        String CREATE_TABLE_2="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_1+" ("
//               +Table1_Column_ID+" INTEGER PRIMARY KEY,"
//                +Table1_Column_1_date+"TIMESTAMP,"
//                +Table1_Column_2_distance+"INTEGER,"
//                +Table1_Column_3_time+"INTEGER,"
//             //   +Table1_Column_4_pace+"INTEGER NOT NULL,"
//                +Table1_Column_5_userId+"INTEGER NOT NULL, FOREIGN KEY (" +Table1_Column_5_userId+ ") REFERENCES " + TABLE_NAME + "(" + Table_Column_ID + "));";

      //  database.execSQL(CREATE_TABLE);

        database.execSQL(UserDetails.CREATE_TABLE);
//Need to create a different function to create the table when you are in the app? Maybe for now make all not null?
     //   database.execSQL(CREATE_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS "+ UserDetails.TABLE_NAME);
      //  database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);

        onCreate(database);

    }

    public long insert(String table,ContentValues cv,String col)
    {
        SQLiteDatabase dataBase = getWritableDatabase();
        long id=dataBase.insert(table,col,cv);
        return id;
    }

//    public long insertUser(String name, String password){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(UserDetails.Table_Column_1_Name, name);
//        values.put(UserDetails.Table_Column_3_Password, password);
//
//        long id = db.insert(UserDetails.TABLE_NAME, null, values);
//
//        db.close();
//
//        return id;
//    }

//    public UserDetails getData(String userId){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(UserDetails.TABLE_NAME,
//                new String[]{UserDetails.Table_Column_ID, UserDetails.Table_Column_1_Name, UserDetails.Table_Column_2_Email, UserDetails.Table_Column_3_Password},
//                UserDetails.Table_Column_ID + "=?",
//                new String[]{String.valueOf(userId)}, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // prepare note object
//        UserDetails userDetails = new UserDetails(
//                cursor.getString(cursor.getColumnIndex(UserDetails.Table_Column_ID)),
//                cursor.getString(cursor.getColumnIndex(UserDetails.Table_Column_1_Name)));
//           //     cursor.getString(cursor.getColumnIndex(UserDetails.Table_Column_2_Email)),
//           //     cursor.getString(cursor.getColumnIndex(UserDetails.Table_Column_3_Password)));
//
//        // close the db connection
//        cursor.close();
//
//        return userDetails;
//    }

//    public int getUsersCount() {
//        String countQuery = "SELECT  * FROM " + UserDetails.TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        cursor.close();
//
//
//        // return count
//        return count;
//    }

}
