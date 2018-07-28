package com.philiday.projectapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    Button LogInButton;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder, NameHolder;
    String name;
    SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t2 = (TextView) findViewById(R.id.signup);

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp(v);
            }
        });

        LogInButton = (Button)findViewById(R.id.loginButton);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        sqLiteHelper = new SQLiteHelper(this);
        // Getting value from All EditText and storing into String Variables.

        UserDetails username = new UserDetails(EmailHolder, PasswordHolder, NameHolder);
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
                if(EmailHolder.equals("")){
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                    return;
                }
                if(EmailHolder.equals(email) && sqLiteHelper.isEmailExists(email)){
                    Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(LoginActivity.this, WelcomeActivity.class);

                    loginIntent.putExtra("Username", EmailHolder);
                    startActivity(loginIntent);
                }else{
                    Toast.makeText(LoginActivity.this,"Username or Password Incorrect, try again",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i("emailHolder", "emailHolder" + email);
                Log.i("passwordHolder", "passwordHolder" + password);


            }
        });

    }
    public void goToSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }





}

