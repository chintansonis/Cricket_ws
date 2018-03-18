package com.cricket.cricketchallenge.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable{
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked = false;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("userFullname")
    @Expose
    private String userFullname;
    @SerializedName("userMobile")
    @Expose
    private String userMobile;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userAddress")
    @Expose
    private String userAddress;
    @SerializedName("userLatitude")
    @Expose
    private String userLatitude;
    @SerializedName("userLongitude")
    @Expose
    private String userLongitude;
    @SerializedName("userTotalPoints")
    @Expose
    private Integer userTotalPoints;
    @SerializedName("userPoints")
    @Expose
    private Integer userPoints;
    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("userLevel")
    @Expose
    private Integer userLevel;
    @SerializedName("userCode")
    @Expose
    private String userCode;
    @SerializedName("userCreatedDate")
    @Expose
    private String userCreatedDate;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public Integer getUserTotalPoints() {
        return userTotalPoints;
    }

    public void setUserTotalPoints(Integer userTotalPoints) {
        this.userTotalPoints = userTotalPoints;
    }

    public Integer getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(Integer userPoints) {
        this.userPoints = userPoints;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(String userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

}
