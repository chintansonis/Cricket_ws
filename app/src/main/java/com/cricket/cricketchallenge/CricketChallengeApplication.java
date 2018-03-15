package com.cricket.cricketchallenge;

import android.app.Application;
import android.content.Context;

import com.cricket.cricketchallenge.api.RestClient;

/**
 * Created by chintans on 29-11-2017.
 */

public class CricketChallengeApplication extends Application {
    public Context context;
    private static CricketChallengeApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RestClient.setupRestClient();
        /*Fabric.with(this, new Crashlytics());*/
        /*if (!new AppDebug().isDebuggable(this)) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.cricket.cricketchallenge");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("CRASHED", true);
                    intent.putExtra("EXCEPTION", paramThrowable);
                    startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });
        }*/
    }
}
