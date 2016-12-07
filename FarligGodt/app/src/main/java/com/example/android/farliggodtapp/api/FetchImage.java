package com.example.android.farliggodtapp.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Agne Ødegaard on 07/12/2016.
 */

public class FetchImage extends AsyncTask<String, Void, Bitmap> {

    private ImageCallback callback;

    public FetchImage(ImageCallback callback, String url){
        this.callback = callback;
        this.execute(url);
    }

    protected Bitmap doInBackground(String... src) {
        try {
            URL url = new URL(src[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);

            connection.connect();

            InputStream input = connection.getInputStream();

            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Exception exception = e;

            return null;
        }
    }

    protected void onPostExecute(Bitmap image) {
        callback.imageLoaded(image);
    }
}