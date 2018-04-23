package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.habittracker.data.HabitDbHelper;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG;

    // Weekdays
    private static final String WEEKDAY_MONDAY = "Monday";
    private static final String WEEKDAY_TUESDAY = "Tuesday";
    private static final String WEEKDAY_WEDNESDAY = "Wednesday";
    private static final String WEEKDAY_THURSAY = "Thursday";

    // Habit status
    private static final int STATUS_UNDONE = 0;
    private static final int STATUS_DONE = 1;
    private static final int STATUS_PARTIALLY = 2;

    static {
        LOG_TAG = HabitDbHelper.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDbHelper habit = new HabitDbHelper(this);
        ContentValues values = new ContentValues();

        // Inserting fake data on database

        values.put(HabitEntry.COLUMN_DESCRIPTION, "Running in the park");
        values.put(HabitEntry.COLUMN_WEEKDAY, WEEKDAY_MONDAY);
        values.put(HabitEntry.COLUMN_STATUS, STATUS_DONE);

        habit.insertHabit(values);

        values.put(HabitEntry.COLUMN_DESCRIPTION, "Lear in the Udacity");
        values.put(HabitEntry.COLUMN_WEEKDAY, WEEKDAY_TUESDAY);
        values.put(HabitEntry.COLUMN_STATUS, STATUS_DONE);

        habit.insertHabit(values);

        values.put(HabitEntry.COLUMN_DESCRIPTION, "Shopping in the Village Mall");
        values.put(HabitEntry.COLUMN_WEEKDAY, WEEKDAY_WEDNESDAY);
        values.put(HabitEntry.COLUMN_STATUS, STATUS_PARTIALLY);

        habit.insertHabit(values);

        values.put(HabitEntry.COLUMN_DESCRIPTION, "Read the book Outlander");
        values.put(HabitEntry.COLUMN_WEEKDAY, WEEKDAY_THURSAY);
        values.put(HabitEntry.COLUMN_STATUS, STATUS_UNDONE);

        habit.insertHabit(values);

        // Geting data habit from database
        Cursor cursor = habit.getHabit();

        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int descriptionColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DESCRIPTION);
            int weekdayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_WEEKDAY);
            int statusColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_STATUS);

            Log.d(LOG_TAG,"The habit table contains " + cursor.getCount() + " habits.");

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                String currentWeekday = cursor.getString(weekdayColumnIndex);
                int currentStatus = cursor.getInt(statusColumnIndex);

                // Display the values from each column of the current row in the cursor in the Logcat
                Log.d(LOG_TAG,currentID + " - " +
                        currentDescription + " - " +
                        currentWeekday + " - " +
                        currentStatus + "\n");
            }
        } finally {
            // Close the cursor when you're done reading from it.
            cursor.close();
        }
    }
}
