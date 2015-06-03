package com.sequoiabridge.captain.toeflavatar.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;

/**
 * Database helper
 */
public class RecordingDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recording.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataContract.RecordingEntry.TABLE_NAME + " (" +
                    DataContract.RecordingEntry._ID + " INTEGER PRIMARY KEY," +
                    DataContract.RecordingEntry.COLUMN_NAME_ENTRY_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    DataContract.RecordingEntry.COLUMN_NAME_ENTRY_FILENAME + TEXT_TYPE +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataContract.RecordingEntry.TABLE_NAME;

    public RecordingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(String filename, String timestamp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataContract.RecordingEntry.COLUMN_NAME_ENTRY_FILENAME, filename);
        values.put(DataContract.RecordingEntry.COLUMN_NAME_ENTRY_TIMESTAMP, timestamp);
        db.insert(DataContract.RecordingEntry.TABLE_NAME, null, values);
    }

    public Cursor queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DataContract.RecordingEntry._ID,
                DataContract.RecordingEntry.COLUMN_NAME_ENTRY_FILENAME,
                DataContract.RecordingEntry.COLUMN_NAME_ENTRY_TIMESTAMP
        };

        Cursor cursor = db.query(
                DataContract.RecordingEntry.TABLE_NAME,  // The table to query
                projection,                            // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if (cursor != null) cursor.moveToFirst();

        return cursor;
    }
}
