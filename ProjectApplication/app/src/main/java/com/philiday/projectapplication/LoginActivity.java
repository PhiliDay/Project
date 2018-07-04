package com.philiday.projectapplication;

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

public class LoginActivity extends AppCompatActivity {

    Button LogInButton;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder;
    Boolean EditTextHolder;
    SQLiteDatabase db;
    String name;
    SQLiteHelper sqLiteHelper;
   // Cursor cursor;
    String temp = "NOT_FOUND" ;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LogInButton = (Button)findViewById(R.id.loginButton);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        sqLiteHelper = new SQLiteHelper(this);
        // Getting value from All EditText and storing into String Variables.

        UserDetails username = new UserDetails(EmailHolder, PasswordHolder);
        sqLiteHelper.getWritableDatabase();

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CheckEditTextStatus();
                String name = Email.getText().toString();
                UserDetails user = sqLiteHelper.displayUser(name);
                EmailHolder = Email.getText().toString();

                String email = user.getEmail();
                String password = user.getPassword();
                Log.i("emailHolder", "emailHolder2" + EmailHolder);

                if(EmailHolder.equals(email)){
                    Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(LoginActivity.this, timelineActivity.class);

                    loginIntent.putExtra("Username", EmailHolder);
                    startActivity(loginIntent);
                }else{
                    Toast.makeText(LoginActivity.this,"Username or Password Incorrect, try again",Toast.LENGTH_LONG).show();

                }
                Log.i("emailHolder", "emailHolder" + email);
                Log.i("passwordHolder", "passwordHolder" + password);


            }
        });

    }




    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextHolder = false ;

        }
        else {

            EditTextHolder = true ;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void runSearch(){
        if(temp.equalsIgnoreCase(PasswordHolder))
        {

            // Going to Dashboard activity after login success message.
//            Intent intent = new Intent(LoginActivity.this, timelineActivity.class);

            // Sending Email to Dashboard Activity using intent.

//            intent.putExtra("Username", EmailHolder);
//            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();

        }
        temp = "NOT_FOUND" ;

    }

}

