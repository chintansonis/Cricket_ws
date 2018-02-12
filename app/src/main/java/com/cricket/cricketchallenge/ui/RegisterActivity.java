package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.custom.WebViewDialog;
import com.cricket.cricketchallenge.helper.Functions;

/**
 * Created by chintans on 05-12-2017.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private android.widget.LinearLayout llback;
    private com.cricket.cricketchallenge.custom.MyEditText etfname;
    private com.cricket.cricketchallenge.custom.MyEditText etlname;
    private com.cricket.cricketchallenge.custom.MyEditText etemail;
    private com.cricket.cricketchallenge.custom.MyEditText etpassword;
    private android.widget.TextView etcountryname;
    private android.widget.EditText etphoneno;
    private com.cricket.cricketchallenge.custom.TfTextView tvcontinue;
    private com.cricket.cricketchallenge.custom.TfTextView tvTermsService;
    private com.cricket.cricketchallenge.custom.TfTextView tvPrivacyPolicy;
    private com.cricket.cricketchallenge.custom.TfTextView tvalreadyhaveaccount;
    private android.widget.LinearLayout llmainjoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setShowBackMessage(false);
        init();

    }

    private void init() {
        llmainjoin = (LinearLayout) findViewById(R.id.ll_main_join);
        tvalreadyhaveaccount = (TfTextView) findViewById(R.id.tv_already_have_account);
        tvPrivacyPolicy = (TfTextView) findViewById(R.id.tvPrivacyPolicy);
        tvTermsService = (TfTextView) findViewById(R.id.tvTermsService);
        tvcontinue = (TfTextView) findViewById(R.id.tv_continue);
        etphoneno = (EditText) findViewById(R.id.et_phone_no);
        etcountryname = (TextView) findViewById(R.id.et_country_name);
        etpassword = (MyEditText) findViewById(R.id.et_password);
        etemail = (MyEditText) findViewById(R.id.et_email);
        etlname = (MyEditText) findViewById(R.id.et_lname);
        etfname = (MyEditText) findViewById(R.id.et_fname);
        llback = (LinearLayout) findViewById(R.id.ll_back);
        findViewById(R.id.ll_back).setOnClickListener(this);
        findViewById(R.id.tvTermsService).setOnClickListener(this);
        findViewById(R.id.tvPrivacyPolicy).setOnClickListener(this);
        findViewById(R.id.tv_continue).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                Functions.hideKeyPad(RegisterActivity.this, llback);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tvTermsService:
                new WebViewDialog(RegisterActivity.this, RegisterActivity.this.getResources().getString(R.string.terms_and_condition_url), RegisterActivity.this.getResources().getString(R.string.terms_services)).show();
                break;
            case R.id.tvPrivacyPolicy:
                new WebViewDialog(RegisterActivity.this, RegisterActivity.this.getResources().getString(R.string.terms_and_condition_url), RegisterActivity.this.getResources().getString(R.string.terms_services)).show();
                break;
            case R.id.tv_continue:
                onRequestForRegister();
                break;

        }
    }

    private void onRequestForRegister() {
        if (TextUtils.isEmpty(etemail.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_mobile));
        } else if (!Functions.emailValidation(etemail.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_valid_mobile));
        } else if (TextUtils.isEmpty(etpassword.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_password));
        } else if (etpassword.getText().toString().trim().length() < 6) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_password_validation));
        } else {
            Functions.fireIntent(RegisterActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}

