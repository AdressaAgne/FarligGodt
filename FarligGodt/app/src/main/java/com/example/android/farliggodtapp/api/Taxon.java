package com.example.android.farliggodtapp.api;

import org.json.JSONObject;

/**
 * Created by orangee on 06/10/16.
 */


public class Taxon implements JSONpop {

    public String name;
    public String lat;
    public String lng;
    public int taxonID;
    public double distance;

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
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
        lat = data.optString("lat");
        lng = data.optString("lng");
        taxonID = data.optInt("taxonID");
        distance = data.optDouble("distance");

    }
}
