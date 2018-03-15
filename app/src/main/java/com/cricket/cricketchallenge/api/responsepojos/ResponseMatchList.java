package com.cricket.cricketchallenge.api.responsepojos;

import com.cricket.cricketchallenge.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMatchList extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<MatchDetail> data = null;

    public List<MatchDetail> getData() {
        return data;
    }

    public void setData(List<MatchDetail> data) {
        this.data = data;
    }
}