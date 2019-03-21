package com.corroy.mathieu.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.corroy.mathieu.mynews.Controllers.Utils.ItemClickSupport;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.TopStoriesAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.topStoriesRecyclerView)
    RecyclerView recyclerView;

    // FOR DATA
    private Disposable disposable;

    // Declare new list of Result
    private List<Result> mResultList;

    // Declare the Adapter
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

        // Call the stream
        this.executeHttpRequestWithRetrofit();

        this.configureOnClickRecyclerView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // Configure RecyclerView Adapter, LayoutManager & glue it together, and add a separator
    private void configureRecyclerView() {
        this.mResultList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setHasFixedSize(true);
        this.topStoriesAdapter = new TopStoriesAdapter(mResultList);
        this.recyclerView.setAdapter(topStoriesAdapter);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    // Configure the RecyclerView to handle click on a news and display it in the webView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
            Result result = mResultList.get(position);
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("Url", result.getUrl());
            startActivity(intent);
        });
    }

    // Create a new subscriber
    private void executeHttpRequestWithRetrofit() {
        String section = "home";
        this.disposable = MyNewsStreams.streamFetchTopStories(section, "pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh")
                .subscribeWith(new DisposableObserver<Article>(){
            @Override
            public void onNext(Article article) {
                updateUI(article.getResults());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // Dispose subscription
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // Display the list in the RecyclerView and refresh the adapter
    private void updateUI(List<Result> news){
        mResultList.clear();
        mResultList.addAll(news);
        topStoriesAdapter.notifyDataSetChanged();
    }
}