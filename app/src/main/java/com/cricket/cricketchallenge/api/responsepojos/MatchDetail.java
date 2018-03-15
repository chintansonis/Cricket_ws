package com.cricket.cricketchallenge.api.responsepojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchDetail implements Serializable {

    @SerializedName("matchID")
    @Expose
    private String matchID;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("team1")
    @Expose
    private String team1;
    @SerializedName("team2")
    @Expose
    private String team2;
    @SerializedName("matchType")
    @Expose
    private String matchType;
    @SerializedName("matchDate")
    @Expose
    private String matchDate;
    @SerializedName("matchTime")
    @Expose
    private String matchTime;
    @SerializedName("matchCreatedDate")
    @Expose
    private String matchCreatedDate;
    @SerializedName("team1Name")
    @Expose
    private String team1Name;
    @SerializedName("team2Name")
    @Expose
    private String team2Name;

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchCreatedDate() {
        return matchCreatedDate;
    }

    public void setMatchCreatedDate(String matchCreatedDate) {
        this.matchCreatedDate = matchCreatedDate;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

}
