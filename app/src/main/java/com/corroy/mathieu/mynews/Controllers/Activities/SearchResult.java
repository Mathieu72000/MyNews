package com.corroy.mathieu.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import com.corroy.mathieu.mynews.Controllers.Fragments.SearchFragment;
import com.corroy.mathieu.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResult extends AppCompatActivity {

    @BindView(R.id.back_arrow) ImageButton backArrow;
    private SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        // Configure the button to get back
        backArrow.setOnClickListener(v -> startActivity());

        // Retrieve the information from SearchActivity
        Intent intent = getIntent();
        String sDate = intent.getStringExtra("start_date");
        String eDate = intent.getStringExtra("end_date");
        String query = intent.getStringExtra("query");
        String section = intent.getStringExtra("section");

        if(savedInstanceState == null){
            this.configureSearchFragment(query, sDate, eDate, section);
        } else {
            searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        }
    }

    // Put information into a Bundle and add it on a Fragment to display them
    private void configureSearchFragment(String query, String sDate, String eDate, String section){
        if(searchFragment == null) {
            searchFragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("query", query);
            bundle.putString("sDate", sDate);
            bundle.putString("eDate", eDate);
            bundle.putString("section", section);
            searchFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, searchFragment)
                    .commit();
            }
        }

        private void startActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        }
    }