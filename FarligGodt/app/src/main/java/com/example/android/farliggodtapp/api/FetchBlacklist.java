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
 * Created by Agne Ødegaard on 06/12/2016.
 */

public class FetchBlacklist {

    private Exception error;
    private BlacklistCallback callback;

    public FetchBlacklist(BlacklistCallback callback) {
        this.callback = callback;

    }


    public void fetch(){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String endPoint = "https://farliggodt.agne.no/api/blacklist";
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
                    callback.blacklistFailed(error);
                    return;
                }


                try {
                    JSONArray data = new JSONArray(s);
                    int dataLength = data.length();
                    Specie[] species = new Specie[dataLength];


                    for (int i = 0; i < dataLength; i++) {

                        species[i] = new Specie();
                        species[i].populate(data.getJSONObject(i));

                    }

                    callback.blacklistSuccess(species);

                } catch (JSONException e) {
                    Log.d("taxon", e.toString());
                    callback.blacklistFailed(e);
                }


            }
        }.execute();


    }

}
