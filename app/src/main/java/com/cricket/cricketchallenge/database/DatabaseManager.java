package com.cricket.cricketchallenge.database;


import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Database manager class.
 */
public class DatabaseManager
{
    // private variables...
    private static DatabaseManager instance;
    private static DataBaseHelper mDatabaseHelper;
    private final AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    /**
     * Initialize instance.
     *
     * @param helper the DataBaseHelper object
     */
    public static synchronized void initializeInstance(DataBaseHelper helper)
    {
        if (instance == null)
        {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized DatabaseManager getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }


    /**
     * Open database sq lite database.
     *
     * @return the sq lite database
     */
    public synchronized SQLiteDatabase openDatabase()
    {
        if (mOpenCounter.incrementAndGet() == 1)
        {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * Open readable database sq lite database.
     *
     * @return the sq lite database
     */
    public synchronized SQLiteDatabase openReadableDatabase()
    {
        if (mOpenCounter.incrementAndGet() == 1)
        {
            // Opening new database
            mDatabase = mDatabaseHelper.getReadableDatabase();
        }
        return mDatabase;
    }

    /**
     * Close database.
     */
    public synchronized void closeDatabase()
    {
        if (mOpenCounter.decrementAndGet() == 0)
        {
            mDatabase.close();

        }
    }
}