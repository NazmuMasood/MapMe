package com.example.mapme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class try_map_activity extends FragmentActivity implements OnMapReadyCallback{
    GoogleMap map; String name; Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_map);

        name = getIntent().getStringExtra("name");
        latitude =  Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude =  Double.parseDouble(getIntent().getStringExtra("longitude"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng locationPoint = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(locationPoint).title(name));
        map.moveCamera(CameraUpdateFactory.newLatLng(locationPoint));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, employee_list_activity.class));
        finish();
    }
}
