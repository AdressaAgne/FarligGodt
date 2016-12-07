package com.example.android.farliggodtapp.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Agne Ødegaard on 07/12/2016.
 */

public class TaxonLocations {

    private Exception error;
    private ApiCallback callback;

    public TaxonLocations(ApiCallback callback) {
        this.callback = callback;
    }


    public void refreshQuery(final String taxonID){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                //lat: 59,342836
                //lng: 5,298503
                String endPoint = "https://farliggodt.agne.no/api/taxon/"+taxonID;
                try {
                    URL url = new URL(endPoint);

                    URLConnection connection = url.openConnection();

                    InputStream stream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuilder result = new StringBuilder();

                    String line;

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();


                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if(s == null && error != null){
                    Log.d("taxon", "Error, could not fetch data");
                    callback.serviceFailed(error);
                    return;
                }


                try {
                    JSONArray data = new JSONArray(s);
                    int dataLength = data.length();
                    Taxon[] taxons = new Taxon[dataLength];

                    for (int i = 0; i < dataLength; i++) {
                        taxons[i] = new Taxon();
                        taxons[i].populate(data.getJSONObject(i));
                    }

                    callback.serviceSuccess(taxons);

                } catch (JSONException e) {
                    Log.d("taxon", e.toString());
                    callback.serviceFailed(e);
                }


            }
        }.execute();
    }

}
