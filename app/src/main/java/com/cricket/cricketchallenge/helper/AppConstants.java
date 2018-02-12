package com.cricket.cricketchallenge.helper;

import com.cricket.cricketchallenge.commonmodel.ContactModel;

import java.util.ArrayList;

/**
 * Created by chintans on 30-11-2017.
 */

public class AppConstants {
    public static final String INTENT_EXTRA_BUTTON_CLICK = "Intent_extra_button_click";
    public static final int loginClick = 0;
    public static final int CONTACT_PERMISSION_CODE=12345;
    public static final int getStartedClick = 1;
    public static String COLORS_PRIMARY[] = {"#90CAF9", "#FFCC80", "#FFAB91", "#9E9E9E"};
    public static String COLORS_PRIMARYDARK[] = {"#42A5F5", "#FFA726", "#FF7043", "#616161"};
    public static final String DB_NAME = "db_cricketchallenge";
    public static final int DB_VERSION = 1;
    public static final String INTENT_CONTACT_SYNC_DONE = "INTENT_CONTACT_SYNC_DONE";
    public static ArrayList<ContactModel> arrayContact = new ArrayList<>();
}
