package com.example.android.farliggodtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.farliggodtapp.database.DatabaseHelper;

public class Settings extends AppCompatActivity {

    private DatabaseHelper db;

    TextView textViewRadius;
    SeekBar seekBarRadius;
    int progress = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = new DatabaseHelper(this);

        //////////////// SEEKBAR FOR RADIUS ///////////////////////

        seekBarRadius = (SeekBar) findViewById(R.id.radius_bar);
        seekBarRadius.setMax(100);

        progress = Integer.parseInt(db.fetchType("radius"));
        seekBarRadius.setProgress(progress);

        textViewRadius = (TextView) findViewById(R.id.radiusNumber);
        textViewRadius.setText(progress + " km");

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 1km in miles 0.621371192, 1mile in km 1.609344
                progress = i;
                textViewRadius.setText(progress + "km");
                db.updateOrInsert("radius", Integer.toString(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Radio Buttons

        RadioGroup distanceType = (RadioGroup) findViewById(R.id.distanceType);
        Button km = (RadioButton) findViewById(R.id.km);
        Button miles = (RadioButton) findViewById(R.id.miles);
        Button nautical = (RadioButton) findViewById(R.id.nautical);

        switch (db.fetchType("distanceType")){

            case "km":
                distanceType.check(R.id.km);
                break;
            case "miles":
                distanceType.check(R.id.miles);
                break;
            case "nautical":
                distanceType.check(R.id.nautical);
                break;
            default:
                db.updateOrInsert("distanceType", "km");
                break;
        }


        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "km");
            }
        });

        miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "miles");
            }
        });

        nautical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "nautical");
            }
        });
    }


    // open next activity //
    public void openMain(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

}
