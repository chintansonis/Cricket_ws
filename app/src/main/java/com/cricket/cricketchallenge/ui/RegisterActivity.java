package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.ResponseLogin;
import com.cricket.cricketchallenge.api.responsepojos.UserModel;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.custom.WebViewDialog;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.helper.Preferences;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private android.widget.TextView et_confirm_password;
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
        et_confirm_password = (TextView) findViewById(R.id.et_confirm_password);
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
        if (TextUtils.isEmpty(etfname.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_fisrt_name));
        }else if (TextUtils.isEmpty(etemail.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_mobile));
        } else if (etemail.getText().toString().length() < 10) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_valid_mobile));
        } else if (TextUtils.isEmpty(etpassword.getText().toString().trim())) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_enter_password));
        } else if (etpassword.getText().toString().trim().length() < 6) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_password_validation));
        } else if (et_confirm_password.getText().toString().trim().length() < 6) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_please_enter_confirm_pass));
        } else if (et_confirm_password.getText().toString().trim().length() < 6) {
            Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_password_validation));
        } else {
            if (Functions.isConnected(RegisterActivity.this)) {
                postCreateUserApi();
            } else {
                Functions.showToast(RegisterActivity.this, getResources().getString(R.string.err_no_internet_connection));
            }
            /*Functions.fireIntent(RegisterActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
        }
    }

    private void postCreateUserApi() {
        showProgressDialog(false);
        RestClient.get().postCreateUserApi(etfname.getText().toString().trim(), etemail.getText().toString().trim(),etpassword.getText().toString()).enqueue(new Callback<List<ResponseLogin>>() {
            @Override
            public void onResponse(Call<List<ResponseLogin>> call, Response<List<ResponseLogin>> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                        onLoginResponse(response.body().get(0).getData().get(0));
                    } else {
                        Functions.showToast(RegisterActivity.this, response.body().get(0).getMessage());
                    }
                } else {
                    Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseLogin>> call, Throwable t) {
                hideProgressDialog();
                Functions.showToast(RegisterActivity.this, getString(R.string.err_something_went_wrong));
            }
        });
    }

    private void onLoginResponse(UserModel userModel) {
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_ID, String.valueOf(userModel.getUserID()));
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_LOGIN_PASS_WORD, etpassword.getText().toString().trim().trim());
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_LOGIN_MOBILE, etemail.getText().toString().trim().trim());
        Preferences.getInstance(RegisterActivity.this).setBoolean(Preferences.KEY_IS_PASSWORD_SET, false);
        Preferences.getInstance(RegisterActivity.this).setBoolean(Preferences.KEY_IS_AUTO_LOGIN, true);
        Preferences.getInstance(RegisterActivity.this).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(userModel));
        Functions.fireIntent(RegisterActivity.this, DashboardActivity.class);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

