package com.example.jonatan.clothesplanner.wardrobe.wardrobedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.jonatan.clothesplanner.wardrobe.IStorageHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Jonatan on 2017-04-23.
 */

public class WardrobeDbHelper extends SQLiteOpenHelper implements IStorageHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WardrobeReader.db";
    public static final String SHIRT = "Shirt";
    public static final String TROUSERS = "Trousers";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME + " (" +
                    WardrobeReaderContract.WardrobeEntry._ID + " INTEGER PRIMARY KEY," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WardrobeReaderContract.WardrobeEntry.TABLE_NAME;

    SQLiteDatabase database;
    private Context dbContext;

    public WardrobeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        dbContext = context;
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
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE,
                WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION,
                WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE + " DESC";

        Cursor cursor = database.query(
                WardrobeReaderContract.WardrobeEntry.TABLE_NAME,
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String type = cursor.getString(cursor.getColumnIndex(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE));
                String description = cursor.getString(cursor.getColumnIndex(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION));
                byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBlob);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                Drawable drawable = new BitmapDrawable(dbContext.getResources(), bitmap);
                wardrobe.addWardrobeItem(description, type, drawable);

                cursor.moveToNext();
            }
        }
    }

    @Override
    public void storeWardrobe(IWardrobe wardrobe) {
        database.execSQL(SQL_DELETE_ENTRIES);
        database.execSQL(SQL_CREATE_ENTRIES);
        for (IWardrobeItem item : wardrobe.getShirts()) {
            ContentValues values = new ContentValues();
            Drawable drawable = item.getDrawable();

            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE, SHIRT);
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION, item.getWardrobeItemString());
            if (drawable != null)
            {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE, stream.toByteArray());
            }

            database.insert(WardrobeReaderContract.WardrobeEntry.TABLE_NAME, null, values);
        }

        for (IWardrobeItem item : wardrobe.getTrousers()) {
            ContentValues values = new ContentValues();
            Drawable drawable = item.getDrawable();
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_TYPE, TROUSERS);
            values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_DESCRIPTION, item.getWardrobeItemString());
            if (drawable != null)
            {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                values.put(WardrobeReaderContract.WardrobeEntry.COLUMN_NAME_IMAGE, stream.toByteArray());
            }
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

