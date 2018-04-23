package com.example.android.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

/** Created by Anderson on 25/03/2018 */

public class InventoryProvider extends ContentProvider {

    /** Tag for the log messages */
    private static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the products table */
    private static final int PRODUCT = 100;

    /** URI matcher code for the content URI for a single pet in the products table */
    private static final int PRODUCT_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.inventory/product" will map to the
        // integer code {@link #PRODUCT}. This URI is used to provide access to MULTIPLE rows
        // of the products table.
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCT, PRODUCT);

        // The content URI of the form "content://com.example.android.inventory/product/#" will map to the
        // integer code {@link #PRODUCT_ID}. This URI is used to provide access to ONE single row
        // of the product table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.inventory/product/3" matches, but
        // "content://com.example.android.inventory/product" (without a number at the end) doesn't match.
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCT + "/#", PRODUCT_ID);
    }

    /** Database helper object */
    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                // For the PRODUCT code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the products table.
                cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PRODUCT_ID:
                // For the PRODUCT_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.inventory/product/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the product table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertProduct(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product name required");
        }

        // Check that the provider is not null
        String provider = values.getAsString(InventoryEntry.COLUMN_PROVIDER);
        if (provider == null) {
            throw new IllegalArgumentException("Provider name required");
        }

        // Check that the provider mail is not null
        String providerMail = values.getAsString(InventoryEntry.COLUMN_PROVIDER_MAIL);
        if (providerMail == null) {
            throw new IllegalArgumentException("Provider e-mail required");
        }

        // Check that the product price is positive and is not null
        Float productPrice = values.getAsFloat(InventoryEntry.COLUMN_PRICE);
        if (productPrice == null && productPrice >= 0.0) {
            throw new IllegalArgumentException("Price is required");
        }

        // Check that the product quantity is positive and is not null
        Integer productQuantity = values.getAsInteger(InventoryEntry.COLUMN_QUANTITY);
        if (productQuantity == null && productQuantity >= 0) {
            throw new IllegalArgumentException("Quantity is required");
        }

        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new product with the given values
        long id = database.insert(InventoryEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the product content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                // For the PRODUCT_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update products in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more products).
     * Return the number of rows that were successfully updated.
     */
    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link InventoryEntry#COLUMN_PRODUCT_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_NAME)) {
            String productName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
            if (productName == null) {
                throw new IllegalArgumentException("Product name required");
            }
        }

        // If the {@link InventoryEntry#COLUMN_PROVIDER} key is present,
        // check that the provider value is valid.
        if (values.containsKey(InventoryEntry.COLUMN_PROVIDER)) {
            String provider = values.getAsString(InventoryEntry.COLUMN_PROVIDER);
            if (provider == null) {
                throw new IllegalArgumentException("Provider required");
            }
        }

        // If the {@link InventoryEntry#COLUMN_PROVIDER_MAIL} key is present,
        // check that the provider mail value is valid.
        if (values.containsKey(InventoryEntry.COLUMN_PROVIDER_MAIL)) {
            String providerMail = values.getAsString(InventoryEntry.COLUMN_PROVIDER_MAIL);
            if (providerMail == null) {
                throw new IllegalArgumentException("Provider mail required");
            }
        }

        // If the {@link InventoryEntry#COLUMN_PRICE} key is present,
        // check that the price value is valid.
        if (values.containsKey(InventoryEntry.COLUMN_PRICE)) {
            // Check that the price is greater than or equal to 0
            Integer productPrice = values.getAsInteger(InventoryEntry.COLUMN_PRICE);
            if (productPrice != null && productPrice < 0) {
                throw new IllegalArgumentException("Price need be 0 or greater");
            }
        }

        // If the {@link InventoryEntry#COLUMN_QUANTITY} key is present,
        // check that the quantity value is valid.
        if (values.containsKey(InventoryEntry.COLUMN_QUANTITY)) {
            // Check that the quantity is greater than or equal to 0
            Integer productQuantity = values.getAsInteger(InventoryEntry.COLUMN_QUANTITY);
            if (productQuantity != null && productQuantity < 0) {
                throw new IllegalArgumentException("Quantity need be 0 or greater");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                // Delete a single row given by the ID in the URI
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
