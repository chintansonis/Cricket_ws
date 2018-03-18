package com.cricket.cricketchallenge.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.helper.Preferences;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgBat;
    float dest = 0;
    Handler handler;
    Runnable runnable;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgBat = (ImageView) findViewById(R.id.imgBat);
        animation();
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_SMS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS);
        int RECEIVE_SMS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS);

        if (READ_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                WRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                READ_SMS == PackageManager.PERMISSION_GRANTED &&
                RECEIVE_SMS == PackageManager.PERMISSION_GRANTED) {

        }


    }

    private void animation() {
        ObjectAnimator mover = ObjectAnimator.ofFloat(imgBat,
                "translationX", -500f, 0f);
        mover.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imgBat, "alpha",
                0f, 1f);
        fadeIn.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mover).with(fadeIn);
        animatorSet.start();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (count == 1) {
                    imgBat.setImageResource(R.drawable.download);
                    counter(2);
                } else if (count == 2) {
                    imgBat.setImageResource(R.drawable.download_2);
                    counter(3);
                } else if (count == 3) {
                    imgBat.setImageResource(R.drawable.download3);
                    counter(4);
                } else if (count == 4) {
                    if (Preferences.getInstance(SplashActivity.this).getBoolean(Preferences.KEY_IS_AUTO_LOGIN)) {
                        Functions.fireIntent(SplashActivity.this, DashboardActivity.class);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginLandingActivity.class));
                        finish();
                    }
                }
            }
        };
        counter(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeCallbacks(runnable);
    }

    private void counter(int cnt) {
        count = cnt;
        handler.postDelayed(runnable, 1000);
    }

}
