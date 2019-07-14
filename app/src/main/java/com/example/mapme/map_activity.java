package com.example.mapme;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class map_activity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap map; String name; Double latitude, longitude; LatLng locationPoint;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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
        map.setOnMarkerClickListener(this);

        locationPoint = new LatLng(latitude, longitude);
        myMarker = map.addMarker(new MarkerOptions().position(locationPoint).title(name));
        myMarker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(locationPoint));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)){
            marker.showInfoWindow();

            //Toast.makeText(this, "You clicked the marker", Toast.LENGTH_LONG).show();
            Intent intent =  new Intent(map_activity.this, jobSchedulerActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, employee_list_activity.class));
        finish();
    }

    /*//method intended for custom marker; not configured yet
    public void customBitmapDescriptor(){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
        Canvas canvas1 = new Canvas(bmp);

        // paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

        // modify canvas
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.newton), 0,0, color);
        canvas1.drawText("User Name!", 30, 40, color);

        // add marker to Map
        map.addMarker(new MarkerOptions()
                .position(locationPoint)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));
    }*/

}
