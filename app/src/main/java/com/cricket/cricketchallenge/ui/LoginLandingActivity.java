package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.view.View;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.helper.Functions;

/**
 * Created by chintans on 04-12-2017.
 */

public class LoginLandingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        init();
    }

    private void init() {
        findViewById(R.id.tvExplore).setOnClickListener(this);
        findViewById(R.id.tv_signin).setOnClickListener(this);
        findViewById(R.id.tv_join).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvExplore:
                Functions.fireIntent(LoginLandingActivity.this, DashboardActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;
            case R.id.tv_signin:
                Functions.fireIntent(LoginLandingActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tv_join:
                Functions.fireIntent(LoginLandingActivity.this, RegisterActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }
}
