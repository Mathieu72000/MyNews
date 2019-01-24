package com.corroy.mathieu.mynews.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.TopStoriesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends Fragment implements AdapterView.OnItemClickListener{

    // Declare the RecyclerView
    @BindView(R.id.topStoriesRecyclerView)
    RecyclerView recyclerView;

    // Declare Subscription
    private Disposable disposable;

    private List<Result> mResultList;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);

        this.configureRecyclerView();

        this.executeHttpRequestWithRetrofit();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // Configure RecyclerView Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // Reset List
        this.mResultList = new ArrayList<>();
        // Create adapter passing list of article
        this.topStoriesAdapter = new TopStoriesAdapter(this.mResultList, Glide.with(this));
        // Attach the adapter to the recyclerView to populate item
        this.recyclerView.setAdapter(this.topStoriesAdapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Set a Divider
        this.recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Declare list of article (Article) & Adapter
        Result result = mResultList.get(position);
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("Url", result.getUrl());
        startActivity(intent);
    }

    // RETROFIT
    private void executeHttpRequestWithRetrofit() {
        String section = "home";
        this.disposable = MyNewsStreams.streamFetchTopStories(section, "pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh")
                .subscribeWith(new DisposableObserver<Article>(){
            @Override
            public void onNext(Article article) {
                updateUI(article.getResults());
                Log.e("TAG", "On Next");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error");
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete");
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUI(List<Result> news){
        mResultList.clear();
        mResultList.addAll(news);
        topStoriesAdapter.notifyDataSetChanged();
    }
}