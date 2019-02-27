package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MyAlarm extends BroadcastReceiver {

    private Context context;
    private Search v;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.executeHttpRequestWithRetrofit();
        }

        private void executeHttpRequestWithRetrofit(){

            SharedPreferences mSharedPreferences = context.getSharedPreferences(NotificationActivity.SHARED_PREF_NOTIF, Context.MODE_PRIVATE);
            String query = mSharedPreferences.getString(NotificationActivity.QUERY_SEARCH, "");
            String category = mSharedPreferences.getString(NotificationActivity.CHECKBOX_STRING, "");
            Log.i("SHAREDPREF", query);
            Log.i("SHAREDPREF", category);
            Disposable disposable = MyNewsStreams.streamFetchSearch(query, "20190226", "20190227", category).subscribeWith(new DisposableObserver<Search>() {
                @Override
                public void onNext(Search value) {
                    Log.e("NOTIF", "on Next");
                    v = value;
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("NOTIF", "on Error");
                }

                @Override
                public void onComplete() {
                    Log.e("NOTIF", "on Complete");
                    Log.i("GETRESPONSE", String.valueOf(v.getResponse().getDocs().size()));
                    if(v.getResponse().getDocs().size() > 0) {
                        createNotification(v.getResponse().getDocs().size());
                    }
                }
            });
        }

        private void createNotification(int numberArticles) {

            String channelId = "channel1";
            CharSequence channelName = "Article channel1";

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            String notification_title = "MyNews";
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("New articles available");
            inboxStyle.addLine("There is " + numberArticles + " article available");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.newyorktimesicon)
                    .setContentTitle(notification_title)
                    .setContentText("New articles available")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(1, mBuilder.build());
            }
        }

        private Date yesterday(){
         final Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, -1);
         return cal.getTime();
        }

        private String getYesterdayDateString(){
          DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
         return dateFormat.format(yesterday());
        }

        private String today(){
          Date currentime = Calendar.getInstance().getTime();
               DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
               return dateFormat.format(currentime);
        }
    }