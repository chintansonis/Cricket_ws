package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.Functions;

import at.markushi.ui.CircleButton;

/**
 * Created by chintans on 18-12-2017.
 */

public class ChallengeSentActivity extends BaseActivity {
    private android.widget.Button btnHome;
    private Toolbar toolbar;
    private CircleButton doneCircle;
    private TfTextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_sent);
        init();
    }

    private void init() {
        doneCircle = findViewById(R.id.doneCircle);
        btnHome = findViewById(R.id.btnHome);
        doneCircle.setPressed(true);
        initToolbar();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(getApplicationContext(), DashboardActivity.class);
                doFinish();
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("Congratulations");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Functions.fireIntent(getApplicationContext(), DashboardActivity.class);
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
    public void onBackPressed() {
        Functions.fireIntent(getApplicationContext(), DashboardActivity.class);
        doFinish();
    }

}
