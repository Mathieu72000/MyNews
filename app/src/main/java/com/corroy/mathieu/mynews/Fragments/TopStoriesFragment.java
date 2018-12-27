package com.corroy.mathieu.mynews.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.TopStoriesAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesFragment extends Fragment {

  // Declare the RecyclerView
  @BindView(R.id.topStoriesRecyclerView) RecyclerView recyclerView;

  // Declare list of article (Article) & Adapter
  private List<Article> article;
  private TopStoriesAdapter topStoriesAdapter;

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);

        this.configureRecyclerView();

        return view;
    }

    //3 Configure RecyclerView Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // 3.1 Reset List
        this.article = new ArrayList<>();
        // 3.2 Create adapter passing list of article
        this.topStoriesAdapter = new TopStoriesAdapter(this.article);
        // 3.3 Attach the adapter to the recyclerView to populate item
        this.recyclerView.setAdapter(this.topStoriesAdapter);
        // 3.4 Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}