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

    EditText Email, Password, Password2;
    Button Register;
    String EmailHolder, PasswordHolder, PasswordHolder2;
    Boolean EditTextHolder;
    SQLiteDatabase db;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String Email_find = "Not_Found";
    String Password_find = "Not_Found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
     //   sqLiteHelper = new SQLiteHelper(getApplicationContext());

        Register = (Button)findViewById(R.id.sign_up);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Password2 = (EditText)findViewById(R.id.password2);
        sqLiteHelper = new SQLiteHelper(this);
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        // Adding click listener to register button.

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            UserDetails user = new UserDetails(EmailHolder, PasswordHolder);

             //   compareEditText();
                db = sqLiteHelper.getWritableDatabase();

                sqLiteHelper.getWritableDatabase();
                compareEditText();
                CheckingEmailAlreadyExistsOrNot();

                if(runSearch() == false) {
                    Log.i("getsTohere", "getsTohere");
                    user.setEmail(EmailHolder);
                    user.setPassword(PasswordHolder);
                    long insertingUser = sqLiteHelper.createUserInDatabase(user);
                    Log.d("User Count", "User Count: " + sqLiteHelper.getAllUsers().size());
                    goToLogin(view);
                }
               // CheckFinalResult();
            }
        });

    }

    public void goToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToTimeline(View view) {
        Intent intent = new Intent(this, timelineActivity.class);
        startActivity(intent);
    }



    // Method to check EditText is empty or Not.
    public void compareEditText(){

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        PasswordHolder2 = Password2.getText().toString();
        Log.i("p1", "p1" + PasswordHolder);
        Log.i("p1", "p2" + PasswordHolder2);
        if(!PasswordHolder.equals(PasswordHolder2)) {
            Log.i("p1", "her");

                Password_find = "Password Incorrect";
       //     EditTextHolder = false;
        }
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PasswordHolder2)){
            Log.i("p1", "p1" + PasswordHolder);
            Log.i("p1", "p2" + PasswordHolder2);

            EditTextHolder = false;

        }
        else {

            EditTextHolder = true ;
        }
    }



    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        db = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        String selectQuery = ("SELECT * FROM " + UserDetails.TABLE_NAME + " WHERE email = '" + EmailHolder +"'");

        cursor = db.rawQuery(selectQuery, null);
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
                Log.i("mytag", "email: " + Email_find);
                // Closing cursor.
              //  cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        compareEditText();
        cursor.close();

    }

    public boolean runSearch(){

        // Checking whether email is already exists or not.
        if(Email_find.equalsIgnoreCase("Email Already Exists"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else if(Password_find.equalsIgnoreCase("Password Incorrect")){
            Toast.makeText(SignUpActivity.this,"Password Incorrect",Toast.LENGTH_LONG).show();

        }
        else {
            return false;
            // If email already doesn't exists then user registration details will entered to SQLite database.
        }
        Password_find = "Not_Found";
        Email_find = "Not_Found" ;
        return true;
    }



}

