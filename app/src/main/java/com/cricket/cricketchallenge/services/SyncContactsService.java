package com.cricket.cricketchallenge.services;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.ContactsContract;
import android.util.Log;

import com.cricket.cricketchallenge.commonmodel.ContactModel;
import com.cricket.cricketchallenge.commonmodel.DbContactListModel;
import com.cricket.cricketchallenge.commonmodel.DbContactsModel;
import com.cricket.cricketchallenge.database.TableContacts;
import com.cricket.cricketchallenge.helper.AppConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The type Sync contacts service.
 */
public class SyncContactsService extends Service {

    // private variables
    private List<ContactModel> contactsModelList;
    private ServiceHandler mServiceHandler;
    private Map<Integer, DbContactListModel> contactMap = new LinkedHashMap<>();
    private TableContacts tbContacts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int userid;
    private int totalcontacts;
    int cus_pos = 0;
    ContactModel contactModelContinue;
    private String image_uri = "";
    int pos = 0, k = 0, list2size = 0;
    int pos1 = 19, no_of_contacts;
    private ArrayList<ContactModel> apicontactlist;


/*    @Override
    public void onResponse(String response) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = 2;
        mServiceHandler.sendMessage(msg);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        finishService();
    }*/

    /**
     * this method is used to finish service
     */
    private void finishService() {
        Log.d("System out", "finish service called");
        Intent intent = new Intent(AppConstants.INTENT_CONTACT_SYNC_DONE);
        sendBroadcast(intent);
        editor.putString("CONTACT_SYNC_STATE", "false").commit();
        Log.d("System out", "stopSelf from contact");
        stopSelf();
    }

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("My_Pref", 0);
        editor = sharedPreferences.edit();
//        Log.d("System out", "service USERID " + sharedPreferences.getInt("UserID", 0));
        userid = sharedPreferences.getInt("UserID", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Log.d("System out", "in startBackgroundTask");
                startBackgroundTask();
            }
        } else {
            startBackgroundTask();
        }
        super.onCreate();
    }

    /**
     * This method is used to start contact syncing
     */
    private void startBackgroundTask() {
        editor.putString("CONTACT_SYNC_STATE", "true").commit();
        tbContacts = new TableContacts(this);
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        Log.d("System out", "mServiceHandler");
        AppConstants.arrayContact.clear();
        mServiceHandler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mServiceHandler != null) {
            if (mServiceHandler.obtainMessage() != null) {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = 1;
                mServiceHandler.sendMessage(msg);
            }
        }
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * this method is used to get contacts.
     *
     * @param cr           content resolver
     * @param contactId    contact id
     * @param contactModel contact model
     */
    private void getAllContacts(ContentResolver cr, String contactId, DbContactListModel contactModel) {
        Cursor phoneNumberCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
        if (phoneNumberCursor != null) {
            Set<String> phoneList = new HashSet<>();
            String name = "";
            String phoneNumber;
            while (phoneNumberCursor.moveToNext()) {
                try {
                    name = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNumber = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneList.add(phoneNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            contactModel.setContactId(Integer.parseInt(contactId));
            contactModel.setName(name);
            contactModel.setPhoneNumber(phoneList);
            try {
                contactMap.put(Integer.valueOf(contactId), contactModel);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //Log.d("System out", "contactMap from all contact " + contactMap.size());
            try {
                phoneNumberCursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this method is used to get email ids.
     *
     * @param cr           content resolver
     * @param contactId    contact id
     * @param contactModel contact model
     */
    private void getAllEmails(ContentResolver cr, String contactId, DbContactListModel contactModel) {
        Cursor emailCursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
        if (emailCursor != null) {
            Set<String> emailList = new HashSet<>();
            while (emailCursor.moveToNext()) {
                try {
                    emailList.add(emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            contactModel.setEmail(emailList);
            emailCursor.close();
        }
    }
    /**
     * this method is used to add contact in database
     */
    private void insertInDatabase(DbContactListModel contactMap) {
        Log.d("System out", "INSERT DATABASE NAME " + contactMap.getContactId());
        DbContactsModel contactsModel = new DbContactsModel();
        try {
            contactsModel.setContactId(contactMap.getContactId());
            contactsModel.setUserName(contactMap.getName());
            contactsModel.setEmails(getCommaSeparatedString(contactMap.getEmail()));
            contactsModel.setPhone(getCommaSeparatedString(contactMap.getPhoneNumber()));
            contactsModel.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbContacts.add(contactsModel);
    }

    /**
     * @param stringSet string set
     * @return comma separated string.
     */
    private String getCommaSeparatedString(Set<String> stringSet) {
        if (!stringSet.isEmpty()) {
            StringBuilder commaSepValueBuilder = new StringBuilder();
            for (String email : stringSet) {
                commaSepValueBuilder.append(email).append(",");
            }
            return commaSepValueBuilder.substring(0, commaSepValueBuilder.length() - 1);
        }
        return "";
    }

    /**
     * @param contactsModelList contact model list
     * @param isDeleted         is previous is delete
     * @return json of all contacts to send to server
     */
    /*private UploadContactContainer createContactJson(ArrayList<ContactModel> contactsModelList, boolean isDeleted) {
        UploadContactContainer contactContainer = new UploadContactContainer();
        contactContainer.setUserId(userid);
        for (ContactModel contactsModel : contactsModelList) {
            Log.e("System out", "contactsModel EMAILDID" + contactsModel.getContact_Email() + " " + contactsModel.getContact_Mobile() + " ID " + contactsModel.getContact_ID());
            UploadContact uploadContact = new UploadContact();
            *//*if (contactsModel.getContact_Email() != null && contactsModel.getContact_Email().length() > 0&&!contactsModel.getContact_Email().isEmpty()) {
                uploadContact.setEmail(contactsModel.getContact_Email().split(","));
            }*//*
            try {
                if (!TextUtils.isEmpty(contactsModel.getContact_Email())) {
                    uploadContact.setEmail(contactsModel.getContact_Email().split(","));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(contactsModel.getContact_Mobile())) {
                    uploadContact.setMobile(contactsModel.getContact_Mobile().split(","));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            *//*if (contactsModel.getContact_Mobile()!=null|| contactsModel.getContact_Mobile().length() > 0|| !contactsModel.getContact_Mobile().isEmpty()) {
                uploadContact.setMobile(contactsModel.getContact_Mobile().split(","));
            }*//*
            try {
                uploadContact.setContactId(contactsModel.getContact_ID());
                contactContainer.addContacts(uploadContact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            contactContainer.setDelete(isDeleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactContainer;
    }*/

    /*public void responseOfContact(String resultString) {
        Log.d("System out", "resultString for contact" + resultString);
        Log.d("System Out", "sync contact result " + resultString);
        if (resultString.length() > 2) {
            try {
                JSONObject jsonObject = new JSONObject(resultString);
                Log.d("System out", "jsonObject check" + jsonObject.toString());
                if (jsonObject.has("Status")) {
                    int Status = jsonObject.optInt("Status");
                    if (Status == 200) {
                        if (pos < AppConstants.arrayContact.size()) {
                            callContactSync(AppConstants.arrayContact, true);
                        }
                        Message msg = mServiceHandler.obtainMessage();
                        msg.arg1 = 2;
                        mServiceHandler.sendMessage(msg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * Service handler class which is used to handle service.
     */
    private final class ServiceHandler extends Handler {
        /**
         * Instantiates a new Service handler.
         *
         * @param looper the looper
         */
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("System out", "msg.arg1" + msg.arg1);
            switch (msg.arg1) {
                case 1:
                    //getNameEmailDetails();
                    readConatcts();
                    /*invokeContactSyncService(true);*/
                    break;
                case 2:
                    //onContinueSyncContacts();
                    break;
                default:
                    break;
            }
        }

        private void onContinueSyncContacts() {
            /*Log.d("System out", "onContinueSyncContacts() Called");
            for (ContactModel contactModel : AppConstants.arrayContact) {
                AppConstants.arrayContact.add(contactModel);
            }
            Log.e("System out", "on continue sync size check" + AppConstants.arrayContact);*/
            AppConstants.arrayContact.clear();
            invokeContactSyncService(false);
        }

        /**
         * @param isDeleted is delete from contacts.
         */
        private void invokeContactSyncService(boolean isDeleted) {
            Log.d("System out", "invokeContactSyncService");
/*            contactsModelList = tbContacts.getAllDataFromQuery();*/
            /*Log.d("System out", "contactsModelList size" + contactsModelList.size());*/
//            Log.d("System out", "AppConstants.arrayContact in INVOKESYNC " + AppConstants.arrayContact.get(0).getContact_Mobile() + " " + AppConstants.arrayContact.get(0).getContact_Email() + " " + AppConstants.arrayContact.get(0).getContact_ID());
            Log.d("System out", "isDeleted AppConstants.arrayContact" + isDeleted + "" + AppConstants.arrayContact.size());
            //callContactSync(AppConstants.arrayContact, isDeleted);
            /*if (AppConstants.apiCall == false) {*/
            /*if (userid != 0 && (isDeleted || !AppConstants.arrayContact.isEmpty())) {
                String contactJson = "";
                try {
                    contactJson = createContactJson(AppConstants.arrayContact, isDeleted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("System out", "contactJson " + contactJson);*/

            /*} else {
                //AppConstants.snackbar(llSettings, "No Internet..", SettingActivity.this);
                finishService();
            }*/
            /*} else if (AppConstants.apiCall == true) {
                Log.d("System out", "in if conatct when another api call happen");
            }*/

                    /*String contactJson = createContactJson(contactsModelList, isDeleted);
                    Log.d("System out", "contactJson " + contactJson);
                    Log.d("System out", "API CALL CONTACT");
                    Call<ContactPojo> call = RestClient.getApiService().syncContactApi(contactJson);
                    call.enqueue(new retrofit.Callback<ContactPojo>() {
                        @Override
                        public void onResponse(retrofit.Response<ContactPojo> response, Retrofit retrofit) {
                            Log.d("System out", "in response  from contact sync " + response.body());
                            if (response.body() != null) {
                                Log.d("System out", "response STATUS from CONTACTSYNC" + response.body().getStatus());
                                if (response.body().getStatus() == 200) {
                                    Message msg = mServiceHandler.obtainMessage();
                                    msg.arg1 = 2;
                                    mServiceHandler.sendMessage(msg);
                                } else {
                                    Log.d("System out", "ELSE response STATUS from CONTACTSYNC");
                                }

                            }
                        }


                        @Override
                        public void onFailure(Throwable t) {
                            finishService();

                        }

                    });*/
        }

    }

    /*private void callContactSync(ArrayList<ContactModel> arraylist, boolean isDeleted) {
        apicontactlist = new ArrayList<ContactModel>();
        *//*int j = 0;
        if (k < 20) {
            for (int i = pos; i < pos + k; i++) {
                apicontactlist.add(j, arraylist.get(i));
                j++;
                list2size++;
            }
        } else {
            for (int i = pos; i <= pos + 19; i++) {
                apicontactlist.add(j, arraylist.get(i));
                j++;
                list2size++;

            }
        }
        pos = pos + j;
        k = arraylist.size() - list2size;*//*
        apicontactlist.addAll(arraylist);
        Log.d("System out", "apicontactlist size " + apicontactlist.size());
        if (userid != 0 && (isDeleted || !apicontactlist.isEmpty())) {
            UploadContactContainer contactJson = new UploadContactContainer();
            try {
                contactJson = createContactJson(apicontactlist, isDeleted);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("System out", "contactJson from method" + contactJson);
            *//*String url = AppConstants.SERVER_URL + "ContactSyncup";
            new PostSync(SyncContactsService.this, "ContactSyncup").execute(url, contactJson);
            Log.d("System out", "url for contact " + url);*//*
            Call<ResponsePojo> call = RestClient.getApiService().syncContactApi(contactJson);
            call.enqueue(new retrofit.Callback<ResponsePojo>() {
                @Override
                public void onResponse(retrofit.Response<ResponsePojo> response, Retrofit retrofit) {
                    Log.d("System out", "in response  from contact sync " + response.body());
                    if (response.body() != null) {
                        Log.d("System out", "response STATUS from CONTACTSYNC" + response.body().getStatus());
                        if (response.body().getStatus() == 200) {
                            Message msg = mServiceHandler.obtainMessage();
                            msg.arg1 = 2;
                            mServiceHandler.sendMessage(msg);
                        } else {
                            Log.d("System out", "ELSE response STATUS from CONTACTSYNC");
                        }
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    finishService();
                }
            });
        } else {
            //AppConstants.snackbar(llSettings, "No Internet..", SettingActivity.this);
            finishService();
        }
    }*/

    public void readConatcts() {
        ContactModel getsynccontacts;
        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = getContentResolver();
        final Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts.HAS_PHONE_NUMBER, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
        String phone = "";
        String emailContact = "";
        String emailType = "";
        Bitmap bitmap;
        //Log.d("System out", "contsct size " + cur.getCount());
        tbContacts.deleteTable();
        try {
            final Cursor contactDetailCursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts.HAS_PHONE_NUMBER, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
            if (contactDetailCursor != null && contactDetailCursor.getCount() > 0) {
                String contactId;
                DbContactListModel contactModel;
                while (contactDetailCursor.moveToNext()) {
                    try {
                        contactId = contactDetailCursor.getString(contactDetailCursor.getColumnIndex(ContactsContract.Contacts._ID));
                        contactModel = new DbContactListModel();
                        getAllEmails(cr, contactId, contactModel);
                        getAllContacts(cr, contactId, contactModel);
                        insertInDatabase(contactModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                contactDetailCursor.close();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                getsynccontacts = new ContactModel();
                totalcontacts = cur.getCount();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {*/
                Log.e("System out", "Counter " + cus_pos + "/" + cur.getCount());
                // txtnoofContacts.setText(cur.getCount() + "");
                // txtcontactsync.setText(cus_pos + "");
                if (cus_pos <= cur.getCount()) {
                    long dataUploaded = (cus_pos * 100) / cur.getCount();
                }
                /*}
                });*/
//                Message msg = new Message();
//                msg.arg1=no_of_contacts;
//                msg.arg2=cus_pos;
//                myHandler.sendMessage(msg);
                no_of_contacts = cur.getCount();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.e("System out", "Name " + name);
                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                if (name != null) {
                    getsynccontacts.setContact_First_Name(name);
                }
                getsynccontacts.setContact_Last_Name("");
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);
                    sb.append("\n Contact Name:" + name);
                    final Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phone = phone.replace("?", "");
                            phone = phone.replace("-", "");
                            phone = phone.replace(" ", "");
                            if (phone != null) {
                                getsynccontacts.setContact_Mobile(phone);
                                getsynccontacts.setContact_ID(id);
                            } else {
                                getsynccontacts.setContact_Mobile("");
                            }
                            sb.append("\n Phone number:" + phone);
                            System.out.println("phone" + phone);
                            cus_pos++;
                        }
                    }
                    if (pCur != null) {
                        pCur.close();
                    }
                }
                Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                if (emailCur != null) {
                    while (emailCur.moveToNext()) {
                        try {
                            emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                            emailContact = emailContact.replace("null", "").trim();
                            getsynccontacts.setContact_Email(emailContact);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
                        System.out.println("Email " + emailContact + " Email Type : " + emailType);
                    }
                }
                if (emailCur != null) {
                    try {
                        emailCur.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*while (birthdatcur.moveToNext()) {
                    birthday = birthdatcur.getString(birthdatcur.getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));
                    getsynccontacts.dob = birthday;
                    // birthday1 = birthdatcur.getString(birthdatcur.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));
                    sb.append("\nEmail:" + birthday + "Email type:" + emailType);
                    System.out.println("Email " + emailContact + " Email Type : " + emailType);
                }
                birthdatcur.close();*/
                String where = ContactsContract.Data.CONTACT_ID
                        + " = "
                        + id + " AND "
                        + ContactsContract.Data.MIMETYPE + "= '"
                        + ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
                        + "'";
                Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                        null, where, null, null);
                if (addrCur != null) {
                    String street = "", poBox = "", city = "";
                    while (addrCur.moveToNext()) {
                        poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                        if (street != null) {
                            try {
                                getsynccontacts.setContact_Address2(street);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (poBox != null) {
                            try {
                                getsynccontacts.setContact_Address1(poBox);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("System out", " city name = " + city);
                        //  if(city.equalsIgnoreCase("null"))
                        try {
                            getsynccontacts.setContact_City(city);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // else
                        try {
                            getsynccontacts.setContact_City(" ");
                        } finally {

                        }

                        //if(state.equalsIgnoreCase("null"))
                        try {
                            getsynccontacts.setContact_State(state);
                        } finally {

                        }
                        //  else
                        try {
                            getsynccontacts.setContact_State(" ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //if(country != null)
                        try {
                            getsynccontacts.setContact_Country(country);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //   else
                        try {
                            getsynccontacts.setContact_Country(" ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //if(postalCode != null)
                        try {
                            getsynccontacts.setContact_ZipCode(postalCode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //  else
                        try {
                            getsynccontacts.setContact_ZipCode(" ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        for (int i = 0; i < city_list.size(); i++) {
//                            if (city_list.get(i).city_name.equalsIgnoreCase(city)) {
//                                city_id = city_list.get(i).city_id;
//                                Toast.makeText(SyncActivity.this,city_id+"",Toast.LENGTH_LONG).show();
//
//                            }
//                        }
                    }
                }
                AppConstants.arrayContact.add(getsynccontacts);
                Log.d("System out","AppConstants.arrayContact"+AppConstants.arrayContact.size());
                /*if (no_of_contacts == AppConstants.arrayContact.size()) {
                    k = AppConstants.arrayContact.size();
                }*/
                //              vectorarray.add(getsynccontacts);
                if (addrCur != null)
                    try {
                        addrCur.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }


        }

        //progressdialoge.dismiss();
        Log.d("System out", "Contact list SIZE " + AppConstants.arrayContact.size());

            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt.setText("Getting Contacts");

                    final Vector<Contact> subsidiesList = getIndexedContacts(vectorarray);
                    totalListSize = subsidiesList.size();
                //    Log.d("System out","size "+subsidiesList.size());
                    UserListAdapter userListAdapter = new UserListAdapter(AddViaContactActivity.this, subsidiesList);
                    booksLV.setAdapter(userListAdapter);
                    LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
                    sideIndex.setOnClickListener(onClicked);
                    sideIndexHeight = sideIndex.getHeight();
                    mGestureDetector = new GestureDetector(AddViaContactActivity.this, new ListIndexGestureListener());
                    }
            });
*/
    }
}
