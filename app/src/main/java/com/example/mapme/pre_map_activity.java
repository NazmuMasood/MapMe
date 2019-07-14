package com.example.mapme;

import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class pre_map_activity extends AppCompatActivity {
    TextView textView;
    HashMap<Integer, Integer> hashMap;
    DatabaseHelper myDb;
    String name, latitude, longitude; Integer id, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_map_activity);

        textView = findViewById(R.id.textView3);
        String message  = getIntent().getStringExtra("message");
        int position = Integer.parseInt(message);

        hashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("HashMap");
        id = hashMap.get(position);
        getInfo(id);


        Intent intent = new Intent(this, map_activity.class);
        intent.putExtra("name", name);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);

        /*
        //Object implementation try
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        ArrayList<Employee> employeeObjects = (ArrayList<Employee>) args.getSerializable("ARRAYLIST");
        String name = employeeObjects.get(position).getName();
        */

//        String display = "Position: "+position+"\n"+"Name: "+name+"\n"+
//                "Latitude: "+latitude+"\n"+"Longitude: "+longitude;
//        textView.setText(display);
    }

    //Fetching information of a specific employee with given id
    private void getInfo(Integer id){
        myDb = new DatabaseHelper(this);

        Cursor cursor = myDb.viewSingleData(id);
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data to show",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()) {
                name = cursor.getString(1);
                location = cursor.getInt(2);
                latitude = cursor.getString(3);
                longitude = cursor.getString(4);
            }
        }
    }
}
