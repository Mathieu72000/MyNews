package com.corroy.mathieu.mynews.Controllers.Fragments;

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

import com.bumptech.glide.Glide;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Controllers.Utils.ItemClickSupport;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Doc;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.SearchAdapter;
import com.corroy.mathieu.mynews.View.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PoliticsFragment extends Fragment {

    // Declare Recycleriew
    @BindView(R.id.politicsRecyclerView)
    RecyclerView mRecyclerView;

    private Disposable mDisposable;

    // Declare List
    private List<Doc> mDocList;
    private SearchAdapter politicsAdapter;


    public PoliticsFragment() {
        // Required empty public constructor
    }

    public static PoliticsFragment newInstance() {
        return new PoliticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_politics, container, false);
        ButterKnife.bind(this, view);

        this.configureRecyclerView();

        this.executeHttpRequestWithRetrofit();

        this.configureOnClickRecyclerView();

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        disposeWhenDestroy();
    }

    private void configureRecyclerView() {
        this.mDocList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        this.mRecyclerView.setLayoutManager(manager);
        this.mRecyclerView.setHasFixedSize(true);
        this.politicsAdapter = new SearchAdapter(mDocList, Glide.with(this));
        this.mRecyclerView.setAdapter(politicsAdapter);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Doc doc = mDocList.get(position);
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("Url", doc.getWebUrl());
                    startActivity(intent);
                });
    }

    private void executeHttpRequestWithRetrofit(){
        this.mDisposable = MyNewsStreams.streamFetchPolitics()
                .subscribeWith(new DisposableObserver<Search>() {
                    @Override
                    public void onNext(Search search) {
                        updateUI(search.getResponse().getDocs());
                        Log.e("POLITICS", "ON NEXT");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("POLITICS", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("POLITICS", "On Complete");
                    }
                });
    }

    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    private void updateUI(List<Doc> res){
        mDocList.clear();
        mDocList.addAll(res);
        politicsAdapter.notifyDataSetChanged();
    }
}