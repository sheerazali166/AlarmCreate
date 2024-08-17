package com.example.alarmcreate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmLoggerReciever extends BroadcastReceiver {

    private static final String TAG = "AlarmLoggerReciever";

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: Log receipt of the intent with timestamp
        Log.i(TAG, "onReceive: Logging alarm at " + DateFormat.getDateTimeInstance().format(new Date()));


    }
}
