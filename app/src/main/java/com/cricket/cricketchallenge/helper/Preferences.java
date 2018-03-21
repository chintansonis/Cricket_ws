package com.cricket.cricketchallenge.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chintans on 11-07-2017.
 */

public class Preferences {

    // SharedPreferences file name
    private static final String PREF_NAME = "com.cricket.cricketchallenge";
    // public static final variables

    // Shared pref mode
    private static final int PRIVATE_MODE = 1;

    // private static variables
    private static Preferences instance;

    /**
     * logging user email
     */
    public static String KEY_USER_LOGIN_MOBILE = "KEY_USER_LOGIN_MOBILE";
    /**
     * the Constant user model
     */
    public static String KEY_USER_MODEL = "KEY_USER_MODEL";

    /**
     * the Constant user model
     */
    public static String KEY_USER_PERSONAL_INFORMATON = "KEY_USER_PERSONAL_INFORMATON";

    private Context context;
    // Shared Preferences
    private SharedPreferences sharedPref;

    /**
     * The constant KEY_USER_LOGIN_PASS_WORD.
     */
    public static final String KEY_USER_LOGIN_PASS_WORD = "KEY_USER_LOGIN_PASS_WORD";

    /**
     * The constant KEY_DASHBOARD_MODEL.
     */
    public static final String KEY_DASHBOARD_DATA = "KEY_DASHBOARD_DATA";

    /**
     * The constant KEY_USER_ID.
     */
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_USER_TOTAL_POINTS = "KEY_USER_TOTAL_POINTS";
    public static final String KEY_LATITUDE = "KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "KEY_LONGITUDE";


    public static final String KEY_LATITUDE_DASHBOARD = "KEY_LATITUDE_DASHBOARD";
    public static final String KEY_LONGITUDE_DASHBOARD = "KEY_LONGITUDE_DASHBOARD";
    /**
     * The constant KEY_IS_AUTO_LOGIN.
     */
    public static final String KEY_IS_AUTO_LOGIN = "KEY_IS_AUTO_LOGIN";
    public static final String KEY_IS_PASSWORD_SET = "KEY_IS_PASSWORD_SET";

    public static final String KEY_IS_DASHBOARD_DATA_LOADED = "KEY_IS_DASHBOARD_DATA_LOADED";

    /**
     * Instantiates a new Preferences.
     *
     * @param context the context
     */
    public Preferences(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    /**
     * Gets instance.
     *
     * @param c the c
     * @return the instance
     */
    public static Preferences getInstance(Context c) {
        if (instance == null) {
            instance = new Preferences(c);
        }
        return instance;
    }

    /**
     * Gets shared pref.
     *
     * @return the shared pref
     */
    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        return sharedPref.getString(key, AppConstants.DEFAULT_STRING);
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        String preferenceValue = value;
        sharedPrefEditor.putString(key, preferenceValue);
        sharedPrefEditor.commit();
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putBoolean(key, value);
        sharedPrefEditor.commit();
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public long getLong(String key) {
        return sharedPref.getLong(key, 0);
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void setLong(String key, long value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putLong(key, value);
        sharedPrefEditor.commit();
    }


    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public String[] getAllIds(String key) {
        String ids = sharedPref.getString(key, "");
        return !ids.isEmpty() ? ids.substring(0, ids.length() - 1).split(",") : new String[0];
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void addIds(String key, long value) {
        String ids = sharedPref.getString(key, "");
        if (!ids.contains(Long.toString(value))) {
            SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
            sharedPrefEditor.putString(key, ids + (value + ","));
            sharedPrefEditor.commit();
        }
    }


    /**
     * Clear all.
     */
    public void clearAll() {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.clear().commit();
    }

}