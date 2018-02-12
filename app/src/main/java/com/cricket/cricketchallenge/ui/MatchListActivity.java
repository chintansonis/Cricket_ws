package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.adapters.MatchListAdapter;
import com.cricket.cricketchallenge.custom.TfTextView;

/**
 * Created by chintans on 14-12-2017.
 */

public class MatchListActivity extends BaseActivity {
    private android.support.v7.widget.RecyclerView rvTrending;
    private com.cricket.cricketchallenge.custom.TfTextView txtNoData;
    private Toolbar toolbar;
    private TfTextView txtTitle;
    private MatchListAdapter matchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        init();
    }

    private void init() {
        txtNoData = (TfTextView) findViewById(R.id.txtNoData);
        rvTrending = (RecyclerView) findViewById(R.id.rvTrending);
        initToolbar();
        setAdapter();
    }


    private void setAdapter() {
        rvTrending.setLayoutManager(new LinearLayoutManager(MatchListActivity.this));
        matchListAdapter = new MatchListAdapter(MatchListActivity.this);
        rvTrending.setAdapter(matchListAdapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.cricket_challenge));
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
}
