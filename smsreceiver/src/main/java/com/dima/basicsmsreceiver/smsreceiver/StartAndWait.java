package com.dima.basicsmsreceiver.smsreceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.dima.basicsmsreceiver.smsreceiver.SmsKeyFieldsHelper.*;

public class StartAndWait extends ActionBarActivity{

    private BroadcastReceiver smsIntentReceiver;

    TextView senderName;
    TextView smsContent;
    TextView smsTimeStamp;
    TextView smsCounter;

    Button btnCancel;
    Button btnResetCounter;

    Integer smsCount;
    String smsBody;
    String smsSender;
    String smsTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senderName = (TextView) findViewById(R.id.sender_name);
        smsContent = (TextView) findViewById(R.id.sms_content);
        smsTimeStamp=(TextView) findViewById(R.id.timeStamp);
        smsCounter = (TextView) findViewById(R.id.counter);
        smsCount = 0;

        btnCancel = (Button) findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartAndWait.this.finish();
            }
        });

        btnResetCounter = (Button) findViewById(R.id.btnResetCounter);
        btnResetCounter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                smsCount=0;
                smsBody = "";
                smsSender = "";
                smsTimeText = "";

                drawContent();

            }
        });
        drawContent();
    }

    @Override
    protected void onResume(){
        super.onResume();

        IntentFilter intentFilter= new IntentFilter("BasicSmsReceiver.intent.MAIN");

        smsIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Long timestamp = intent.getLongExtra(SMS_RECEIVED_TIME,0l);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                smsCount++;
                Date smsReceiveDate = new Date(timestamp);

                Toast.makeText(getApplicationContext(), "New Sms Received", 1).show();

                smsBody = intent.getStringExtra(SMS_BODY);
                smsSender = intent.getStringExtra(SMS_SENDER);
                smsTimeText = simpleDateFormat.format(smsReceiveDate);

                drawContent();

            }
        };
        this.registerReceiver(smsIntentReceiver,intentFilter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void drawContent(){
        smsContent.setText(smsBody);
        senderName.setText(smsSender);
        smsTimeStamp.setText(smsTimeText);
        smsCounter.setText(String.valueOf(smsCount));
    }

}
