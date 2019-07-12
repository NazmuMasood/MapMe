package com.example.mapme;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
;import java.net.URL;

public class FetchJsonData extends AsyncTask<Void, Void, String> {
    String data, dataParsed="", singleDataParsed; JSONArray jsonArray;
    private static final String TAG = FetchJsonData.class.getSimpleName();
    DatabaseHelper myDb;

    Context context;


    public FetchJsonData(Context context){
        this.context = context;
    }

    //Main task i.e. file fetching and parsing
    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://anontech.info/courses/cse491/employees.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            if (data==null){
                dataParsed="Null value found";
                return dataParsed;
            }

            String crappyPrefix = "null";
            if(data.startsWith(crappyPrefix)){
                data = data.substring(crappyPrefix.length());
            }
            if(data.endsWith(crappyPrefix)){
                data = data.substring(0, data.length()-4);
            }

            jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                singleDataParsed = "Name: " + jsonObject.get("name") + ", ";

                String locationObjStr = ""+jsonObject.get("location");
                if (locationObjStr.equals("null")){
                    singleDataParsed = singleDataParsed + "Latitude: null, Longitude: null" + "\n";
                } else {
                    JSONObject locationObj = new JSONObject(locationObjStr);
                    singleDataParsed = singleDataParsed + "Latitude: " + locationObj.get("latitude") +
                            ", Longitude: " + locationObj.get("latitude") + "\n";
                }

                dataParsed = dataParsed + singleDataParsed + "\n";
            }

            /*for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String locationObjStr = ""+jsonObject.get("location");

                if (locationObjStr.equals("null")){
                    dataParsed = dataParsed + "Latitude: null, Longitude: null" + "\n";
                } else {
                    JSONObject locationObj = new JSONObject(locationObjStr);
                    dataParsed = dataParsed + "Latitude: " + locationObj.get("latitude") +
                            ", Longitude: " + locationObj.get("latitude") + "\n";
                }
            }*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        }
        return dataParsed;
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        /*Toast toast = Toast.makeText(context,"JSON data: "+this.dataParsed+"\n"+"Raw data: "+data,Toast.LENGTH_LONG);
        toast.show();*/
        MainActivity.textView.setText(this.dataParsed);
        myDb = new DatabaseHelper(context);
        myDb.deleteAllValues();
        boolean isInserted = myDb.writeJsonDataToDb(jsonArray);
        if(isInserted)
            Toast.makeText(context,"Data Inserted into database",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"Data not Inserted into database",Toast.LENGTH_LONG).show();
    }

}
