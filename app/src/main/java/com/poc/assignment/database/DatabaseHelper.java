
package com.poc.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public String TAG = "ALBUM DB";
    public static final String DATABASE_NAME = "album.db";
    public static int DATABASE_VERSION = 6;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(DBManager.CREATE_TABLE);
        Log.v(TAG, "Database tabled created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        if (oldVersion < 5) {
            Log.v(TAG, "Table altered");
            db.execSQL("ALTER TABLE DB_DownloadMedia ADD COLUMN sourcepath VARCHAR DEFAULT \"\"");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "Downgrade database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        dropTables(db);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db) {
        Log.v(TAG, "Droping database tables");
        db.execSQL(DBManager.DROP_TABLE);
        Log.v(TAG, "Database tables droppped");
    }
}
