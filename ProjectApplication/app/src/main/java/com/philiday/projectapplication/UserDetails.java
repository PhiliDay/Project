package com.philiday.projectapplication;

import android.database.sqlite.SQLiteDatabase;

public class UserDetails {

    public static final String TABLE_NAME = "user";

    public static final String Table_Column_ID="userId";
    public static final String Table_Column_1_Name="firstName";
    public static final String Table_Column_2_Email="email";
    public static final String Table_Column_3_Password="password";

     String userId;
     String name;
     String email;
     String password;
     String firstName;



    // Create table SQL query
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
          //  +Table_Column_ID+" VARCHAR, "
            + Table_Column_1_Name+" VARCHAR, "
            +Table_Column_2_Email+" VARCHAR, "
            +Table_Column_3_Password+" VARCHAR)";

    public UserDetails() {

    }

    public UserDetails(String email, String password, String firstName) {
       // this.userId = userId;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return userId;
    }

    public String getFirstName(){ return firstName;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public void setId(String userId){
        this.userId = userId;
    }

   // public void setName(String name) {
   //     this.name = name;
   // }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

   // public String getName() {
  //      return name;
   // }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

}
