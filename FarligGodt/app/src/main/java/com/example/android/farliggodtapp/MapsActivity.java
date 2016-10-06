package com.example.android.farliggodtapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.farliggodtapp.api.Api;
import com.example.android.farliggodtapp.api.ApiCallback;
import com.example.android.farliggodtapp.api.Taxon;
import com.example.android.farliggodtapp.database.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ApiCallback {

    private GoogleMap mMap;

    public double longitude, latitude;

    public Api taxonApi;

    public String lng, lat;

    private DatabaseHelper db;

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(this);
        taxonApi = new Api(this);

        if(db.fetchType("radius") == null){
            db.updateOrInsert("radius", "25");
        }

        /* *
         * Permission request for Android 6.0 and later
         * */

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


        /* *
         *
         * Getting the location, and making it into the two string elements lat and lng.
         * Uses first the last known location, and later the GPS-location.
         *
         * */

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Gets the last known location while waiting for GPS-connection:
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            lng = Double.toString(longitude);
            lat = Double.toString(latitude);

            Log.v("Latlong1", lat + " and " + lng);
        }



        // Defining a listener that responds to location updates.
        // This is only triggered when GPS is enabled and receives contact with satellites.
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                longitude = location.getLongitude();
                latitude = location.getLatitude();

                lng = Double.toString(longitude);
                lat = Double.toString(latitude);

                // Call function here to do stuff with lat and lng

                Log.v("Latlong2", lat + " and " + lng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
    }

    /* *
     * Permission request for Android 6.0 and later
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Register the listener with the Location Manager to receive location updates
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    /* *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        
        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,12));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    // open next activity //
    public void openSettings(View view){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    @Override
    public void serviceSuccess(Taxon[] taxons) {

        //do stuff when the api fetches stuff
        int dataLength = taxons.length;
        for (int i = 0; i < dataLength; i++) {

            taxons[i].getLat();
            LatLng taxonLatLng = new LatLng(taxons[i].getLat(), taxons[i].getLng());
            mMap.addMarker(new MarkerOptions().position(taxonLatLng).title(taxons[i].getName()));
        }

    }

    @Override
    public void serviceFailed(Exception exc) {

    }
}
