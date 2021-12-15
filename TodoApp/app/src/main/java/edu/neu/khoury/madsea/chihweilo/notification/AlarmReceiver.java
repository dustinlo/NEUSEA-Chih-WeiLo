package edu.neu.khoury.madsea.chihweilo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.ThreadLocalRandom;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        String title = intent.getStringExtra("todo_title");
        int id = intent.getIntExtra("todo_id", -1);

        NotificationCompat.Builder nb = notificationHelper.getNotification(title);
        notificationHelper.getNotificationManager().notify(id, nb.build());
    }
}
