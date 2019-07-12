package com.example.mapme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class saveLocation_map_activity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map; Integer id; String name; Double latitude, longitude;
    Button button; DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location_map_activity);


        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);

        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocation();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng locationPoint = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(locationPoint).title(name));
        map.moveCamera(CameraUpdateFactory.newLatLng(locationPoint));

        //button.setText(latitude+" "+longitude);
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        startActivity(new Intent(this, employee_list_activity.class));
        finish();
    }

    //saves employee location in DB
    private void saveLocation(){
        myDb = new DatabaseHelper(this);
        Boolean updated = myDb.updateData(id, latitude.toString(), longitude.toString());
        if (updated){
            Toast.makeText(this, "Database Updated", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Database Not Updated", Toast.LENGTH_LONG).show();
        }
    }
}
