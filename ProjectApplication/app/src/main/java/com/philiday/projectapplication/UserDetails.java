package com.philiday.projectapplication;

public class UserDetails {

    public static final String TABLE_NAME = "user";

    public static final String Table_Column_ID="userId";
    public static final String Table_Column_1_Name="name";
    public static final String Table_Column_2_Email="email";
    public static final String Table_Column_3_Password="password";

    private String userId;
    private String name;
    private String email;
    private String password;


    // Create table SQL query
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
            +Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Table_Column_1_Name+" VARCHAR, "
            +Table_Column_2_Email+" VARCHAR, "
            +Table_Column_3_Password+" VARCHAR)";

    public UserDetails() {
    }

    public UserDetails(String userId, String name) {
        this.userId = userId;
        this.name = name;
       // this.email = email;
       // this.password = password;
    }

    public String getId() {
        return userId;
    }

    public void setId(String userId){
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

}
