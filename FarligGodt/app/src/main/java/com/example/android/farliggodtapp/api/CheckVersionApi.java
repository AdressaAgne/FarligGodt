package com.example.android.farliggodtapp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.farliggodtapp.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Agne Ødegaard on 06/12/2016.
 */

public class CheckVersionApi {
    private DatabaseHelper db;
    private Exception error;
    private VersionCallback callback;

    public CheckVersionApi(VersionCallback callback, Context context) {
        db = new DatabaseHelper(context);
        this.callback = callback;
    }


    public void checkVersion(){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String endPoint = "https://farliggodt.agne.no/api/check";
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
                    callback.versionAPIFailed(error);
                    return;
                }

                try {
                    JSONArray data = new JSONArray(s);
                    JSONObject json = data.getJSONObject(0);
                    String version = json.optString("version");
                    float v = Float.parseFloat(version);
                    float currentVersion = Float.parseFloat(db.fetchType("version"));
                    if(v > currentVersion){
                        callback.onOldVersion(version);
                    } else {
                        callback.onUptoDateVersion();
                    }

                    callback.versionAPISuccess(version);

                } catch (JSONException e) {
                    Log.d("taxon", e.toString());
                    callback.versionAPIFailed(e);
                }


            }
        }.execute();


    }

}

