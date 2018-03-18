package com.cricket.cricketchallenge.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.TfTextView;

/**
 * Created by chintans on 14-02-2018.
 */

public class OtpVerificationActivity extends BaseActivity implements View.OnClickListener {
    private int dotsCount;
    String OTPRECEIVER_ACTION = "cricket.otp.receiver";
    private ImageView[] dots;
    private EditText edtVerify1;
    private EditText edtVerify2;
    private EditText edtVerify3;
    private EditText edtVerify4;
    private EditText edtVerify5;
    private TfTextView txtNext;
    private TfTextView txtResendOtp;
    private LinearLayout llWaitingForOtp, llOtpVerified;
    private LinearLayout CountDots;
    private String otpNumber = "";
    private boolean isOtpVerified = false;
    private SharedPreferences mPrefrence;
    private SharedPreferences.Editor mEditor;
    private TfTextView txtNextSubmit;
    BroadcastReceiver OTPReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //UI update here
            if (intent != null) {

                String OTP = intent.getStringExtra("otp");
                edtVerify1.setText(OTP.substring(0, 1));
                edtVerify2.setText(OTP.substring(1, 2));
                edtVerify3.setText(OTP.substring(2, 3));
                edtVerify4.setText(OTP.substring(3, 4));
                edtVerify5.setText(OTP.substring(4, 5));
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifcation);
        setShowBackMessage(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(OTPRECEIVER_ACTION);
        registerReceiver(OTPReceiver, intentFilter);
        init();
        final TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (edtVerify1.getText().toString().length() > 0)     //size as per your requirement
                {
                    edtVerify2.requestFocus();
                }
                if (edtVerify2.getText().toString().length() > 0)     //size as per your requirement
                {
                    edtVerify3.requestFocus();
                }
                if (edtVerify3.getText().toString().length() > 0)     //size as per your requirement
                {
                    edtVerify4.requestFocus();
                }
                if (edtVerify4.getText().toString().length() > 0)     //size as per your requirement
                {
                    edtVerify5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check Fields For Empty Values
                String s1 = edtVerify1.getText().toString();
                String s2 = edtVerify2.getText().toString();
                String s3 = edtVerify3.getText().toString();
                String s4 = edtVerify4.getText().toString();
                String s5 = edtVerify5.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equalsIgnoreCase("")) {
                    txtNext.setEnabled(false);
                    txtNext.setVisibility(View.GONE);
                } else {
                    txtNext.setEnabled(true);
                    txtNext.setVisibility(View.VISIBLE);
                    otpNumber = s1 + s2 + s3 + s4 + s5;
                }

            }
        };
        edtVerify1.addTextChangedListener(mTextWatcher);
        edtVerify2.addTextChangedListener(mTextWatcher);
        edtVerify3.addTextChangedListener(mTextWatcher);
        edtVerify4.addTextChangedListener(mTextWatcher);
        edtVerify5.addTextChangedListener(mTextWatcher);
    }


    private void init() {
        txtNext = (TfTextView) findViewById(R.id.txtNext);
        txtResendOtp = (TfTextView) findViewById(R.id.txtResendOtp);
        llOtpVerified = (LinearLayout) findViewById(R.id.llOtpVerified);
        llWaitingForOtp = (LinearLayout) findViewById(R.id.llWaitingForOtp);
        edtVerify5 = (EditText) findViewById(R.id.edtVerify5);
        edtVerify4 = (EditText) findViewById(R.id.edtVerify4);
        edtVerify3 = (EditText) findViewById(R.id.edtVerify3);
        edtVerify2 = (EditText) findViewById(R.id.edtVerify2);
        edtVerify1 = (EditText) findViewById(R.id.edtVerify1);
        txtNext.setOnClickListener(this);
        txtNextSubmit = (TfTextView) findViewById(R.id.txtNextSubmit);
        txtNextSubmit.setOnClickListener(this);
        txtResendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNext:
                if (txtNext.getText().toString().equalsIgnoreCase(getString(R.string.verify))) {
                    /*callRegisterStep2Api();*/

                }
                break;
            case R.id.txtResendOtp:
                /*callResendOtpApi();*/
                break;
            case R.id.txtNextSubmit:
                LoginLandingActivity.loginLandingActivity.finish();
                /*Intent intent = new Intent(OtpVerificationActivity.this, RegistrationPasswordActivity.class);
                intent.putExtra(isFrom, "OtpVerificationActivity");
                Functions.fireIntent(OtpVerificationActivity.this, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                break;
            default:
                break;
        }
    }

    /*private void callResendOtpApi() {
        if (Functions.isConnected(OtpVerificationActivity.this)) {
            showProgressDialog(false);
            RequestMobile requestMobile = new RequestMobile();
            requestMobile.setMobile(signUpStepTwoRequest.getMobile());
            RestClient.get().sendOtpApi(requestMobile).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            Functions.showToast(OtpVerificationActivity.this, response.body().getMessage());
                            clearAllEditText();
                        } else {
                            Functions.showToast(OtpVerificationActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_no_internet_connection));
        }
    }*/

    private void clearAllEditText() {
        edtVerify1.setText("");
        edtVerify2.setText("");
        edtVerify3.setText("");
        edtVerify4.setText("");
        edtVerify5.setText("");
    }
    /*private void callRegisterStep2Api() {
        if (Functions.isConnected(OtpVerificationActivity.this)) {
            showProgressDialog(false);
            signUpStepTwoRequest.setOTP(otpNumber);
            signUpStepTwoRequest.setDeviceToken(mPrefrence.getString("mDeviceToken", ""));
            RestClient.get().signUpStepTwoApi(signUpStepTwoRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    hideProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                            animationLeft();
                            onLoginResponse(response.body().getUserDetails());
                        } else {
                            Functions.showToast(OtpVerificationActivity.this, response.body().getMessage());
                        }
                    } else {
                        Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    hideProgressDialog();
                    Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_something_went_wrong));
                }
            });
        } else {
            Functions.showToast(OtpVerificationActivity.this, getString(R.string.err_no_internet_connection));
        }
    }*/

    /*private void onLoginResponse(UserDetails userDetails) {
        Preferences.getInstance(OtpVerificationActivity.this).setString(Preferences.KEY_USER_ID, userDetails.getUserId());
        Preferences.getInstance(OtpVerificationActivity.this).setBoolean(Preferences.KEY_IS_PASSWORD_SET, true);
        Preferences.getInstance(OtpVerificationActivity.this).setString(Preferences.KEY_USER_LOGIN_MOBILE, userDetails.getPhone());
        Preferences.getInstance(OtpVerificationActivity.this).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(userDetails));
    }*/

    private void animationLeft() {
        final Animation sideOut = AnimationUtils.loadAnimation(OtpVerificationActivity.this, R.anim.slide_out_left);
        final Animation sidein = AnimationUtils.loadAnimation(OtpVerificationActivity.this, R.anim.slide_in_right);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        llWaitingForOtp.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                llWaitingForOtp.setAnimation(sideOut);
                llOtpVerified.setVisibility(View.VISIBLE);
                llOtpVerified.startAnimation(sidein);
                txtNext.setVisibility(View.GONE);
                txtNextSubmit.setVisibility(View.VISIBLE);
                isOtpVerified = true;

            }
        }, 500);

    }


    @Override
    public void onBackPressed() {
        dofinish();
    }

    private void dofinish() {
        if (isOtpVerified) {

        } else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    /*private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().contains("SKYPLT")) {
                final String message = intent.getStringExtra("message");
                if (!message.equalsIgnoreCase("")) {
                    String[] strArr = message.split("");
                    edtVerify1.setText(strArr[0]);
                    edtVerify2.setText(strArr[1]);
                    edtVerify3.setText(strArr[2]);
                    edtVerify4.setText(strArr[3]);
                    edtVerify5.setText(strArr[4]);
                    Functions.showToast(OtpVerificationActivity.this, message);
                }
            }
        }
    };*/

/*
    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("SKYPLT"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
*/

}
