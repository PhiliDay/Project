package com.philiday.projectapplication;


import android.content.Context;

public class RunDetails {

    public static final String TABLE_NAME_1 = "RunTable1";


    public static final String Table1_Column_ID="runId";
    public static final String Table1_Column_1_date="timeOfRun";
    public static final String Table1_Column_2_distance="distance";
    public static final String Table1_Column_3_time="time";
    public static final String Table1_Column_4_pace="pace";
    public static final String Table1_Column_5_userId="userId";
    public static final String Table1_Column_6_walkingDist="walkingDist";
    public static final String Table1_Column_7_ranDist="ranDist";

    private String runId;
    private String date;
    private String distance;
    private String time;
    private String userId;
    private String walkingDist;
    private String ranDist;


    public static final String CREATE_TABLE_2="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_1+" ("
      //      +Table1_Column_ID+" TIMESTAMP PRIMARY KEY AUTOINCREMENT,"
            +Table1_Column_1_date+" TIMESTAMP, "
            +Table1_Column_2_distance+" VARCHAR, "
            +Table1_Column_3_time+" VARCHAR, "
            +Table1_Column_6_walkingDist+" VARCHAR, "
            +Table1_Column_7_ranDist+" VARCHAR, "
            //   +Table1_Column_4_pace+"INTEGER NOT NULL,"
            +Table1_Column_5_userId+" VARCHAR NOT NULL, FOREIGN KEY (" +Table1_Column_5_userId+ ") REFERENCES " + UserDetails.TABLE_NAME + "(" + UserDetails.Table_Column_1_Name + "));";

    public RunDetails(){

    }



    public RunDetails(String userId, String date, String distance, String time, String walkingDist, String ranDist) {
        this.userId = userId;
        this.date = date;
        this.distance = distance;
        this.time = time;
        this.walkingDist = walkingDist;
        this.ranDist = ranDist;
    }

    public String getrunId() {
        return runId;
    }

    public void setRunId(String runId){
        this.runId = runId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime(){
        return time;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setWalkingDist(String walkingDist){
        this.walkingDist = walkingDist;
    }

    public void setRanDist(String ranDist){
        this.ranDist = ranDist;
    }

    public String getWalkingDist(){
        return walkingDist;
    }
    public String getRanDist(){
        return ranDist;
    }



    public void setRun(String userId, String distance, String time, String date){
        this.userId = userId;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

}
