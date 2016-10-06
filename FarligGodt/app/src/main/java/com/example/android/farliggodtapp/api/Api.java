package com.example.android.farliggodtapp.api;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by orangee on 06/10/16.
 */

public class Api{


    private ApiCallback callback;

    private Exception error;

    private String lat;
    private String lng;
    private int dist;

    public Api(ApiCallback callback) {
        this.callback = callback;

    }

    public void refreshQuery(final String location, final String lat, final String lng, final int dist){
        this.lat = lat;
        this.lng = lng;
        this.dist = dist;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                //lat: 59.342836
                //lng: 5.298503
                //dist: 25
                String endPoint = "https://webpro3.agne.no/nearby_api/"+lat+"/"+lng+"/"+dist;

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
                    callback.serviceFailed(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResult = data.optJSONObject("");

                    Taxon species = new Taxon();
                    species.populate(queryResult);

                    callback.serviceSuccess(species);

                } catch (JSONException e) {
                    callback.serviceFailed(e);
                }


            }
        }.execute(location);
    }

    public class LocWeatherException extends Exception{
        public LocWeatherException(String message) {
            super(message);
        }
    }
}
