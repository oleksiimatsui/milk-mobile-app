package com.example.milkmobileapp;

import android.provider.BaseColumns;
public final class FeedReaderContract {
    public static abstract class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "MILK";
        static final String MILK_ID = "_ID";
        static final String MILK_NAME = "milk_name";
        static final String MILK_YEAR = "milk_year";
        static final String MILK_COST = "milk_cost";
        static final String MILK_PRODUCTION = "milk_production";
    }
}
