package com.example.android.farliggodtapp.api;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by orangee on 06/10/16.
 */


public class Taxon implements JSONpop {

    public String name;
    public Double lat;
    public Double lng;
    public String taxonID;
    public String distance;

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getTaxonID() {
        return taxonID;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public void populate(JSONObject data) {

        name        = data.optString("navn");
        lat         = data.optDouble("lat");
        lng         = data.optDouble("lng");
        taxonID     = data.optString("taxonID");
        distance    = data.optString("distance");

    }
}
