package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.adapters.MatchListAdapter;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.MatchDetail;
import com.cricket.cricketchallenge.api.responsepojos.ResponseMatchList;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chintans on 14-12-2017.
 */

public class MatchListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private android.support.v4.widget.SwipeRefreshLayout swipeContainer;
    private android.support.v7.widget.RecyclerView rvTrending;
    private com.cricket.cricketchallenge.custom.TfTextView txtNoData;
    private Toolbar toolbar;
    private TfTextView txtTitle;
    private MatchListAdapter matchListAdapter;
    private ArrayList<MatchDetail> matchDetailArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        init();
        if (Functions.isConnected(MatchListActivity.this)) {
            getMAtchListApi();
        } else {
            Functions.showToast(MatchListActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void getMAtchListApi() {
        showProgressDialog(false);
        RestClient.get().getMatchList().enqueue(new Callback<List<ResponseMatchList>>() {
                                                    @Override
                                                    public void onResponse(Call<List<ResponseMatchList>> call, Response<List<ResponseMatchList>> response) {
                                                        hideProgressDialog();
                                                        if (swipeContainer.isRefreshing()) {
                                                            swipeContainer.setRefreshing(false);
                                                        }
                                                        if (response.body() != null) {
                                                            if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                                                                matchDetailArrayList.addAll(response.body().get(0).getData());
                                                                matchListAdapter.addAll(matchDetailArrayList);
                                                            } else {
                                                                Functions.showToast(MatchListActivity.this, response.body().get(0).getMessage());
                                                            }
                                                        } else {
                                                            Functions.showToast(MatchListActivity.this, getString(R.string.err_something_went_wrong));
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<ResponseMatchList>> call, Throwable t) {
                                                        if (swipeContainer.isRefreshing()) {
                                                            swipeContainer.setRefreshing(false);
                                                        }
                                                        hideProgressDialog();
                                                        Functions.showToast(MatchListActivity.this, getString(R.string.err_something_went_wrong));
                                                    }
                                                });

    }

    private void init() {
        txtNoData = (TfTextView) findViewById(R.id.txtNoData);
        rvTrending = (RecyclerView) findViewById(R.id.rvTrending);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(false);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        initToolbar();
        setAdapter();
    }


    private void setAdapter() {
        rvTrending.setLayoutManager(new LinearLayoutManager(MatchListActivity.this));
        matchListAdapter = new MatchListAdapter(MatchListActivity.this, matchDetailArrayList);
        rvTrending.setAdapter(matchListAdapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.match_list));
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        doFinish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                doFinish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doFinish() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onRefresh() {
        if (Functions.isConnected(MatchListActivity.this)) {
            getMAtchListApi();
        } else {
            swipeContainer.setRefreshing(false);
            Functions.showToast(MatchListActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }
}
