package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.Functions;

/**
 * Created by chintans on 05-12-2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.LinearLayout llback;
    private com.cricket.cricketchallenge.custom.MyEditText etemailsignin;
    private com.cricket.cricketchallenge.custom.MyEditText etpasswordsignin;
    private com.cricket.cricketchallenge.custom.TfTextView tvsigninsignin;
    private com.cricket.cricketchallenge.custom.TfTextView tvRegister;
    private com.cricket.cricketchallenge.custom.TfTextView tvforgotpassword;
    private android.widget.LinearLayout llsignmain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setShowBackMessage(false);
        init();
    }
    private void init() {
        llsignmain = (LinearLayout) findViewById(R.id.ll_sign_main);
        tvforgotpassword = (TfTextView) findViewById(R.id.tv_forgot_password);
        tvsigninsignin = (TfTextView) findViewById(R.id.tv_signin_signin);
        tvRegister = (TfTextView) findViewById(R.id.tvRegister);
        etpasswordsignin = (MyEditText) findViewById(R.id.et_password_signin);
        etemailsignin = (MyEditText) findViewById(R.id.et_email_signin);
        llback = (LinearLayout) findViewById(R.id.ll_back);
        findViewById(R.id.ll_back).setOnClickListener(this);
        findViewById(R.id.tv_signin_signin).setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                Functions.hideKeyPad(LoginActivity.this, llback);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_signin_signin:
                signinRequest();
                break;
            case R.id.tvRegister:
                Functions.fireIntent(LoginActivity.this, RegisterActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }

    }

    private void signinRequest() {
        if (TextUtils.isEmpty(etemailsignin.getText().toString().trim())) {
            Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_enter_mobile));
        } else if (etemailsignin.getText().toString().length() < 10) {
            Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_enter_valid_mobile));
        } else if (TextUtils.isEmpty(etpasswordsignin.getText().toString().trim())) {
            Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_enter_password));
        } else if (etpasswordsignin.getText().toString().trim().length() < 6) {
            Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_password_validation));
        } else {
            Functions.fireIntent(LoginActivity.this, DashboardActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
