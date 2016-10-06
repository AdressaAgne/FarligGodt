package com.example.android.farliggodtapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        progress = Integer.parseInt(db.fetchType("radius"));;

        if(progress == 0){
            progress = 25;
            db.updateOrInsert("radius", "25");
        }

        seekBarRadius.setProgress(progress);

        textViewRadius = (TextView) findViewById(R.id.radiusNumber);
        textViewRadius.setText(progress + " km");

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
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

        ////////////////////////////////////////////////////////////////////////////////////
    }
}
