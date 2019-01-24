package com.corroy.mathieu.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.PagerAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // FOR DESIGN
    @BindView(R.id.activityMainToolbar) Toolbar toolbar;
    @BindView(R.id.activityMainDrawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.activityMainNavigationView) NavigationView navigationView;
    @BindView(R.id.activityViewPager) ViewPager viewPager;
    @BindView(R.id.activityMainTabs) TabLayout tabLayout;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //---------------------------------------------------------------------------------------------
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);

        mAlarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MyAlarm.class);
        mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                , AlarmManager.INTERVAL_DAY, mPendingIntent);

        //---------------------------------------------------------------------------------------------

        this.configureToolbar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        // Set the PagerAdapter and glue it with the ViewPager
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Glue the TabLayout and the ViewPager together
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onBackPressed(){
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Handle Navigation Option Item Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.notifications:
                Toast.makeText(this, "Encoding notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this, "Encoding help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "Encoding about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        // Set current location in the ViewPager to handle the position of the fragments
        switch (id){
            case R.id.topStories:
                viewPager.setCurrentItem(0);
                break;
            case R.id.mostPopular:
                viewPager.setCurrentItem(1);
                break;
            case R.id.politics:
                viewPager.setCurrentItem(2);
                break;
            default:
                viewPager.setCurrentItem(0);
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ----------------
    // CONFIGURATION
    // ----------------

    // Configure Toolbar
    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My News");
    }

    // Configure DrawerLayout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
       navigationView.setNavigationItemSelectedListener(this);
    }
}