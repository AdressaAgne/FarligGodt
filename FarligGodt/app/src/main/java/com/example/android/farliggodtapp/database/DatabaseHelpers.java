package com.example.android.farliggodtapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Agne Ødegaard on 06/10/16.
 */

public class DatabaseHelpers extends SQLiteOpenHelper {

    private static final String databaseName = "FarligGodt.db";
    private static final String tableName = "saved";

    private static final String tableColValue = "value";
    private static final String tableColType = "type";


    public DatabaseHelpers(Context context) {
        super(context, databaseName, null, 1);
    }

    /**
     * When the database is created
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (" + tableColValue + " VARCHAR(255), " + tableColType + " VARCHAR(255) PRIMARY KEY UNIQUE)");
    }

    /**
     * When the app is uninstalled
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    /**
     * Insert a row into the database
     * @param type
     * @param value
     * @return
     */
    private boolean insertData(String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);
        cv.put(tableColType, type);

        long result = db.insert(tableName, null, cv);

        return result > -1;
    }

    /**
     * Update a row in the database
     * @param type
     * @param value
     * @return
     */
    private boolean updateData(String type, String value){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);

        int result = db.update(tableName, cv, tableColType + " = ?", new String[] {String.valueOf(type) });

        return result > -1;
    }

    /**
     * Fetch a value from the database
     * @param type
     * @return value
     */
    public String fetchType(String type){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE type = ?", new String[] {String.valueOf(type) });


        if(!c.moveToFirst()) {
            c.close();
            return null;
        }


        String val = c.getString(c.getColumnIndex(tableColValue));
        c.close();
        return val;

    }

    /**
     * Update or Insert a value into the database
     * @param type
     * @param value
     */
    public void updateOrInsert(String type, String value){
        if(!insertData(type, value)) {
            updateData(type, value);
        }
    }
}
