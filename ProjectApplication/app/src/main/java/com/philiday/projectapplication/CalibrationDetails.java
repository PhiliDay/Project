package com.philiday.projectapplication;

import android.content.Context;

public class CalibrationDetails {

    public static final String TABLE_NAME_3 = "CalibrationTableWalk";
    public static final String TABLE_NAME_4 = "CalibrationTableRun";


    public static final String Table3_Column_UserId = "userId";
    public static final String Table3_Column1_averageX = "averageX";
    public static final String Table3_Column2_averageY = "averageY";
    public static final String Table3_Column3_averageZ = "averageZ";
    public static final String Table3_Column4_varianceX = "varianceX";
    public static final String Table3_Column5_varianceY = "varianceY";
    public static final String Table3_Column6_varianceZ = "varianceZ";
    public static final String Table3_Column7_maxX = "maxX";
    public static final String Table3_Column8_maxY = "maxY";
    public static final String Table3_Column9_maxZ = "maxZ";
    public static final String Table3_Column10_minX = "minX";
    public static final String Table3_Column11_minY = "minY";
    public static final String Table3_Column12_minZ = "minZ";
    public static final String Table3_Column13_Q1X = "Q1X";
    public static final String Table3_Column14_Q3X = "Q3X";
    public static final String Table3_Column15_Q1Y = "Q1Y";
    public static final String Table3_Column16_Q3Y = "Q3Y";
    public static final String Table3_Column17_Q1Z = "Q1Z";
    public static final String Table3_Column18_Q3Z = "Q3Z";
    public static final String Table3_Column19_speed = "Speed";
    public static final String Table3_Column20_time = "Time";

    String userId;
    Double averageX;
    Double averageY;
    Double averageZ;
    Double varianceX;
    Double varianceY;
    Double varianceZ;
    Double maxX;
    Double maxY;
    Double maxZ;
    Double minY;
    Double minX;
    Double minZ;
    Double Q1X;
    Double Q3X;
    Double Q3Y;
    Double Q1Y;
    Double Q1Z;
    Double Q3Z;
    Double Speed;


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_3 + " ("
            + Table3_Column_UserId + " VARCHAR, "
            + Table3_Column1_averageX + " REAL, "
            + Table3_Column2_averageY + " REAL, "
            + Table3_Column3_averageZ + " REAL, "
            + Table3_Column4_varianceX + " REAL, "
            + Table3_Column5_varianceY + " REAL, "
            + Table3_Column6_varianceZ + " REAL, "
            + Table3_Column7_maxX + " REAL, "
            + Table3_Column8_maxY + " REAL, "
            + Table3_Column9_maxZ + " REAL, "
            + Table3_Column10_minX + " REAL, "
            + Table3_Column11_minY + " REAL, "
            + Table3_Column12_minZ + " REAL, "
            + Table3_Column13_Q1X + " REAL, "
            + Table3_Column14_Q3X + " REAL, "
            + Table3_Column15_Q1Y + " REAL, "
            + Table3_Column16_Q3Y + " REAL, "
            + Table3_Column17_Q1Z + " REAL, "
            + Table3_Column19_speed + " REAL, "
            + Table3_Column18_Q3Z + " REAL, FOREIGN KEY (" + Table3_Column_UserId + ") REFERENCES " + UserDetails.TABLE_NAME + "(" + UserDetails.Table_Column_2_Email + "));";

    public static final String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_4 + " ("
            + Table3_Column_UserId + " VARCHAR, "
            + Table3_Column1_averageX + " REAL, "
            + Table3_Column2_averageY + " REAL, "
            + Table3_Column3_averageZ + " REAL, "
            + Table3_Column4_varianceX + " REAL, "
            + Table3_Column5_varianceY + " REAL, "
            + Table3_Column6_varianceZ + " REAL, "
            + Table3_Column7_maxX + " REAL, "
            + Table3_Column8_maxY + " REAL, "
            + Table3_Column9_maxZ + " REAL, "
            + Table3_Column10_minX + " REAL, "
            + Table3_Column11_minY + " REAL, "
            + Table3_Column12_minZ + " REAL, "
            + Table3_Column13_Q1X + " REAL, "
            + Table3_Column14_Q3X + " REAL, "
            + Table3_Column15_Q1Y + " REAL, "
            + Table3_Column16_Q3Y + " REAL, "
            + Table3_Column17_Q1Z + " REAL, "
            + Table3_Column19_speed + " REAL, "
            + Table3_Column18_Q3Z + " REAL, FOREIGN KEY (" + Table3_Column_UserId + ") REFERENCES " + UserDetails.TABLE_NAME + "(" + UserDetails.Table_Column_2_Email + "));";

    public CalibrationDetails() {

    }

    public CalibrationDetails(String userId, Double averageX, Double averageY, Double averageZ, Double varianceX, Double varianceY, Double varianceZ, Double maxX, Double maxY, Double maxZ, Double minX, Double minY, Double minZ
    , Double Q1X, Double Q3X, Double Q1Y, Double Q3Y, Double Q1Z, Double Q3Z, Double Speed){
        this.userId = userId;
        this.averageX = averageX;
        this.averageY = averageY;
        this.averageZ = averageZ;
        this.varianceX = varianceX;
        this.varianceY = varianceY;
        this.varianceZ = varianceZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.Q1X = Q1X;
        this.Q3X = Q3X;
        this.Q1Y = Q1Y;
        this.Q3Y = Q3Y;
        this.Q1Z = Q1Z;
        this.Q3Z = Q3Z;
        this.Speed = Speed;
    }

    public String getUserId(){ return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public Double getAverageX(){ return averageX;}
    public void setAverageX(Double averageX){this.averageX = averageX;}

    public Double getAverageY(){ return averageY;}
    public void setAverageY(Double averageY){this.averageY = averageY;}

    public Double getAverageZ(){ return averageZ; }
    public void setAverageZ(Double averageZ){this.averageZ = averageZ;}

    public Double getVarianceX(){ return varianceX;}
    public void setVarianceX(Double varianceX){this.varianceX = varianceX;}

    public Double getVarianceY(){ return varianceY;}
    public void setVarianceY(Double varianceY){this.varianceY = varianceY;}

    public Double getVarianceZ(){ return varianceZ;}
    public void setVarianceZ(Double varianceZ){this.varianceZ = varianceZ;}

    public Double getMaxX(){return maxX;}
    public void setMaxX(Double maxX){this.maxX = maxX;}

    public Double getMaxY(){ return maxY;}
    public void setMaxY(Double maxY){ this.maxY = maxY;}

    public Double getMaxZ() { return maxZ;}
    public void setMaxZ(Double maxZ){this.maxZ = maxZ;}

    public Double getMinX(){ return minX;}
    public void setMinX(Double minX){this.minX = minX;}

    public Double getMinY(){return minY;}
    public void setMinY(Double minY){this.minY = minY;}

    public Double getMinZ(){return minZ;}
    public void setMinZ(Double minZ){this.minZ = minZ;}

    public Double getQ1X(){return Q1X;}
    public void setQ1X(Double Q1X){this.Q1X = Q1X;}

    public Double getQ3X(){return Q3X;}
    public void setQ3X(Double Q3X){this.Q3X = Q3X; }

    public Double getQ1Y(){return Q1Y;}
    public void setQ1Y(Double Q1Y){this.Q1Y = Q1Y;}

    public Double getQ3Y(){return Q3Y;}
    public void setQ3Y(Double Q3Y){this.Q3Y = Q3Y;}

    public Double getQ1Z(){return Q1Z;}
    public void setQ1z(Double Q1Z){this.Q1Z = Q1Z;}

    public Double getQ3Z(){return Q3Z;}
    public void setQ3Z(Double Q3Z){this.Q3Z = Q3Z;}

    public Double getSpeed(){return Speed;}
    public void setSpeed(Double Speed){this.Speed = Speed;}








}
