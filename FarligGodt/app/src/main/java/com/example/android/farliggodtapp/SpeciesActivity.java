package com.example.android.farliggodtapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.farliggodtapp.R;

/**
 * Created by thomhess on 30.11.2016.
 */

public class SpeciesActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specie);

        TextView name = (TextView) findViewById(R.id.name);

        String s = getIntent().getStringExtra("navn");

        name.setText(s);

    }



}

