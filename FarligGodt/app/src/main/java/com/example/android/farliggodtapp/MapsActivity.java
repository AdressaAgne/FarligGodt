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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.android.farliggodtapp.api.Api;
import com.example.android.farliggodtapp.api.ApiCallback;
import com.example.android.farliggodtapp.api.BlacklistCallback;
import com.example.android.farliggodtapp.api.FetchBlacklist;
import com.example.android.farliggodtapp.api.Specie;
import com.example.android.farliggodtapp.api.Taxon;
import com.example.android.farliggodtapp.database.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ApiCallback, GoogleMap.OnMapClickListener, BlacklistCallback {

    private GoogleMap mMap = null;

    public double longitude, latitude;

    public Api taxonApi;
    public FetchBlacklist blacklist;

    private DatabaseHelper db;

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private ProgressDialog apiProgress;

    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private boolean customLocation = false;

    private SearchView searchInput;

    private String[] speciesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(this);
        taxonApi = new Api(this);
        blacklist = new FetchBlacklist(this);

        if (db.fetchType("radius") == null) {
            db.updateOrInsert("radius", "25");
        }
        apiProgress = new ProgressDialog(this);
        apiProgress.setMessage(getString(R.string.loadTaxonInit));
        apiProgress.show();



        String lastLat = db.fetchType("lastLat");
        String lastLng = db.fetchType("lastLng");

        if(lastLat != null && lastLng != null){
            latitude = Double.parseDouble(lastLat);
            longitude = Double.parseDouble(lastLng);
            taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
            changeCamera(latitude, longitude);
        }

        requestFineLocationPermit();

        //Searchfield listener
        speciesList = db.getBlacklistString();

        AutoCompleteTextView searchbar = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, speciesList);

        searchbar.setAdapter(adapter);

        searchbar.setThreshold(1);


    }

    /**
     * when menu is created
     * @param menu Menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * when a menu item is selected/clicked
     * @param item MenuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
     * @param requestCode int
     * @param permissions string array
     * @param grantResults int array
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(lastLoc != null){
            latitude = lastLoc.getLatitude();
            longitude = lastLoc.getLongitude();
            taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
        }

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(!customLocation) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
                    db.updateOrInsert("lastLat", Double.toString(latitude));
                    db.updateOrInsert("lastLng", Double.toString(longitude));
                    changeCamera(latitude, longitude);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS disabled", Toast.LENGTH_SHORT).show();
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 30000, locationListener);

    }

    public void changeCamera(double lat, double lng){
        if(mMap == null) return;

        LatLng current = new LatLng(lat, lng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 12));


    }


    /**
     * Google maps init
     * @param googleMap mMap
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
     * Api successfully got data
     * @param taxons array of Taxon
     */
    @Override
    public void serviceSuccess(Taxon[] taxons) {
        if(mMap == null) return;

        apiProgress.hide();
        apiProgress.dismiss();

        for (Taxon taxon : taxons) {
            taxon.getLat();
            LatLng taxonLatLng = new LatLng(taxon.getLat(), taxon.getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(taxonLatLng)
                    .title(taxon.getName())
                    .snippet("Get short description from database")
            );

            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.filnavn))
            //.anchor(0.0f, 1.0f)
        }

    }

    /**
     * Api service failed to get data
     * @param exc error msg
     */
    @Override
    public void serviceFailed(Exception exc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.errorAlert)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.tryAgain), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                })
                .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taxonApi.refreshQuery(latitude, longitude, db.fetchType("radius"));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * When the map fragment is clicked
     * @param latLng coords
     */
    @Override
    public void onMapClick(LatLng latLng) {
        Log.v("gps", "ClickOnMap");
        customLocation = true;
        apiProgress.setMessage(getString(R.string.loadTaxon));
        apiProgress.show();

        taxonApi.refreshQuery(latLng.latitude, latLng.longitude, db.fetchType("radius"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
    }

    @Override
    public void blacklistSuccess(Specie[] species) {
        Toast.makeText(getBaseContext(), "Fetching Blacklist Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void blacklistFailed(Exception exc) {
        Toast.makeText(getBaseContext(), "Blacklist Featch Failed", Toast.LENGTH_LONG).show();
    }
}
