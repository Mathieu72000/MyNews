package com.corroy.mathieu.mynews.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Controllers.WebViewActivity;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.TopStoriesAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends Fragment implements AdapterView.OnItemClickListener {

    // Declare the RecyclerView
    @BindView(R.id.topStoriesRecyclerView)
    RecyclerView recyclerView;

    // Declare Subscription
    private Disposable disposable;

    // Declare list of article (Article) & Adapter
    private Article article;
    private List<Article> articleList;
    private TopStoriesAdapter adapter;

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
        // 3.1 Reset List
        this.articleList = new ArrayList<>();
        // 3.2 Create adapter passing list of article
        this.adapter = new TopStoriesAdapter(this.articleList);
        // 3.3 Attach the adapter to the recyclerView to populate item
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        article = articleList.get(position);
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("Url", article.getUrl());
        startActivity(intent);
    }

    // RETROFIT
    private void executeHttpRequestWithRetrofit() {
        String section = "home";
        this.disposable = MyNewsStreams.streamFetchTopStories(section, "b8ce4364a4f24d9e8159ad992ca61a45").subscribeWith(new DisposableObserver<Article>(){
            @Override
            public void onNext(Article article) {
                updateUI(article);
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

    private void updateUI(Article res){
        articleList.clear();
        articleList.add(res);
        adapter.notifyDataSetChanged();
    }
}