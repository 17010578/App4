package com.myapplicationdev.android.app4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class BroadcastTaskReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        String name = intent.getStringExtra("name");

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] v = {500,1000};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context,TaskArrayAdapter.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        // BigText style notification
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle("Task Manager Reminder");
        bigText.bigText("");
        bigText.setSummaryText("New Task Added");

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(name);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setStyle(bigText);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        builder.setSound(soundUri);
        builder.setVibrate(v);
        builder.setLights(Color.RED, 3000, 3000);

        Notification n = builder.build();

        // An integer good to have, for you to programmatically cancel it
        notificationManager.notify(123, n);

    }
}
