package com.example.android.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by Anderson on 22/03/2018.
 *
 * Habits database helper.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG;

    static {
        LOG_TAG = HabitDbHelper.class.getSimpleName();
    }

    private static final String DATABASE_NAME = "habit.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the habit table
        String SQL_CREATE_HABIT_TABLE =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_WEEKDAY + " TEXT, "
                + HabitEntry.COLUMN_STATUS + " INTEGER NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Insert habit data
    public void insertHabit(ContentValues values) {

        long newRowId;

        newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.d(LOG_TAG, "New row ID" + newRowId );
    }

    // Read habit data
    public Cursor getHabit() {

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_DESCRIPTION,
                HabitEntry.COLUMN_WEEKDAY,
                HabitEntry.COLUMN_STATUS
        };

        return db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
}