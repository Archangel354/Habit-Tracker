package com.example.owner.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.owner.habittracker.data.HabitContract.HabitEntry;

import static com.example.owner.habittracker.data.HabitContract.HabitEntry.TABLE_NAME;


/**
 * Created by e244194 on 7/28/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "habit.db";

    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE " + HabitEntry.TABLE_NAME + "("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT, "
                + HabitEntry.COLUMN_COMMENT + " TEXT, "
                + HabitEntry.COLUMN_STARTTIME + " TEXT, "
                + HabitEntry.COLUMN_ENDTIME + " TEXT, "
                + HabitEntry.COLUMN_DURATION + " INTEGER);";

        Log.i("LOG2", "after string to define table");

        Log.i(LOG_TAG,SQL_CREATE_HABITS_TABLE);

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
