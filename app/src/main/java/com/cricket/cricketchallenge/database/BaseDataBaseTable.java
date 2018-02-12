package com.cricket.cricketchallenge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cricket.cricketchallenge.helper.AppConstants;


/**
 * This class is used to create data base. This is Base class which are managing
 * application database.
 */
public class BaseDataBaseTable
{
    /**
     * Instantiates a new Base data base table.
     *
     * @param c the context
     */
/*
     * Class constructor
     */
    protected BaseDataBaseTable(Context c)
    {
        DatabaseManager.initializeInstance(new DataBaseHelper(c, AppConstants.DB_NAME, null, AppConstants.DB_VERSION));
    }

    /**
     * This method is used to insert value in given data base
     *
     * @param tableName the table name
     * @param values    the Content Values object
     * @param db        the instance of SQLiteDatabase
     * @return the long id of record
     */
    protected long insertValue(String tableName, ContentValues values, SQLiteDatabase db)
    {
        if (tableName != null && values != null && db != null)
        {
            return db.insert(tableName, null, values);
        } else
        {
            return -1;
        }
    }
}
