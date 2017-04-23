package com.example.jonatan.clothesplanner.wardrobe.wardrobedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jonatan.clothesplanner.wardrobe.IStorageHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

/**
 * Created by Jonatan on 2017-04-23.
 */

public class WardrobeDbHelper extends SQLiteOpenHelper implements IStorageHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WardrobeReader.db";
    public static final String SHIRT = "shirt";
    public static final String TROUSERS = "trousers";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME + " (" +
                    WardrobeReaderContract.WardrobeEntry._ID + " INTEGER PRIMARY KEY," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME;

    SQLiteDatabase database;

    public WardrobeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
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

    @Override
    public void loadWardrobe(IWardrobe wardrobe) {

    }

    @Override
    public void storeWardrobe(IWardrobe wardrobe) {
        database.execSQL(SQL_DELETE_ENTRIES);
        database.execSQL(SQL_CREATE_ENTRIES);
        for (IWardrobeItem item : wardrobe.getShirts()) {
            ContentValues values = new ContentValues();
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE, SHIRT);
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION, item.getWardrobeItemString());
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE, item.getDrawable().toString());
            database.insert(WardrobeReaderContract.WardrobeEntry.TABLE_NAME, null, values);
        }

        for (IWardrobeItem item : wardrobe.getTrousers()) {
            ContentValues values = new ContentValues();
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE, TROUSERS);
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION, item.getWardrobeItemString());
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE, item.getDrawable().toString());
            database.insert(WardrobeReaderContract.WardrobeEntry.TABLE_NAME, null, values);
        }
    }

    @Override
    public int[] loadWeeklyPlanIndex() {
        return new int[0];
    }

    @Override
    public void storeWeeklyPlanIndex(int weeklyPlanShirtIndex, int weeklyPlanTrousersIndex) {

    }

    @Override
    public void closeStorage() {
        database.close();
    }
}

