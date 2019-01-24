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
import com.bumptech.glide.Glide;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
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

public class MostPopularFragment extends Fragment implements AdapterView.OnItemClickListener{

    // Declare the RecyclerView
    @BindView(R.id.mostPopularRecyclerView)
    RecyclerView mRecyclerView;

    // Declare Subscription
    private Disposable disposable;

    private List<Result> mResultList;
    private TopStoriesAdapter mostPopularAdapter;


    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance() {
        return new MostPopularFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);

        this.configureRecyclerView();

        this.executeHttpRequestWithRetrofit();

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureRecyclerView(){

        this.mResultList = new ArrayList<>();

        this.mostPopularAdapter = new TopStoriesAdapter(this.mResultList, Glide.with(this));

        this.mRecyclerView.setAdapter(this.mostPopularAdapter);

        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Declare list of article (Article) & Adapter
        Result result = mResultList.get(position);
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("Url", result.getUrl());
        startActivity(intent);
    }

    private void executeHttpRequestWithRetrofit(){
        int period = 7;
        this.disposable = MyNewsStreams.streamFetchMostPopular(period, "pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh")
                .subscribeWith(new DisposableObserver<Article>() {
            @Override
            public void onNext(Article article) {
                updateUI(article.getResults());
                Log.e("MOSTPOP", "Requête effectué !");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MOSTPOP", "Echec de la requête !");
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUI(List<Result> res){
        mResultList.clear();
        mResultList.addAll(res);
        mostPopularAdapter.notifyDataSetChanged();
    }
}