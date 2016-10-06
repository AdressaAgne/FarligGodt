package com.example.android.farliggodtapp.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by orangee on 06/10/16.
 */

public class Api{


    private ApiCallback callback;

    private Exception error;

    private Double lat;
    private Double lng;
    private int dist;

    public Api(ApiCallback callback) {
        this.callback = callback;

    }

    public void refreshQuery(final Double lat, final Double lng, final int dist){
        this.lat = lat;
        this.lng = lng;
        this.dist = dist;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                //lat: 59.342836
                //lng: 5.298503
                //dist: 25
                String endPoint = "https://webpro3.agne.no/nearby_api/"+Double.toString(lat)+"/"+Double.toString(lng)+"/"+Double.toString(dist);
                //String endPoint = "https://webpro3.agne.no/nearby_api/59.342836/5.298503/25";



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
