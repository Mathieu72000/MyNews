package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MyAlarm extends BroadcastReceiver {

    private Context mContext;
    private String category;
    private String query;
    private Disposable dispose;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        retrieveSharedPreferences();
        executeHttpRequestWithRetrofit();
        }

        private void executeHttpRequestWithRetrofit(){
            dispose = MyNewsStreams.streamFetchSearch(query, null, null, category).subscribeWith(new DisposableObserver<Search>() {
                @Override
                public void onNext(Search value) {
                    Log.e("NOTIF", "on Next");
                    if(value.getResponse().getDocs().size() > 0) {
                        sendNotification(value.getResponse().getDocs().size());
                    } else if
                        (value.getResponse().getDocs().size() == 0) {
                        sendNotification(0);
                        }
                    }

                @Override
                public void onError(Throwable e) {
                    Log.e("NOTIF", "on Error");
                }

                @Override
                public void onComplete() {
                }
            });
        }

        private void sendNotification(int numberArticles){
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            String notification_title = "MyNews";
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("New articles available");
            inboxStyle.addLine("There is " + numberArticles + " article available");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default")
                    .setSmallIcon(R.drawable.newyorktimesicon)
                    .setContentTitle(notification_title)
                    .setContentText("New articles available")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
            Log.i("ALARM", "Je sonne !");
        }

        private void retrieveSharedPreferences(){
            SharedPreferences mSharedPreferences = mContext.getSharedPreferences(NotificationActivity.SHARED_PREF_NOTIF, Context.MODE_PRIVATE);
            query = mSharedPreferences.getString(NotificationActivity.QUERY_SEARCH, null);
            category = mSharedPreferences.getString(NotificationActivity.CHECKBOX_STRING, null);
        }
    }