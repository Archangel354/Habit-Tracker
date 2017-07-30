package com.example.owner.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.habittracker.data.HabitContract;
import com.example.owner.habittracker.data.HabitDbHelper;
import com.example.owner.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private HabitDbHelper mDbHelper;

    // Hard-coded values for the table "habit"
    String nameString = "Run daily marathon";
    String commentString = "Ho Hum.. another marathon..";
    String startTimeString = " 6:00 ";
    String endTimeString = " 10:00 ";
    int durationString = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

        Cursor mCursor = readData();
        displayDatabaseInfo(mCursor);
        insertHabit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor mCursor = readData();
        displayDatabaseInfo(mCursor);
    }



    // Read the database and RETURN a cursor per the Project Rubric and reviewer's notes.
    private Cursor readData(){
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE IF NOT EXISTS " + HabitEntry.TABLE_NAME + "("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT, "
                + HabitEntry.COLUMN_COMMENT + " TEXT, "
                + HabitEntry.COLUMN_STARTTIME + " TEXT, "
                + HabitEntry.COLUMN_ENDTIME + " TEXT, "
                + HabitEntry.COLUMN_DURATION + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_COMMENT,
                HabitEntry.COLUMN_STARTTIME,
                HabitEntry.COLUMN_ENDTIME,
                HabitEntry.COLUMN_DURATION};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }


    /**
     * Revised helper method to display information in the onscreen TextView about the state of
     * the habit tracker database.
     */
    private void displayDatabaseInfo(Cursor cursor) {


        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The habits table contains <number of rows in Cursor> pets.
            // _id - name - comment - start time - end time - duration
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_COMMENT + " - " +
                    HabitEntry.COLUMN_STARTTIME + " - " +
                    HabitEntry.COLUMN_ENDTIME + " - " +
                    HabitEntry.COLUMN_DURATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int commentColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_COMMENT);
            int startTimeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_STARTTIME);
            int  endTimeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_ENDTIME);
            int  durationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DURATION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentComment = cursor.getString(commentColumnIndex);
                String currentStartTime = cursor.getString(startTimeColumnIndex);
                String currentEndTime = cursor.getString(endTimeColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentComment + " - " +
                        currentStartTime + " - " +
                        currentEndTime + " - " +
                        currentDuration));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertHabit(){

        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Get the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys, and habit strings are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(HabitEntry.COLUMN_COMMENT, commentString);
        values.put(HabitEntry.COLUMN_STARTTIME, startTimeString);
        values.put(HabitEntry.COLUMN_ENDTIME, endTimeString);
        values.put(HabitEntry.COLUMN_DURATION, durationString);

        // Insert a new row for habit in the database.  Return the ID of that row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
