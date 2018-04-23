package com.example.android.inventory.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;
/**
 * Created by Anderson on 25/03/2018.
 *
 * Database helper for Inventory App. Manages database creation and version management.
 */

class InventoryDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link InventoryDbHelper}.
     *
     * @param context of the app
     */
    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the products table
        String SQL_CREATE_PRODUCTS_TABLE =  "CREATE TABLE "
                + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PROVIDER + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PROVIDER_MAIL + " INTEGER NOT NULL, "
                + InventoryEntry.COLUMN_PRICE + " REAL NOT NULL, "
                + InventoryEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0, "
                + InventoryEntry.COLUMN_IMAGE_URI + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
