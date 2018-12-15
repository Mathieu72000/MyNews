package com.corroy.mathieu.mynews;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //FOR FRAGMENTS
    // Declare fragment handled by Navigation Drawer
    private Fragment fragmentTopStories;
    private Fragment fragmentMostPopular;
    private Fragment fragmentPolitics;

    //FOR DATA
    // Identify each fragment with a number
    private static final int FRAGMENT_TOP_STORIES = 0;
    private static final int FRAGMENT_MOST_POPULAR = 1;
    private static final int FRAGMENT_POLITICS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.showFirstFragment();
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

        // Handle Navigation Item Click
        // Show Fragment after user clicked on a menu item
        switch (id){
            case R.id.topStories:
                this.showFragment(FRAGMENT_TOP_STORIES);
                break;
            case R.id.mostPopular:
                this.showFragment(FRAGMENT_MOST_POPULAR);
                break;
            case R.id.politics:
                this.showFragment(FRAGMENT_POLITICS);
                break;
            default:
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

    // -----------
    // FRAGMENTS
    // -----------

    // Show fragment according an Identifier
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_TOP_STORIES:
                this.showTopStoriesFragment();
                break;
            case FRAGMENT_MOST_POPULAR:
                this.showMostPopularFragment();
                break;
            case FRAGMENT_POLITICS:
                this.showPoliticsFragment();
                break;
            default:
                break;
        }
    }

    // Create each fragment page and show it
    private void showTopStoriesFragment(){
        if(this.fragmentTopStories == null) this.fragmentTopStories = TopStoriesFragment.newInstance();
        this.startTransactionFragment(this.fragmentTopStories);
    }

    private void showMostPopularFragment(){
        if (this.fragmentMostPopular == null) this.fragmentMostPopular = MostPopularFragment.newInstance();
        this.startTransactionFragment(this.fragmentMostPopular);
    }

    private void showPoliticsFragment(){
        if (this.fragmentPolitics == null) this.fragmentPolitics = PoliticsFragment.newInstance();
        this.startTransactionFragment(this.fragmentPolitics);
    }

    // Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.activityMainFrameLayout, fragment).commit();
        }
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activityMainFrameLayout);
        if (visibleFragment == null){
            this.showFragment(FRAGMENT_TOP_STORIES);
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}

