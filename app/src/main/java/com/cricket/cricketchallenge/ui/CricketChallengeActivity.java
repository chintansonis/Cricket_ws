package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.Functions;

/**
 * Created by chintans on 14-12-2017.
 */

public class CricketChallengeActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TfTextView txtTitle;
    private TfTextView tvtosschallenge;
    private TfTextView tvwinchallenge;
    private TfTextView tvmanofmatchchallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_challenge);
        init();
    }

    private void init() {
        tvmanofmatchchallenge = (TfTextView) findViewById(R.id.tv_man_of_match_challenge);
        tvwinchallenge = (TfTextView) findViewById(R.id.tv_win_challenge);
        tvtosschallenge = (TfTextView) findViewById(R.id.tv_toss_challenge);
        initToolbar();
        tvmanofmatchchallenge.setOnClickListener(this);
        tvtosschallenge.setOnClickListener(this);
        tvwinchallenge.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.match_challenge));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onBackPressed() {
        doFinish();
    }

    private void doFinish() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_man_of_match_challenge:
                Functions.fireIntent(CricketChallengeActivity.this, MatchListActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tv_toss_challenge:
                Functions.fireIntent(CricketChallengeActivity.this, MatchListActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tv_win_challenge:
                Functions.fireIntent(CricketChallengeActivity.this, MatchListActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }
}

