package com.corroy.mathieu.mynews.Controllers;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        // Find the ViewPager and configure it with the PagerAdapter
        viewPager = findViewById(R.id.activityViewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Find the TabLayout and configure it in the ViewPager to handle Tabs
        TabLayout tabs = findViewById(R.id.activityMainTabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
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
        this.toolbar = findViewById(R.id.activityMainToolbar);
        setSupportActionBar(toolbar);
    }

    // Configure DrawerLayout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activityMainDrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
       this.navigationView = findViewById(R.id.activityMainNavigationView);
       navigationView.setNavigationItemSelectedListener(this);
    }
}