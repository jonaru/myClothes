package com.example.jonatan.clothesplanner.wardrobe.wardrobedb;

import android.provider.BaseColumns;

/**
 * Created by Jonatan on 2017-04-23.
 */

public final class WardrobeReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WardrobeReaderContract(){}

    /* Inner class that defines the table contents */
    public static class WardrobeEntry implements BaseColumns {
        public static final String TABLE_NAME = "wardrobe_entry";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}
