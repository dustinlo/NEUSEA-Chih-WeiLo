package edu.neu.khoury.madsea.chihweilo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

import edu.neu.khoury.madsea.chihweilo.R;

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNELID = "test";
    public static final String CHANNELNAME = "ToDoApp - Dustin Lo";

    private NotificationManager mNotificationManager;

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null){
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    private void createChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_DEFAULT);
        getNotificationManager().createNotificationChannel(channel);
    }

    public NotificationCompat.Builder getNotification(String title) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNELID)
                .setContentTitle(title)
                .setContentText("Reminder: task to complete -> " + title)
                .setSmallIcon(R.drawable.ic_baseline_notes_24);
    }
}
