package com.example.android.farliggodtapp;

import android.annotation.SuppressLint;
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
    int progress;

    private String type = "km";
    private String textType = "km";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = new DatabaseHelper(this);


        // Radio Buttons

        RadioGroup distanceType = (RadioGroup) findViewById(R.id.distanceType);
        Button km = (RadioButton) findViewById(R.id.km);
        Button miles = (RadioButton) findViewById(R.id.miles);
        Button nautical = (RadioButton) findViewById(R.id.nautical);

        if(db.fetchType("distanceType") == null){
            db.updateOrInsert("distanceType", "km");
        }

        switch (db.fetchType("distanceType")){

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
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });

        miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "miles");
                type = "Miles";
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });

        nautical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateOrInsert("distanceType", "nautical");
                type = "Nautical";
                updateRangeText(Integer.parseInt(db.fetchType("radius")));
            }
        });


        //////////////// SEEKBAR FOR RADIUS ///////////////////////

        seekBarRadius = (SeekBar) findViewById(R.id.radius_bar);
        seekBarRadius.setMax(50);

        progress = Integer.parseInt(db.fetchType("radius"));
        seekBarRadius.setProgress(progress);

        textViewRadius = (TextView) findViewById(R.id.radiusNumber);
        textViewRadius.setText(progress + " " + textType);

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


        updateRangeText(Integer.parseInt(db.fetchType("radius")));
    }

    /**
     * Convert Km to miles or nautocal miles
     * @param i
     */
    public void updateRangeText(int i){
        double value = i;

        switch (type){
            case "Miles":
                double km_to_miles = 0.621371192;
                value = i * km_to_miles;
                break;
            case "Nautical":
                double km_to_nautical = 0.539957;
                value = i * km_to_nautical;
                break;
        }
        @SuppressLint("DefaultLocale") String OneDecimal = String.format("%.1f", value);
        textViewRadius.setText(OneDecimal + " " + textType);
    }


    /**
     * open Main Maps Activity
     * @param view
     */
    public void openMain(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

}
