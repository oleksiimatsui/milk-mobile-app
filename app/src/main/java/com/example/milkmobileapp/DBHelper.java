package com.example.milkmobileapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "MILK.DB";
    static final int DATABASE_VERSION = 2;

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            FeedReaderContract.FeedEntry.TABLE_NAME + " ( " +
            FeedReaderContract.FeedEntry.MILK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FeedReaderContract.FeedEntry.MILK_NAME + " TEXT, " +
            FeedReaderContract.FeedEntry.MILK_YEAR + " NUMERIC, " +
            FeedReaderContract.FeedEntry.MILK_COST + " REAL, " +
            FeedReaderContract.FeedEntry.MILK_PRODUCTION + " REAL " +
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}