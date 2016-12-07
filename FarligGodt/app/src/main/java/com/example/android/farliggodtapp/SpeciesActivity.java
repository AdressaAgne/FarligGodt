package com.example.android.farliggodtapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.farliggodtapp.R;
import com.example.android.farliggodtapp.api.FetchImage;
import com.example.android.farliggodtapp.api.ImageCallback;
import com.example.android.farliggodtapp.api.Specie;
import com.example.android.farliggodtapp.database.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thomhess on 30.11.2016.
 */

public class SpeciesActivity extends AppCompatActivity implements ImageCallback {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specie);

        String taxonID = getIntent().getStringExtra("taxonID");

        TextView name = (TextView) findViewById(R.id.name);

        TextView desc = (TextView) findViewById(R.id.desc);
        TextView byline = (TextView) findViewById(R.id.byline);

        DatabaseHelper db = new DatabaseHelper(this);

        Specie taxon = db.getSpecie(Integer.parseInt(taxonID));

        name.setText(taxon.getName());

        String bylineText = taxon.getLatin() + ", " + taxon.getFamily();
        byline.setText(bylineText);
        desc.setText(taxon.getRisk());

        setTitle(taxon.getName());

        String imageURL = "https://farliggodt.agne.no" + taxon.getImage();
        new FetchImage(this, imageURL);

        Log.v("blacklist", "image: " + taxon.getImage() + ", " + imageURL);

    }


    @Override
    public void imageLoaded(Bitmap image) {
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}

