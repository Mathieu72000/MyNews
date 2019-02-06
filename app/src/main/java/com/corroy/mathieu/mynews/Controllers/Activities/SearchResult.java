package com.corroy.mathieu.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.corroy.mathieu.mynews.Controllers.Fragments.SearchFragment;
import com.corroy.mathieu.mynews.R;

public class SearchResult extends AppCompatActivity {

    private SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

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
}