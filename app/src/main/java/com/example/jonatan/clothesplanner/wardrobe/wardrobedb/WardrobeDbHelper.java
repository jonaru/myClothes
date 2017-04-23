package com.example.jonatan.clothesplanner.wardrobe.wardrobedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jonatan on 2017-04-23.
 */

public class WardrobeDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WardrobeReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME + " (" +
                    WardrobeReaderContract.WardrobeEntry._ID + " INTEGER PRIMARY KEY," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME;

    public WardrobeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
