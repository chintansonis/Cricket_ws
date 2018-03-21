package com.cricket.cricketchallenge.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.adapters.AppUserListAdapter;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.ResponseAppUser;
import com.cricket.cricketchallenge.api.responsepojos.UserModel;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chintans on 14-12-2017.
 */

public class AppUserListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private Toolbar toolbar;
    private TfTextView txtTitle;
    private AppUserListAdapter appUserListAdapter;
    public ArrayList<UserModel> contactPojoArrayList = new ArrayList<>();
    public ArrayList<UserModel> SearchList = new ArrayList<>();
    private EditText etSearchContacts;
    private TfTextView tvCancel;
    private android.widget.LinearLayout llSearchContacts;
    private android.widget.ImageView ivSearch;
    private RecyclerView rvTrending;
    private SwipeRefreshLayout swipeContainer;
    private TfTextView txtNoData;
    private TfTextView tvchallnege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appuser);
        init();
        if (Functions.isConnected(AppUserListActivity.this)) {
            getMAtchListApi();
        } else {
            checkVisibility(contactPojoArrayList);
            Functions.showToast(AppUserListActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void getMAtchListApi() {
        showProgressDialog(false);
        RestClient.get().getAppUsersList().enqueue(new Callback<List<ResponseAppUser>>() {
            @Override
            public void onResponse(Call<List<ResponseAppUser>> call, Response<List<ResponseAppUser>> response) {
                hideProgressDialog();
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                        contactPojoArrayList.addAll(response.body().get(0).getData());
                        Collections.sort(contactPojoArrayList);
                        setAdapter();
                        checkVisibility(contactPojoArrayList);
                    } else {
                        Functions.showToast(AppUserListActivity.this, response.body().get(0).getMessage());
                    }
                } else {
                    checkVisibility(contactPojoArrayList);
                    Functions.showToast(AppUserListActivity.this, getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseAppUser>> call, Throwable t) {
                checkVisibility(contactPojoArrayList);
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                hideProgressDialog();
                Functions.showToast(AppUserListActivity.this, getString(R.string.err_something_went_wrong));
            }
        });

    }

    private void checkVisibility(ArrayList<UserModel> contactPojoArrayList) {
        if (contactPojoArrayList.size() > 0) {
            rvTrending.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            rvTrending.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        tvchallnege = (TfTextView) findViewById(R.id.tv_challnege);
        txtNoData = (TfTextView) findViewById(R.id.txtNoData);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        rvTrending = (RecyclerView) findViewById(R.id.rvTrending);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        llSearchContacts = (LinearLayout) findViewById(R.id.llSearchContacts);
        tvCancel = (TfTextView) findViewById(R.id.tvCancel);
        etSearchContacts = (EditText) findViewById(R.id.etSearchContacts);
        swipeContainer.setRefreshing(false);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        initToolbar();
        etSearchContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = etSearchContacts.getText().toString();
                filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tvCancel.setOnClickListener(this);
        tvchallnege.setOnClickListener(this);
    }

    private void filter(String SearchText) {
        SearchList.clear();
        if (SearchText.length() == 0) {
            setAdapter();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearchContacts.getWindowToken(), 0);
        } else {
            for (int i = 0; i < contactPojoArrayList.size(); i++) {
                if (contactPojoArrayList.get(i).getUserFullname().toString().toLowerCase().contains(SearchText.toLowerCase()) || contactPojoArrayList.get(i).getUserFullname().toUpperCase().contains(SearchText.toUpperCase()) || contactPojoArrayList.get(i).getUserMobile().toString().toLowerCase().contains(SearchText)) {
                    SearchList.add(contactPojoArrayList.get(i));
                }
            }
            appUserListAdapter = new AppUserListAdapter(AppUserListActivity.this, SearchList);
            rvTrending.setAdapter(appUserListAdapter);
            if (appUserListAdapter != null) {
                appUserListAdapter.notifyDataSetChanged();
            }
            checkvisibility(SearchList);
        }
    }

    private void checkvisibility(ArrayList<UserModel> searchList) {
        if (searchList.size() > 0) {
            swipeContainer.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            swipeContainer.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            tvchallnege.setText("Invite");
        }

    }


    private void setAdapter() {
        rvTrending.setLayoutManager(new LinearLayoutManager(AppUserListActivity.this));
        appUserListAdapter = new AppUserListAdapter(AppUserListActivity.this, contactPojoArrayList);
        rvTrending.setAdapter(appUserListAdapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.app_user_list));
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
        if (Functions.isConnected(AppUserListActivity.this)) {
            getMAtchListApi();
        } else {
            checkVisibility(contactPojoArrayList);
            swipeContainer.setRefreshing(false);
            Functions.showToast(AppUserListActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_challnege:
                if (tvchallnege.getText().toString().trim().equalsIgnoreCase("Invite")) {

                } else {

                }
                break;
            case R.id.tvCancel:
                try {
                    etSearchContacts.setText("");
                    if (getCurrentFocus() != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;

        }
    }
}
