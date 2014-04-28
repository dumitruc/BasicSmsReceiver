package com.dima.basicsmsreceiver.smsreceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

import static com.dima.basicsmsreceiver.smsreceiver.SmsKeyFieldsHelper.*;


/**
 * Created by dima on 03/03/2014.
 */
public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();

        if (extras == null){
            return;
        }

        Object[] pdus = (Object[]) extras.get("pdus");
        for (Object pdusItem:pdus){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdusItem);


           Intent smsLaunchIntent = new Intent("BasicSmsReceiver.intent.MAIN");

            smsLaunchIntent.putExtra(SMS_BODY,smsMessage.getMessageBody());
            smsLaunchIntent.putExtra(SMS_SENDER,smsMessage.getOriginatingAddress());
            smsLaunchIntent.putExtra(SMS_RECEIVED_TIME,smsMessage.getTimestampMillis());

            context.sendBroadcast(smsLaunchIntent);
        }
    }


}
