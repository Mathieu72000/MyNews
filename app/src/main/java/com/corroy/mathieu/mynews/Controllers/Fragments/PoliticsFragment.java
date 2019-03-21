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
import com.bumptech.glide.Glide;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
import com.corroy.mathieu.mynews.Controllers.Utils.ItemClickSupport;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Doc;
import com.corroy.mathieu.mynews.Models.Search;
import com.corroy.mathieu.mynews.R;
import com.corroy.mathieu.mynews.View.SearchAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PoliticsFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.politicsRecyclerView)
    RecyclerView mRecyclerView;

    // FOR DATA
    private Disposable mDisposable;

    // Declare new list of Doc
    private List<Doc> mDocList;

    // Declare the adapter
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

        // Call the stream
        this.executeHttpRequestWithRetrofit();

        this.configureOnClickRecyclerView();

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        disposeWhenDestroy();
    }

    // Configure RecyclerView Adapter, LayoutManager & glue it together, and add a separator
    private void configureRecyclerView() {
        this.mDocList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        this.mRecyclerView.setLayoutManager(manager);
        this.mRecyclerView.setHasFixedSize(true);
        this.politicsAdapter = new SearchAdapter(mDocList, Glide.with(this));
        this.mRecyclerView.setAdapter(politicsAdapter);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    // Configure the RecyclerView to handle click on a news and display it in the webView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Doc doc = mDocList.get(position);
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("Url", doc.getWebUrl());
                    startActivity(intent);
                });
    }

    // Create a new subscriber
    private void executeHttpRequestWithRetrofit(){
        this.mDisposable = MyNewsStreams.streamFetchPolitics()
                .subscribeWith(new DisposableObserver<Search>() {
                    @Override
                    public void onNext(Search search) {
                        updateUI(search.getResponse().getDocs());
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
    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    // Display the list in the RecyclerView and refresh the adapter
    private void updateUI(List<Doc> res){
        mDocList.clear();
        mDocList.addAll(res);
        politicsAdapter.notifyDataSetChanged();
    }
}