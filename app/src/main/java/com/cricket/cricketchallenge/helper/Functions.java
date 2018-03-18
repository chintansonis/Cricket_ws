package com.cricket.cricketchallenge.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.FontType;
import com.droidbyme.toastlib.ToastEnum;
import com.droidbyme.toastlib.ToastLib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String toStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static int toLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static void showToast(Context context, String message) {
        new ToastLib.Builder(context, message)
                .duration(ToastEnum.SHORT)
                .backgroundColor(ContextCompat.getColor(context, R.color.orange))
                .textColor(ContextCompat.getColor(context, R.color.white))
                .typeface(getFontType(context, FontType.Medium.getId()))
                .textSize(16)
                .corner(24)
                .margin(128)
                .padding(24)
                .spacing(1)
                .gravity(Gravity.BOTTOM)
                .show();
    }

    public static void showAlertDialogWithYes(Context mContext, String message, final DialogOptionsSelectedListener dialogOptionsSelectedListener) {
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogOptionsSelectedListener != null)
                                dialogOptionsSelectedListener.onSelect(true);
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }
    public static String getTimeFromDate(String date) {
        String time = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            Date date1 = sdf1.parse(date);
            time = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void clearSession(Context context) {
        // Logout code
    }

    public static Typeface getFontType(Context _context, int typeValue) {

        Typeface typeface;

        if (typeValue == 1) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 2) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 3) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 4) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        }

        return typeface;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + " " + model;
    }

    public static void showUserDetails(final Context context) {
        // msg
    }

    public static Spanned setHTML(String mainString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mainString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(mainString);
        }
    }

    public static void showPromptDialog(Context context, String title, String message, String positive, String negative, final onPromptListener onPromptListener) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onPromptListener != null) {
                    onPromptListener.onPositive();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onPromptListener != null) {
                    onPromptListener.onNegative();
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }



    public interface onPromptListener {
        void onPositive();

        void onNegative();
    }

    public static void openPlayStore(Context context) {
        String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void noInternet(final Context context) {
        // no internet dialog
    }

    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static void loadImageURL(Context context, String url, final ImageView imageView, final ProgressBar progressBar) {
        Glide.with(context).load(url)
                .fitCenter()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(R.drawable.ic_launcher_background)
                .crossFade()
                .into(imageView);
    }

    public interface DialogOptionsSelectedListener {
        void onSelect(boolean isYes);
    }

    public static Typeface getRegularFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        return tf;
    }


    public static void setRegularFont(Context context, TextView textView) {
        textView.setTypeface(getRegularFont(context));
    }

    public static Typeface getBoldFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "fonts/Ubuntu-Bold.ttf");
        return tf;
    }
}
