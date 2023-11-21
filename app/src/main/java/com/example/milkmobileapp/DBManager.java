package com.example.milkmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBManager {

    static DBHelper DB;
    public DBManager(DBHelper _DB){
        DB = _DB;
    }
    public void  refreshTable(){
        SQLiteDatabase db = DB.getWritableDatabase();

        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.MILK_NAME, "111" );
        values.put(FeedReaderContract.FeedEntry.MILK_COST, 11.2);
        values.put(FeedReaderContract.FeedEntry.MILK_PRODUCTION, 1231);
        values.put(FeedReaderContract.FeedEntry.MILK_YEAR, "11.01.2002");
        long newRowId;
        newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        this.getTable();
    }

    public ArrayList<Milk> getTable(){
        SQLiteDatabase db = DB.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.MILK_NAME,
                FeedReaderContract.FeedEntry.MILK_PRODUCTION,
                FeedReaderContract.FeedEntry.MILK_COST,
                FeedReaderContract.FeedEntry.MILK_YEAR,
            };
        String sortOrder = FeedReaderContract.FeedEntry._ID + " DESC";

        ArrayList<Milk> list = new ArrayList<Milk>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME;

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.MILK_ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.MILK_NAME));
                        int year = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.MILK_YEAR));
                        float cost = cursor.getFloat(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.MILK_COST));
                        float production = cursor.getFloat(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.MILK_PRODUCTION));
                        Milk obj = new Milk(id, name,year,cost,production);
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        System.out.println(list);
        return list;

    }
}
