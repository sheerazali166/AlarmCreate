package com.example.alarmcreate;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private Intent notificationRecieverIntent;
    private Intent loggerRecieverIntent;
    private PendingIntent notificationRecieverPendingIntent;
    private PendingIntent loggerRecieverPendingIntent;

    private static final long INITIAL_ALARM_DELAY = 2 * 56 * 2000L;
    protected static final long JITTER = 4000L;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // TODO: Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // TODO: Create an intent to broadcast to the Alarm Notification Reciever
        notificationRecieverIntent = new Intent(MainActivity.this, AlarmNotificationReciever.class);

        // TODO: Create an Pending Intent that holds the Notification Reciever Intent
        // notificationRecieverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
               // notificationRecieverIntent, 0);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            notificationRecieverPendingIntent = PendingIntent.getActivity
                    (MainActivity.this, 0, notificationRecieverIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            notificationRecieverPendingIntent = PendingIntent.getActivity
                    (MainActivity.this, 0, notificationRecieverIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        // TODO: Create an intent to broadcast to the Alarm Logger Reciever
        loggerRecieverIntent = new Intent(MainActivity.this, AlarmLoggerReciever.class);

        // TODO: Create Pending Intent that holds the Logger Reciever Pending Intent
        // loggerRecieverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, loggerRecieverIntent, 0);

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            loggerRecieverPendingIntent = PendingIntent.getActivity
                    (MainActivity.this, 0, loggerRecieverIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            loggerRecieverPendingIntent = PendingIntent.getActivity
                    (MainActivity.this, 0, loggerRecieverIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        // TODO: Setup single alarm button
        Button singleAlarmButton = findViewById(R.id.single_alarm_button);
        singleAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Set single alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +
                        INITIAL_ALARM_DELAY, notificationRecieverPendingIntent);

                // TODO: Set single alarm to fire shortly after previous alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INITIAL_ALARM_DELAY
                        + JITTER, loggerRecieverPendingIntent);

                // TODO: Show Toast Message
                Toast.makeText(MainActivity.this, "Single Alarm Set", Toast.LENGTH_SHORT).show();

            }
        });

        // TODO: Set up repeating Alarm Button
        Button repeatingAlarmButton = findViewById(R.id.repeating_alarm_button);
        repeatingAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Set repeating alarm
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +
                        INITIAL_ALARM_DELAY, AlarmManager.INTERVAL_FIFTEEN_MINUTES, notificationRecieverPendingIntent);

                // TODO: Set repeating alarm to fire shortly after previous alarm
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
                        + JITTER, AlarmManager.INTERVAL_FIFTEEN_MINUTES, loggerRecieverPendingIntent);

                // TODO: Show Toast Message
                Toast.makeText(MainActivity.this, "Repeating Alarm Set", Toast.LENGTH_SHORT).show();

            }
        });

        // TODO: Set up inexact repeating Alarm Button
        Button inexactRepeatingAlarmButton = findViewById(R.id.inexact_repeating_alarm_button);
        inexactRepeatingAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Set inexact repeating alarm
                alarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        notificationRecieverPendingIntent);
            }
        });

        // TODO: Set inexact repeating alarm to fire shortly after previous alarm
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY + JITTER,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, loggerRecieverPendingIntent);

        Toast.makeText(this, "Inexact Repeating Alarm Set", Toast.LENGTH_SHORT).show();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TODO: Set up cancel repeating alarm button
        Button cancelRepeatingAlarmButton = findViewById(R.id.cancel_repeating_alarm_button);
        cancelRepeatingAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Cancel all alarms using Notification Reciever Intent Pending
                alarmManager.cancel(notificationRecieverPendingIntent);

                // TODO: Cancel all alarms using Logger Reciever Pending Intent
                alarmManager.cancel(loggerRecieverPendingIntent);

                // TODO: Show Toast Message
                Toast.makeText(MainActivity.this, "Repeating Alarms Cancelled", Toast.LENGTH_SHORT).show();


            }
        });


    }
}