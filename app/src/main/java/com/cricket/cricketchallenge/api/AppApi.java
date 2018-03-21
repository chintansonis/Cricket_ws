package com.cricket.cricketchallenge.api;

import com.cricket.cricketchallenge.api.responsepojos.ResponseAppUser;
import com.cricket.cricketchallenge.api.responsepojos.ResponseLogin;
import com.cricket.cricketchallenge.api.responsepojos.ResponseMatchList;
import com.cricket.cricketchallenge.api.responsepojos.ResponsePending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Shriji on 3/15/2018.
 */

public interface AppApi {
    @FormUrlEncoded
    @POST("prediction/pendinglist")
    Call<List<ResponsePending>> getPendingList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST("prediction/requestedlist")
    Call<List<ResponsePending>> getRequestedList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST("prediction/acceptedlist")
    Call<List<ResponsePending>> getAcceptedList(@Field("userID") String userID);

    @POST("matches/list")
    Call<List<ResponseMatchList>> getMatchList();

    @POST("users/list")
    Call<List<ResponseAppUser>> getAppUsersList();

    @FormUrlEncoded
    @POST("users/login")
    Call<List<ResponseLogin>> postLoginApi(@Field("userMobile") String userMobile,@Field("userPassword") String userPassword);


    @FormUrlEncoded
    @POST("users/create-user")
    Call<List<ResponseLogin>> postCreateUserApi(@Field("userFullname") String userFullname,@Field("userMobile") String userMobile,@Field("userPassword") String userPassword);
}
