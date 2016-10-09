package com.example.android.farliggodtapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ApiCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    public double longitude, latitude;

    public Api taxonApi;

    private DatabaseHelper db;

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private ProgressDialog apiProgress;

    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private boolean customLocation = false;

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

        if (db.fetchType("radius") == null) {
            db.updateOrInsert("radius", "25");
        }
        apiProgress = new ProgressDialog(this);
        apiProgress.setMessage("Loading species near you.");
        apiProgress.show();

        requestFineLocationPermit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Permission request for Android 6.0 and later
     */
    public void requestFineLocationPermit(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            requestLoc();
        }
    }

    /**
     * Permission request for Android 6.0 and later
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Register the listener with the Location Manager to receive location updates
                    requestLoc();

                } else {
                    requestFineLocationPermit();
                }
            }
        }
    }

    /**
     * request location updates
     */
    public void requestLoc(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //setLatLng(location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(!customLocation) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));

                    LatLng current = new LatLng(latitude, longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 12));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 60000, locationListener);

    }


    /**
     * Google maps init
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    /**
     * open Settings Activity
     * @param view
     */
    public void openSettings(View view){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    /**
     * Api successfully got data
     * @param taxons
     */
    @Override
    public void serviceSuccess(Taxon[] taxons) {
        apiProgress.hide();
        apiProgress.dismiss();

        int dataLength = taxons.length;
        for (int i = 0; i < dataLength; i++) {
            taxons[i].getLat();
            LatLng taxonLatLng = new LatLng(taxons[i].getLat(), taxons[i].getLng());
            mMap.addMarker(new MarkerOptions().position(taxonLatLng).title(taxons[i].getName()));
        }

    }

    /**
     * Api service failed to get data
     * @param exc
     */
    @Override
    public void serviceFailed(Exception exc) {
        String msg = "Could not load species, check your internet connection.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                })
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        customLocation = true;
        apiProgress.setMessage("Loading species near selected location.");
        apiProgress.show();

        taxonApi.refreshQuery(latLng.latitude, latLng.longitude, db.fetchType("radius"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
    }
}
