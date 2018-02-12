package com.cricket.cricketchallenge.commonmodel;

import java.io.Serializable;

/**
 * Created by FIPL on 19/09/2015.
 */
public class ContactModel implements Serializable {
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked = false;
    public String RowNum;
    public String Contact_ID;
    public String Contact_First_Name;
    public String Contact_Last_Name;
    public String Contact_Email;
    public String Contact_Mobile;
    public String Contact_Address1;
    public String Contact_Address2;
    public String Contact_City;
    public String Contact_State;
        public String Contact_Country;
    public String Contact_ZipCode;
    public String Contact_User_ID;
    public String Status;
    public String Message;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String flag;

    public String getContact_Favourite() {
        return Contact_Favourite;
    }

    public void setContact_Favourite(String contact_Favourite) {
        Contact_Favourite = contact_Favourite;
    }

    public String Contact_Favourite;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected;

    public String getRowNum() {
        return RowNum;
    }

    public void setRowNum(String rowNum) {
        RowNum = rowNum;
    }

    public String getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(String contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getContact_First_Name() {
        return Contact_First_Name;
    }

    public void setContact_First_Name(String contact_First_Name) {
        Contact_First_Name = contact_First_Name;
    }

    public String getContact_Last_Name() {
        return Contact_Last_Name;
    }

    public void setContact_Last_Name(String contact_Last_Name) {
        Contact_Last_Name = contact_Last_Name;
    }

    public String getContact_Email() {
        return Contact_Email;
    }

    public void setContact_Email(String contact_Email) {
        Contact_Email = contact_Email;
    }

    public String getContact_Mobile() {
        return Contact_Mobile;
    }

    public void setContact_Mobile(String contact_Mobile) {
        Contact_Mobile = contact_Mobile;
    }

    public String getContact_Address1() {
        return Contact_Address1;
    }

    public void setContact_Address1(String contact_Address1) {
        Contact_Address1 = contact_Address1;
    }

    public String getContact_Address2() {
        return Contact_Address2;
    }

    public void setContact_Address2(String contact_Address2) {
        Contact_Address2 = contact_Address2;
    }

    public String getContact_City() {
        return Contact_City;
    }

    public void setContact_City(String contact_City) {
        Contact_City = contact_City;
    }

    public String getContact_State() {
        return Contact_State;
    }

    public void setContact_State(String contact_State) {
        Contact_State = contact_State;
    }

    public String getContact_Country() {
        return Contact_Country;
    }

    public void setContact_Country(String contact_Country) {
        Contact_Country = contact_Country;
    }

    public String getContact_ZipCode() {
        return Contact_ZipCode;
    }

    public void setContact_ZipCode(String contact_ZipCode) {
        Contact_ZipCode = contact_ZipCode;
    }

    public String getContact_User_ID() {
        return Contact_User_ID;
    }

    public void setContact_User_ID(String contact_User_ID) {
        Contact_User_ID = contact_User_ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


}
