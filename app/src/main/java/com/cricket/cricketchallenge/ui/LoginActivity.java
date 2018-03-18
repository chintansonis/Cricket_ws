package com.cricket.cricketchallenge.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.ResponseLogin;
import com.cricket.cricketchallenge.api.responsepojos.UserModel;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
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
            /*Functions.fireIntent(LoginActivity.this, DashboardActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            if (Functions.isConnected(LoginActivity.this)) {
                postLoginApi();
            } else {
                Functions.showToast(LoginActivity.this, getResources().getString(R.string.err_no_internet_connection));
            }
        }
    }

    private void postLoginApi() {
        showProgressDialog(false);
        RestClient.get().postLoginApi(etemailsignin.getText().toString().trim(), etpasswordsignin.getText().toString().trim()).enqueue(new Callback<List<ResponseLogin>>() {
            @Override
            public void onResponse(Call<List<ResponseLogin>> call, Response<List<ResponseLogin>> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                        onLoginResponse(response.body().get(0).getData().get(0));
                    } else {
                        Functions.showToast(LoginActivity.this, response.body().get(0).getMessage());
                    }
                } else {
                    Functions.showToast(LoginActivity.this, getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseLogin>> call, Throwable t) {
                hideProgressDialog();
                Functions.showToast(LoginActivity.this, getString(R.string.err_something_went_wrong));
            }
        });
    }

    private void onLoginResponse(UserModel userModel) {
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_ID, String.valueOf(userModel.getUserID()));
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_LOGIN_PASS_WORD, etpasswordsignin.getText().toString().trim().trim());
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_LOGIN_MOBILE, etemailsignin.getText().toString().trim().trim());
        Preferences.getInstance(LoginActivity.this).setBoolean(Preferences.KEY_IS_PASSWORD_SET, false);
        Preferences.getInstance(LoginActivity.this).setBoolean(Preferences.KEY_IS_AUTO_LOGIN, true);
        Preferences.getInstance(LoginActivity.this).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(userModel));
        LoginLandingActivity.loginLandingActivity.finish();
        Functions.fireIntent(LoginActivity.this, DashboardActivity.class);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
