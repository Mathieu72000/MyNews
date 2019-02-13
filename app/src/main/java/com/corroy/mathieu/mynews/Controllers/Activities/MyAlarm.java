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

import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MyAlarm extends BroadcastReceiver {

    private static final String QUERY = "QUERY";
    private static final String FILTER_QUERY = "FILTER_QUERY";
    private String section;
    private Context mContext;
    private Disposable mDisposable;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        }

        private void executeHttpRequestWithRetrofit(String queryTerm, List<String>categoryList){
        section = categoryList.toString();
            this.mDisposable = MyNewsStreams.streamFetchSearch(queryTerm, null, null, section).subscribeWith(new DisposableObserver<Search>() {
                @Override
                public void onNext(Search value) {
                    if(!(value.getResponse().getDocs().isEmpty())){
                        sendNotification(queryTerm, categoryList);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

        private void sendNotification(String queryTerm, List<String> categoryList){
            if (Build.VERSION.SDK_INT < 26) {
                return;
            }
            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);

        Intent intent = new Intent(mContext, SearchResult.class);
        intent.putExtra(QUERY, queryTerm);
        intent.putStringArrayListExtra(FILTER_QUERY, (ArrayList<String>)categoryList);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            String notification_title = "MyNews";
            String notification_text = "We have some news for you !";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default")
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