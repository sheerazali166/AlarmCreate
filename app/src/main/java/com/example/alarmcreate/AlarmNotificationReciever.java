package com.example.alarmcreate;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmNotificationReciever extends BroadcastReceiver {

    // TODO: Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String TAG = "AlarmNotificationReciever";

    // TODO: Notification Text Elememnts
    private final CharSequence tickerText = "You are playing angry birds again!";
    private final CharSequence contentTitle = "A kind reminder";
    private final CharSequence contentText = "Get back to studying!";

    // TODO: Notification Action Elements
    private Intent notificationIntent;
    private PendingIntent contentPendingIntent = null;

    // TODO:  Notification Sound and Vibration on Arrival
    private final Uri soundURL = Uri.parse("android.resource://com.example.alarmcreate/"
            + R.raw.alarm_rooster);

    private final long[] vibratePattern = { 0, 200, 300, 300 };

    @SuppressLint({"UnspecifiedImmutableFlag", "WrongConstant"})
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: The intent to be used when the user clicks on the notification view
        notificationIntent = new Intent(context, MainActivity.class);

        // TODO: Pending intent that wraps the underlying intent
        // contentPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            contentPendingIntent = PendingIntent.getActivity
                    (context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            contentPendingIntent = PendingIntent.getActivity
                    (context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        // contentPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        // TODO: Build the notification
        Notification.Builder builder = new Notification.Builder(context).setTicker(tickerText)
                .setSmallIcon(R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(contentPendingIntent)
                .setSound(soundURL).setVibrate(vibratePattern);

        // TODO: Get the Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // TODO: Press the Notification to the Notification Manager
        notificationManager.notify(MY_NOTIFICATION_ID, builder.build());

        // TODO: Log occurence of notify() call
        Log.i(TAG, "Sending notification at: " + DateFormat.getDateTimeInstance().format(new Date()));

    }
}
