package com.example.android.farliggodtapp.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Agne Ødegaard on 07/12/2016.
 */

public class FetchImage extends AsyncTask<String, Void, Bitmap> {

    private ImageCallback callback;
    private Activity activity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public FetchImage(ImageCallback callback, String url, Activity activity){
        verifyStoragePermissions(activity);
        this.callback = callback;
        this.execute(url);
        this.activity = activity;
    }

    protected Bitmap doInBackground(String... src) {
        Bitmap loadedImage = this.loadImage(src[0]);

        if(loadedImage != null){
            return loadedImage;
        }

        return this.getImage(src[0]);
    }

    private String basename(String str){
        return str.substring(str.lastIndexOf("/")+1);
    }


    private Bitmap loadImage(String src){
        String basename = basename(src);

        String path = Environment.getExternalStorageDirectory()+"/"+basename;

        File file = new File(path);
        Log.v("blacklist", "path: " + path);
        if(file.exists()){
            Log.v("blacklist", "loadImage: " + basename);
            return BitmapFactory.decodeFile(path);
        }

        return null;
    }


    private Bitmap getImage(String src){
        try {
            URL url = new URL("https://farliggodt.agne.no" + src);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);

            connection.connect();

            InputStream input = connection.getInputStream();

            Bitmap bmap = BitmapFactory.decodeStream(input);
            String basename = basename(src);


            File file = new File (Environment.getExternalStorageDirectory(), basename);
            FileOutputStream outputStream = new FileOutputStream(file);
            Log.v("blacklist", "saveImage: " + basename);

            bmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

            return bmap;

        } catch (Exception e) {
            Log.v("blacklist", "saveImage Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    protected void onPostExecute(Bitmap image) {
        callback.imageLoaded(image);
    }
}