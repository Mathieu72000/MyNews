package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.corroy.mathieu.mynews.R;

public class MyAlarm extends BroadcastReceiver {

    private String notification_title = "MyNews";
    private String notification_text = "We have some news for you !";

    @Override
    public void onReceive(Context context, Intent intent) {

            if (Build.VERSION.SDK_INT < 26) {
                return;
            }
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);

            // Create an explicit intent for an Activity in your app
            Intent mIntent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                    .setSmallIcon(R.drawable.newyorktimesicon)
                    .setContentTitle(notification_title)
                    .setContentText(notification_text)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

        notificationManager.notify(1, mBuilder.build());

                 Log.i("ALARM", "Je sonne !");
        }
    }