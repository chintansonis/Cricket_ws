package com.cricket.cricketchallenge.api;

import com.cricket.cricketchallenge.api.responsepojos.ResponseMatchList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Shriji on 3/15/2018.
 */

public interface AppApi {
    @POST("matches/list")
    Call<List<ResponseMatchList>> getMatchList();
}
