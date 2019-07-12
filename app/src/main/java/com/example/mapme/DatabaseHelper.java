package com.example.mapme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class DatabaseHelper extends SQLiteOpenHelper {
   SQLiteDatabase db;

   public static final String DATABASE_NAME = "employees.db";
   public static final String TABLE_NAME = "employees";
   public static final String EMPLOYEE_ID = "ID";
   public static final String EMPLOYEE_NAME = "NAME";
   public static final String EMPLOYEE_LOCATION = "LOCATION";
   public static final String EMPLOYEE_LATITUDE = "LATITUDE";
   public static final String EMPLOYEE_LONGITUDE = "LONGITUDE";

   public DatabaseHelper(Context context){
       super(context, DATABASE_NAME, null, 1);
       db = this.getWritableDatabase();
   }

   @Override
   public void onCreate(SQLiteDatabase db){
       db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LOCATION INTEGER,LATITUDE TEXT,LONGITUDE TEXT)");
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Takes json array as input, write new column values into database table
    public boolean writeJsonDataToDb(JSONArray jsonArray) {
       try {
           for (int i = 0; i < jsonArray.length(); i++){
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               String name, latitude, longitude; Integer location;

               name = jsonObject.getString("name");

               String locationObjStr = ""+jsonObject.get("location");
               if (locationObjStr.equals("null")){
                   location = 0;
                   latitude = null; longitude = null;
               } else {
                   location = 1;
                   JSONObject locationObj = new JSONObject(locationObjStr);
                   latitude =  locationObj.getString("latitude");
                   longitude = locationObj.getString("longitude");
               }

               ContentValues contentValues = new ContentValues();
               contentValues.put(EMPLOYEE_NAME,name);
               contentValues.put(EMPLOYEE_LOCATION,location);
               contentValues.put(EMPLOYEE_LATITUDE,latitude);
               contentValues.put(EMPLOYEE_LONGITUDE,longitude);
               long result = db.insert(TABLE_NAME,null ,contentValues);
               if(result == -1)
                   return false;
               else
                   continue;
           }
       }
       catch (JSONException e){
           e.printStackTrace();
       }
       return true;
    }

    //Deletes all values from a table
    public void deleteAllValues(){
        db.execSQL("delete from "+ TABLE_NAME);
    }

    //Returns all values from a table
    public Cursor viewData(){
       String query = "select * from "+TABLE_NAME;
       Cursor cursor = db.rawQuery(query,null);
       return cursor;
    }

    //Return single column values for given id
    public Cursor viewSingleData (Integer id){
       String query = "select * from "+TABLE_NAME+ " where "+EMPLOYEE_ID+" = "+id+"";
       Cursor cursor = db.rawQuery(query,null);
       return cursor;
    }

    //Updates location, latitude, longitude column of the given id
    public boolean updateData(Integer id, String latitude, String longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_LOCATION,1);
        contentValues.put(EMPLOYEE_LATITUDE,latitude);
        contentValues.put(EMPLOYEE_LONGITUDE,longitude);
        db.update(TABLE_NAME, contentValues, EMPLOYEE_ID+"="+id, null);
        return true;
    }
}
