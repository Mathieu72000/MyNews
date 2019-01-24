package com.corroy.mathieu.mynews.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.SearchAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    // Declare the RecyclerView
    @BindView(R.id.searchRecyclerView)
    RecyclerView recyclerView;

    private SearchAdapter searchAdapter;
    private String query = "";
    private String start_date = "";
    private String end_date = "";
    private String section = "";
    private List<Result> mResultList;
    private Disposable disposable;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            start_date = bundle.getString("sDate", null);
            end_date = bundle.getString("eDate", null);
        }

        this.configureRecyclerView();

        this.executeHttpRequestWithRetrofit();

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        disposeWhenDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Declare list of article (Article) & Adapter
        Result result = mResultList.get(position);
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("Url", result.getUrl());
        startActivity(intent);
    }

    private void configureRecyclerView() {
        // 3.1 Reset List
        this.mResultList = new ArrayList<>();
        // 3.2 Create adapter passing list of article
        this.searchAdapter = new SearchAdapter(this.mResultList);
        // 3.3 Attach the adapter to the recyclerView to populate item
        this.recyclerView.setAdapter(this.searchAdapter);
        // 3.4 Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void executeHttpRequestWithRetrofit(){
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        this.disposable = MyNewsStreams.streamFetchSearch(query, start_date, end_date, section).subscribeWith(new DisposableObserver<Result>() {
            @Override
            public void onNext(Result value) {
                updateUISearch(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUISearch(Result results){
        mResultList.clear();
        mResultList.add(results);
        searchAdapter.notifyDataSetChanged();
    }
}
