package com.corroy.mathieu.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MyAlarm extends BroadcastReceiver {

    private Context context;
    private Search v;
    private String query;
    private String category;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.executeHttpRequestWithRetrofit();
        }

        // Send a request when the alarm ring
        private void executeHttpRequestWithRetrofit(){
            this.retrieveSharedPreferences();
            Disposable disposable = MyNewsStreams.streamFetchSearch(query, getYesterdayDateString(), today(), category).subscribeWith(new DisposableObserver<Search>() {
                @Override
                public void onNext(Search value) {
                    v = value;
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                    if(v.getResponse().getDocs().size() > 0) {
                        createNotification(v.getResponse().getDocs().size());
                    }
                }
            });
        }

        // Create a new Notification channel
        private void createNotification(int numberArticles) {

            String channelId = "channel1";
            CharSequence channelName = "Article channel1";

            Intent intent = new Intent(context, SearchResult.class);
            intent.putExtra("query", query);
            intent.putExtra("start_date", getYesterdayDateString());
            intent.putExtra("end_date", today());
            intent.putExtra("section", category);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            String notification_title = "MyNews";
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("New articles available");
            inboxStyle.addLine("There is " + numberArticles + " articles available");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.nytnotification)
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

        // Retrieve the choice of checkbox(es), and keyword(s) of the user
        private void retrieveSharedPreferences(){
            SharedPreferences mSharedPreferences = context.getSharedPreferences(NotificationActivity.SHARED_PREF_NOTIF, Context.MODE_PRIVATE);
            query = mSharedPreferences.getString(NotificationActivity.QUERY_SEARCH, "");
            category = mSharedPreferences.getString(NotificationActivity.CHECKBOX_STRING, "");
        }

        // Retrieve the date of yesterday for the request parameters
        private Date yesterday(){
         final Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, -1);
         return cal.getTime();
        }

        // Format the date of yesterday()
        private String getYesterdayDateString(){
          @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
         return dateFormat.format(yesterday());
        }

        // Retrieve the date of today for the request parameters
        private String today(){
          Date currentTime = Calendar.getInstance().getTime();
               @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
               return dateFormat.format(currentTime);
        }
    }