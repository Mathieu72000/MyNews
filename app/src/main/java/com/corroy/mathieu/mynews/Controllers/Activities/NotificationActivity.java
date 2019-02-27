package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.corroy.mathieu.mynews.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.back_arrow_notification)
    ImageButton backArrow;
    @BindView(R.id.notification_switch)
    Switch mSwitch;
    @BindView(R.id.search__checkbox_arts)
    CheckBox checkboxArts;
    @BindView(R.id.search_checkbox_business)
    CheckBox checkboxBusiness;
    @BindView(R.id.search_checkbox_entrepreneurs)
    CheckBox checkboxEntrepreneurs;
    @BindView(R.id.search_checkbox_politics)
    CheckBox checkboxPolitics;
    @BindView(R.id.search_checkbox_sports)
    CheckBox checkboxSports;
    @BindView(R.id.search_checkbox_travel)
    CheckBox checkboxTravel;
    @BindView(R.id.search_term_notification)
    TextInputEditText searchTermNotification;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public static final String SHARED_PREF_NOTIF = "shared_pref_notif";
    public static final String QUERY_SEARCH = "query_search";
    public static final String CHECKBOX_STRING = "checkbox_string";
//    private String section = "type_of_material:News";
    private SharedPreferences.Editor editor = null;
    String section = "type_of_material:News";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        backArrow.setOnClickListener(v -> startActivity());
        treatmentNotification();
    }

    private void setAlarm() {
        Log.e("SWITCH", "activé");

        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MyAlarm.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        //Set the alarm to start at 12:00 a.m
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                , AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    public String checkboxVerification() {

        List<CheckBox> checkboxesList = new ArrayList<>();

        final CheckBox travel = findViewById(R.id.search_checkbox_travel);
        final CheckBox sports = findViewById(R.id.search_checkbox_sports);
        final CheckBox politics = findViewById(R.id.search_checkbox_politics);
        final CheckBox entrepreneurs = findViewById(R.id.search_checkbox_entrepreneurs);
        final CheckBox business = findViewById(R.id.search_checkbox_business);
        final CheckBox arts = findViewById(R.id.search__checkbox_arts);

        checkboxesList.clear();

        if (travel.isChecked()) {
            travel.setChecked(true);
            checkboxesList.add(travel);
        } if
        (sports.isChecked()) {
            sports.setChecked(true);
            checkboxesList.add(sports);
        } if
        (politics.isChecked()) {
            politics.setChecked(true);
            checkboxesList.add(politics);
        } if
        (entrepreneurs.isChecked()) {
            entrepreneurs.setChecked(true);
            checkboxesList.add(entrepreneurs);
        } if
        (business.isChecked()) {
            business.setChecked(true);
            checkboxesList.add(business);
        } if
        (arts.isChecked()) {
            arts.setChecked(true);
            checkboxesList.add(arts);
        }

        section = "type_of_material:News";

        int count = 0;

        for(int i = 0; i < checkboxesList.size(); i++) {
            if (checkboxesList.get(i).isChecked()) {
                if (count == 0) {
                    section = section + " AND news_desk:(" + checkboxesList.get(i).getText().toString();
                    count++;
                }
                else if (count > 0){
                    section = section + " OR " + checkboxesList.get(i).getText().toString();
                    count++;
                }
            }
        }

        if(count > 0) section = section + ")";
        return section;
    }

        public void treatmentNotification(){
       mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
        editor = getSharedPreferences(SHARED_PREF_NOTIF, MODE_PRIVATE).edit();
        if (isChecked) {
            Log.e("EDITTEXT", checkboxVerification());
            Log.e("EDITTEXT", searchTermNotification.getText().toString());
            if (checkboxVerification().equals("") && searchTermNotification.getText().toString().equals("")){
                mSwitch.setChecked(false);
                Toast.makeText(this, "Please enter a search and select at least one category", Toast.LENGTH_SHORT).show();
            } else if (checkboxVerification().isEmpty()){
                mSwitch.setChecked(false);
                Toast.makeText(this, "Please select at least one category to search", Toast.LENGTH_SHORT).show();
            } else if (searchTermNotification.getText().toString().equals("")){
                mSwitch.setChecked(false);
                Toast.makeText(this, "Please enter a search", Toast.LENGTH_SHORT).show();
            } else
                editor.putString(QUERY_SEARCH, searchTermNotification.getText().toString());
                editor.putString(CHECKBOX_STRING, checkboxVerification());
                editor.apply();
            mSwitch.setChecked(true);
            setAlarm();
        }
        if (!isChecked) {
            mSwitch.setChecked(false);
            alarmMgr.cancel(alarmIntent);
            Log.e("SWITCH", "désactivé");
        }
    });
}
    private void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}