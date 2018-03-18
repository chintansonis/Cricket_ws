package com.cricket.cricketchallenge.services.receivers;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cricket.cricketchallenge.helper.SMSCode;


public class MySMSBroadCastReceiver extends BroadcastReceiver {
    String OTPRECEIVER_ACTION = "cricket.otp.receiver";


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.e("SMS Code","j");
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");

        for (Object p : pdus){
            byte[] sms = (byte[]) p;

            SmsMessage message = SmsMessage.createFromPdu(sms);

            String content = message.getMessageBody();
            String number = message.getOriginatingAddress();

            SMSCode.SMSInfo smsInfo = SMSCode.findSMSCode(content);

            if (smsInfo != null) {
                // toast
                // Toast.makeText(context, "YO"+ smsInfo.code, Toast.LENGTH_LONG).show();

                // 复制到剪贴板
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("SmsCode", smsInfo.code);
                clipboardManager.setPrimaryClip(clipData);


                Intent local = new Intent();
                local.setAction(OTPRECEIVER_ACTION);
                local.putExtra("otp", smsInfo.code);
                context.sendBroadcast(local);


            }
        }
    }
}
