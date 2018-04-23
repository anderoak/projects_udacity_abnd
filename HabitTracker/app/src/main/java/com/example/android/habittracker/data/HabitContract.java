package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Anderson on 22/03/2018.
 *
 * Habits database contract.
 */

public class HabitContract {

    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns {

        // Table related constants
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "habit";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_WEEKDAY = "weekday";
        public static final String COLUMN_STATUS = "status";
    }
}