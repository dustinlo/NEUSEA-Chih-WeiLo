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
        String title = intent.getStringExtra("todo");
        System.out.println("TESTTTTTTTT " + title);
        NotificationCompat.Builder nb = notificationHelper.getNotification(title);
        int id = ThreadLocalRandom.current().nextInt(1, 100000 + 1);
        notificationHelper.getNotificationManager().notify(id, nb.build());
    }
}
