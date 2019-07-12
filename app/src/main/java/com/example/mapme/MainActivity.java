package com.example.mapme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Button button,button2; public static TextView textView;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                if(!prefs.getBoolean("firstTime", false)) {

                    // run your one time code
                    if (fetchJsonFile()) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("firstTime", true);
                        editor.commit();
                    }
                }
                else
                    Toast.makeText(MainActivity.this,"JSON data has already been fetched",Toast.LENGTH_LONG).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                if(!prefs.getBoolean("firstTime", false)) {
                    Toast.makeText(MainActivity.this,"JSON data isn't fetched yet",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, employee_list_activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean fetchJsonFile(){
        //Checking if data connection is on
        if (hasConnectivity(this)) {

            //Calling a AsyncTask process to fetch JSON from server and parse it
            FetchJsonData process = new FetchJsonData(MainActivity.this);
            process.execute();

            return true;
        }
        else {
            Toast.makeText(MainActivity.this,"No internet connection. Please try again..",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static boolean hasConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
    }

}
