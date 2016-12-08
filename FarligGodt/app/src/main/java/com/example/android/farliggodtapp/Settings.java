package com.example.android.farliggodtapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.farliggodtapp.database.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class Settings extends AppCompatActivity {

    private DatabaseHelper db;

    TextView textViewRadius;
    SeekBar seekBarRadius;
    int progress;

    private String type = "km";
    private String textType = "km";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);


        // Radio Buttons

        RadioGroup distanceType = (RadioGroup) findViewById(R.id.distanceType);
        Button km = (RadioButton) findViewById(R.id.km);
        Button miles = (RadioButton) findViewById(R.id.miles);
        Button nautical = (RadioButton) findViewById(R.id.nautical);

        if (db.fetchType("distanceType") == null) {
            db.updateOrInsert("distanceType", "km");
        }

        switch (db.fetchType("distanceType")) {

            case "km":
                distanceType.check(R.id.km);
                type = "km";
                textType = getString(R.string.km);
                break;
            case "miles":
                distanceType.check(R.id.miles);
                type = "Miles";
                textType = getString(R.string.miles);
                break;
            case "nautical":
                distanceType.check(R.id.nautical);
                type = "Nautical";
                textType = getString(R.string.nautical);
                break;
            default:
                distanceType.check(R.id.km);
                break;
        }

        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "km");
                type = "km";
                textType = getString(R.string.km);
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });

        miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "miles");
                type = "Miles";
                textType = getString(R.string.miles);
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });

        nautical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "nautical");
                type = "Nautical";
                textType = getString(R.string.nautical);
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });

        //////////////// SEEKBAR FOR RADIUS ///////////////////////

        seekBarRadius = (SeekBar) findViewById(R.id.radius_bar);
        seekBarRadius.setMax(50);

        progress = Integer.parseInt(db.fetchType("radius"));
        seekBarRadius.setProgress(progress);

        textViewRadius = (TextView) findViewById(R.id.radiusNumber);

        updateRangeText(progress);

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                db.updateOrInsert("radius", Integer.toString(i));

                updateRangeText(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        ////////// Checkboxes for season-filter //////////
        CheckBox seasonCheck = (CheckBox) findViewById(R.id.seasonCheck);

        seasonCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Log.v("tag", "check");
                } else {
                    Log.v("tag", "uncheck");
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Convert Km to miles or nautical miles
     *
     * @param i
     */
    public void updateRangeText(int i) {
        double value = i;

        switch (type) {
            case "Miles":
                double km_to_miles = 0.621371192;
                value = i * km_to_miles;
                break;
            case "Nautical":
                double km_to_nautical = 0.539957;
                value = i * km_to_nautical;
                break;
        }
        @SuppressLint("DefaultLocale") String OneDecimal = String.format("%.1f", value) + " " + textType;
        textViewRadius.setText(OneDecimal);
    }


    /**
     * open Main Maps Activity
     *
     * @param view main
     */
    public void openMain(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Settings Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
