package com.example.askme.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.askme.Data.States;
import com.example.askme.MainActivity;
import com.example.askme.R;

public class notifications {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    public static void getDailyNotification(Context context, States states) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel primaryChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Daily quiz", NotificationManager.IMPORTANCE_DEFAULT);
            primaryChannel.enableLights(true);
            primaryChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(primaryChannel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String title = "Daily quiz title";
        String question = "What is the capital of" + states.getStateName() + "?";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(question)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
