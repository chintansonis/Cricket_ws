package com.cricket.cricketchallenge.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PendingObject implements Serializable {

    @SerializedName("predictionID")
    @Expose
    private String predictionID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("otherUserID")
    @Expose
    private String otherUserID;
    @SerializedName("predictionType")
    @Expose
    private String predictionType;
    @SerializedName("matchID")
    @Expose
    private String matchID;
    @SerializedName("teamID")
    @Expose
    private String teamID;
    @SerializedName("playerID")
    @Expose
    private String playerID;
    @SerializedName("predictionCreatedDate")
    @Expose
    private String predictionCreatedDate;
    @SerializedName("predictionStatus")
    @Expose
    private String predictionStatus;
    @SerializedName("userFullname")
    @Expose
    private String userFullname;

    public String getPredictionID() {
        return predictionID;
    }

    public void setPredictionID(String predictionID) {
        this.predictionID = predictionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOtherUserID() {
        return otherUserID;
    }

    public void setOtherUserID(String otherUserID) {
        this.otherUserID = otherUserID;
    }

    public String getPredictionType() {
        return predictionType;
    }

    public void setPredictionType(String predictionType) {
        this.predictionType = predictionType;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPredictionCreatedDate() {
        return predictionCreatedDate;
    }

    public void setPredictionCreatedDate(String predictionCreatedDate) {
        this.predictionCreatedDate = predictionCreatedDate;
    }

    public String getPredictionStatus() {
        return predictionStatus;
    }

    public void setPredictionStatus(String predictionStatus) {
        this.predictionStatus = predictionStatus;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

}
