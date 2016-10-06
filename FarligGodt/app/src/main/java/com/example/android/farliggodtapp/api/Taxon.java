package com.example.android.farliggodtapp.api;

import org.json.JSONObject;

/**
 * Created by orangee on 06/10/16.
 */


public class Taxon implements JSONpop {

    public String name;
    public Double lat;
    public Double lng;
    public int taxonID;
    public double distance;

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public void populate(JSONObject data) {

        name = data.optString("navn");
        lat = data.optDouble("lat");
        lng = data.optDouble("lng");
        taxonID = data.optInt("taxonID");
        distance = data.optDouble("distance");

    }
}
