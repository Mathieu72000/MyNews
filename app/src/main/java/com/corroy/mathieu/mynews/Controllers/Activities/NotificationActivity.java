package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import com.corroy.mathieu.mynews.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    private AlarmManager alarmMgr = null;
    private PendingIntent alarmIntent;
    public static final String SHARED_PREF_NOTIF = "shared_pref_notif";
    public static final String QUERY_SEARCH = "query_search";
    public static final String SWITCH_ACTIVATED = "switch_activated";
    public static final String CHECKBOX_STRING = "checkbox_string";
    private SharedPreferences.Editor editor;
    private List<CheckBox> checkboxesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        backArrow.setOnClickListener(v -> startActivity());
        restoreSharedPreferences();
        treatmentNotification();
    }

    public void checkboxVerification() {

        editor = getSharedPreferences(SHARED_PREF_NOTIF, MODE_PRIVATE).edit();

        checkboxesList.clear();

        if (checkboxTravel.isChecked()) {
            checkboxTravel.setChecked(true);
            checkboxesList.add(checkboxTravel);
        } if
        (checkboxSports.isChecked()) {
            checkboxSports.setChecked(true);
            checkboxesList.add(checkboxSports);
        } if
        (checkboxPolitics.isChecked()) {
            checkboxPolitics.setChecked(true);
            checkboxesList.add(checkboxPolitics);
        } if
        (checkboxEntrepreneurs.isChecked()) {
            checkboxEntrepreneurs.setChecked(true);
            checkboxesList.add(checkboxEntrepreneurs);
        } if
        (checkboxBusiness.isChecked()) {
            checkboxBusiness.setChecked(true);
            checkboxesList.add(checkboxBusiness);
        } if
        (checkboxArts.isChecked()) {
            checkboxArts.setChecked(true);
            checkboxesList.add(checkboxArts);
        }

        String section = "type_of_material:News";

        String categories = "";
        Log.i("CHECKBOXSIZE", String.valueOf(checkboxesList.size()));
        for(int i = 0; i < checkboxesList.size(); i++) {
            Log.i("INDEX I", String.valueOf(i));
                if (i == 0) {
                    editor.putString(CHECKBOX_STRING, checkboxesList.get(i).getText().toString());
                    section = section + " AND news_desk:(" + checkboxesList.get(i).getText().toString();
                    categories += checkboxesList.get(i).getText().toString();
                }
                else {
                    section = section + " OR " + checkboxesList.get(i).getText().toString();
                    categories += "|" + checkboxesList.get(i).getText().toString();
                }
            }
                editor.putString(CHECKBOX_STRING, categories);
                editor.apply();
                if (checkboxesList.size() > 0) section = section + ")";

    }

        public void treatmentNotification(){
       mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
           editor = getSharedPreferences(SHARED_PREF_NOTIF, MODE_PRIVATE).edit();
           Log.i("CHECKBOXSIZE", String.valueOf(checkboxesList.size()));
           checkboxVerification();
           if (isChecked) {
               if (searchTermNotification.getText().toString().isEmpty()) {
                   Toast.makeText(this, "Please enter keyword to search", Toast.LENGTH_SHORT).show();
                   mSwitch.setChecked(false);

               } else if (checkboxesList.size() == 0) {
                   mSwitch.setChecked(false);
                   Toast.makeText(this, "Please select at least one category to search", Toast.LENGTH_SHORT).show();

               } else {
                   editor.putString(QUERY_SEARCH, searchTermNotification.getText().toString());
                   editor.putString(SWITCH_ACTIVATED, "true");
                   editor.apply();
                   mSwitch.setChecked(true);
                   restoreSharedPreferences();
                   setAlarm();
                   Log.e("SWITCH", "activé");
               }
           } else {
               editor.clear().apply();
               mSwitch.setChecked(false);
               cancelAlarm();
               Log.e("SWITCH", "désactivé");
             }
          });
        }
    private void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setAlarm() {

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

    public void cancelAlarm(){
        if(alarmMgr != null && alarmIntent != null)
        alarmMgr.cancel(alarmIntent);
    }

    private void restoreSharedPreferences() {
        SharedPreferences mSharedPreferences = getSharedPreferences(SHARED_PREF_NOTIF, Context.MODE_PRIVATE);
        String query = mSharedPreferences.getString(QUERY_SEARCH, "");
        String switchBtn = mSharedPreferences.getString(SWITCH_ACTIVATED, "");
        String category = mSharedPreferences.getString(CHECKBOX_STRING, "");

        this.searchTermNotification.setText(query);
        Log.i("SWITCHBTN", String.valueOf(switchBtn.equals("true")));

        if (switchBtn.equals("true")) {
            this.mSwitch.setChecked(true);
            Log.i("CATEGORY", category);
            String[] array = category.split("\\|");

            for (int i = 0; i < array.length; i++) {
                Log.i("CATEGORY", array[i]);
                if (array[i].equals("Politics")) {
                    checkboxPolitics.setChecked(true);
                }
                if (array[i].equals("Arts")) {
                    checkboxArts.setChecked(true);
                }
                if (array[i].equals("Entrepreneurs")) {
                    checkboxEntrepreneurs.setChecked(true);
                }
                if (array[i].equals("Travel")) {
                    checkboxTravel.setChecked(true);
                }
                if (array[i].equals("Sports")) {
                    checkboxSports.setChecked(true);
                }
                if (array[i].equals("Business")) {
                    checkboxBusiness.setChecked(true);
                }
            }
        }
    }
}