package com.philiday.projectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;

public class SignUpActivity extends AppCompatActivity {

    EditText Email, Password;
    Button Register;
    String EmailHolder, PasswordHolder;
    Boolean EditTextHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String Email_find = "Not_Found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Register = (Button)findViewById(R.id.sign_up);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //  SQLiteDatabase mydb = sqLiteHelper.getWritableDatabase();
                SQLiteDataBaseBuild();
                SQLiteTableBuild();
                compareEditText();

                CheckingEmailAlreadyExistsOrNot();


            //    CheckFinalResult();
            }
        });

    }

    public void goToTimeline(View view) {
        Intent intent = new Intent(this, timelineActivity.class);
        startActivity(intent);
    }


    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " + SQLiteHelper.Table_Column_3_Password + " VARCHAR);");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase() {
        long a;
        // If editText is not empty then this block will executed.
        if (EditTextHolder == true) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", EmailHolder);
            contentValues.put("password", PasswordHolder);
            a = sqLiteHelper.insert(SQLiteHelper.TABLE_NAME, contentValues, SQLiteHelper.Table_Column_ID);
            if (a > 0) {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            sqLiteDatabaseObj.close();

        }
    }

    // Method to check EditText is empty or Not.
    public void compareEditText(){

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextHolder = false ;

        }
        else {

            EditTextHolder = true ;
        }
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);
        Log.v("MYDB","Table TABLE_NAME has " +
                Integer.toString(cursor.getCount()) +
                " rows");

        for (int i=0; i < cursor.getColumnCount(); i++) {
            Log.v("MYDB", "Table TABLE_NAME has a column named " +
                    cursor.getColumnName(i)
            );
        }
        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                Email_find = "Email Already Exists";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        runSearch();

    }

    public void runSearch(){

        // Checking whether email is already exists or not.
        if(Email_find.equalsIgnoreCase("Email Already Exists"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        Email_find = "Not_Found" ;
    }



}

