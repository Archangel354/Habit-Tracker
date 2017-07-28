package com.example.owner.habittracker;

import android.provider.BaseColumns;

import java.sql.Time;

/**
 * Created by e244194 on 7/28/2017.
 */

public class HabitContract {

    // Create empty HabitContract constructor
    private HabitContract(){}

    // Inner class to define values for habit table.
    // Each entry represents a logged action by the user
    public static final class HabitActivity implements BaseColumns {

        // Name of the database table for Habit Tracker
        public final static String TABLE_NAME = "habits";

        // Unique ID for the logged activity
        public final String _ID = BaseColumns._ID;

        // Name of the activity performed by the user
        public final static String COLUMN_HABIT_NAME ="name";

        // Comment about the activity performed by the user
        public final static String COLUMN_COMMENT ="comment";

        // Start time of the activity performed by the user
        public final static Time COLUMN_STARTTIME = Time.valueOf("startime");

        // End time of the activity performed by the user
        public final static Time COLUMN_ENDTIME = Time.valueOf("endtime");

        // Duration of the activity performed by the user
        public final static Time COLUMN_DURATION = Time.valueOf("duration");

    }
}
