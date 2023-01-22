package com.example.poke_pedia;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHandler extends ContextWrapper {

    private NotificationManager manager;
    public static final String CHANNEL_HIGH_ID = "1";
    public static final String CHANNEL_LOW_ID = "2";
    private final String HIGH_CH = "HIGH CHANNEL";
    private final String LOW_CH = "LOW CHANNEL";

    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }
    public NotificationManager getManager() {
        if (manager==null)
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return manager;
    }
    public void createChannels () {
        NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, HIGH_CH, NotificationManager.IMPORTANCE_HIGH);
        highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationChannel lowChanel = new NotificationChannel(CHANNEL_LOW_ID, LOW_CH, NotificationManager.IMPORTANCE_LOW);
        lowChanel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(highChannel);
        getManager().createNotificationChannel(lowChanel);
    }

    public Notification.Builder createNotification (String title, String msg, boolean priority) {
        if (Build.VERSION.SDK_INT>= 26) {
            if (priority) {
                return createNotificationChannels(title, msg, CHANNEL_HIGH_ID);
            }
            return createNotificationChannels(title, msg, CHANNEL_LOW_ID);
        }
        return createNotificationNoChannels(title, msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder createNotificationChannels (String title, String msg, String channel) {

        return  new Notification.Builder(getApplicationContext(), channel)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_baseline_send_24)
                .setAutoCancel(true);
    }

    private Notification.Builder createNotificationNoChannels (String title, String msg) {
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_baseline_send_24)
                .setAutoCancel(true);
    }
}
