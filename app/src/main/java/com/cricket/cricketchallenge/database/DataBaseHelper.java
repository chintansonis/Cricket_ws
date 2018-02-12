package com.cricket.cricketchallenge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class is Helper class which is extend SqLite database.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    /**
     * Instantiates a new Data base helper.
     *
     * @param context the context
     * @param name    the name of database
     * @param factory the factory
     * @param version the version of database
     */
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TableContacts.CREATE_TABLE);
        db.execSQL(TableContacts.CREATE_TRIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(TableContacts.DROP_TABLE);
        db.execSQL(TableContacts.DROP_TABLE_TRIP);
        onCreate(db);
    }
}
