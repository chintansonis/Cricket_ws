package com.cricket.cricketchallenge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cricket.cricketchallenge.commonmodel.DbContactsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to create table for Contacts and its have all basic method like add/update/delete and fetch record from cleaning table.
 */
public class TableContacts extends BaseDataBaseTable {

    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "tb_contacts";
    public static final String TABLE_TRIP = "tb_trip";
    /**
     * Query for delete this table.
     */
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String DROP_TABLE_TRIP = "DROP TABLE IF EXISTS " + TABLE_TRIP;
    /**
     * The constant DELETE_TABLE.
     */
    public static final String DELETE_TABLE = "DELETE FROM " + TABLE_NAME;

    // private variables.
    private static final String KEY_CONTACT_ID = "contact_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_STATUS = "status";
    private static final String LOID = "loid";
    private static final String MYCURRENTLONG = "mycurrentlong";
    private static final String MYCURRENTLAT = "mycurrentlat";
    private static final String TEXT = " TEXT,";
    /**
     * Query for create this table.
     */
    public static final String CREATE_TABLE = "Create Table " + TABLE_NAME + "("
            + KEY_CONTACT_ID + " INTEGER PRIMARY KEY,"
            + KEY_USER_NAME + TEXT
            + KEY_EMAIL + TEXT
            + KEY_PHONE + TEXT
            + KEY_STATUS + " INTEGER )";
    public static final String CREATE_TRIP_TABLE = "Create Table " + TABLE_TRIP + "("
            + LOID + TEXT
            + MYCURRENTLAT + TEXT
            + MYCURRENTLONG + " TEXT )";
    private static final String SELECT_FROM = "select * from ";
    private static final String WHERE = " where ";


    /**
     * Instantiates a new Table contacts.
     *
     * @param c the Context
     */
    public TableContacts(Context c) {
        super(c);
    }

    /**
     * Add.
     *
     * @param contactInfo the contact info
     */
    public void add(DbContactsModel contactInfo) {
        if (contactInfo != null) {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(KEY_CONTACT_ID, contactInfo.getContactId());
            values.put(KEY_USER_NAME, contactInfo.getUserName());
            values.put(KEY_EMAIL, contactInfo.getEmails());
            values.put(KEY_PHONE, contactInfo.getPhone());
            values.put(KEY_STATUS, contactInfo.getStatus());
            if (!isRecordExist(contactInfo.getContactId(), db)) {
                insertValue(TABLE_NAME, values, db);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public void addTripCurrrentLatLong(String Loid, String mycurrentlat, String mycurrentlong) {
        if (Loid != null) {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(LOID, Loid);
            values.put(MYCURRENTLAT, mycurrentlat);
            values.put(MYCURRENTLONG, mycurrentlong);
            insertValue(TABLE_TRIP, values, db);
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.d("System out", "values database " + values);
            DatabaseManager.getInstance().closeDatabase();
        }
    }
    /**
     * This method update all record in table to with given cleaning value.
     *
     * @param contactsModel the contacts model
     */
    public void updateRecord(DbContactsModel contactsModel) {
        if (contactsModel != null) {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_CONTACT_ID, contactsModel.getContactId());
            values.put(KEY_USER_NAME, contactsModel.getUserName());
            values.put(KEY_EMAIL, contactsModel.getEmails());
            values.put(KEY_PHONE, contactsModel.getPhone());
            values.put(KEY_STATUS, contactsModel.getStatus());
            db.update(TABLE_NAME, values, KEY_CONTACT_ID + " = ?", new String[]{String.valueOf(contactsModel.getContactId())});
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    /**
     * Delete table.
     */
    public void deleteTable() {
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            db.execSQL(DELETE_TABLE);
            DatabaseManager.getInstance().closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method check given value is exist in table or not. In this method check by given by id and db .
     *
     * @param id record id.
     * @param db SQLiteDatabase instance to make actions
     */
    private boolean isRecordExist(int id, SQLiteDatabase db) {
        boolean isExist = false;
        String sql = SELECT_FROM + TABLE_NAME;
        if (id != 0) {
            sql = sql + WHERE + KEY_CONTACT_ID + "= '" + id + "'";
        }
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            isExist = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isExist;

    }

    /**
     * This method get all data from data base to with given query.
     *
     * @return the all data from query
     */
    public List<DbContactsModel> getAllDataFromQuery() {
        String sql = SELECT_FROM + TABLE_NAME + WHERE + KEY_STATUS + " = 0 LIMIT 500";
        return getDataFromQuery(sql);
    }

    /**
     * This method get all data from data base to with given query.
     *
     * @return the all contacts
     */
    public List<DbContactsModel> getAllContacts() {
        String sql = SELECT_FROM + TABLE_NAME;
        return getDataFromQuery(sql);
    }

    /**
     * This method get all data from data base to with given query.
     */
    private List<DbContactsModel> getDataFromQuery(String query) {
        SQLiteDatabase db = DatabaseManager.getInstance().openReadableDatabase();
        List<DbContactsModel> contactList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contactList = loadContactData(cursor);
        }
        DatabaseManager.getInstance().closeDatabase();
        return contactList;
    }

    /**
     * This method will load all data in to array list from given
     */
    private List<DbContactsModel> loadContactData(Cursor cursor) {
        List<DbContactsModel> contactList = new ArrayList<>();
        do {
            int contactId = cursor.getInt(cursor.getColumnIndex(KEY_CONTACT_ID));
            Log.d("System out", "contact_id from loadcontactdata====   "+contactId);
            String userName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
            String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));

            DbContactsModel contactsModel = new DbContactsModel();
            contactsModel.setContactId(contactId);
            contactsModel.setUserName(userName);
            contactsModel.setEmails(email);
            contactsModel.setPhone(phone);
            contactsModel.setStatus(status);
            contactList.add(contactsModel);
        } while (cursor.moveToNext());
        cursor.close();
        return contactList;
    }
}
