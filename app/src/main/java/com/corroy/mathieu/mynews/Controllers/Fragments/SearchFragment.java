package com.corroy.mathieu.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.corroy.mathieu.mynews.Controllers.Utils.ItemClickSupport;
import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Controllers.Activities.WebViewActivity;
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

public class SearchFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.searchRecyclerView)
    RecyclerView recyclerView;

    // FOR DATA
    private Disposable disposable;

    // Declare new list of Doc
    private List<Doc> mDocList;

    // Declare the Adapter
    private SearchAdapter searchAdapter;

    private String query = "";
    private String start_date = "";
    private String end_date = "";
    private String section = "";
    private ProgressDialog mProgressDialog;

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
            query = bundle.getString("query", null);
            start_date = bundle.getString("sDate", null);
            end_date = bundle.getString("eDate", null);
            section = bundle.getString("section", "type_of_material:News");
        }

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
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setHasFixedSize(true);
        this.searchAdapter = new SearchAdapter(mDocList, Glide.with(this));
        this.recyclerView.setAdapter(searchAdapter);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    // Configure the RecyclerView to handle click on a news and display it in the webView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Doc doc = mDocList.get(position);
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("Url", doc.getWebUrl());
                    startActivity(intent);
                });
    }

    // Create a new subscriber
    private void executeHttpRequestWithRetrofit(){
        showProgressDialog();
        this.disposable = MyNewsStreams.streamFetchSearch(query, start_date, end_date, section)
                .subscribeWith(new DisposableObserver<Search>() {
            @Override
            public void onNext(Search value) {
                if(value.getResponse().getDocs().isEmpty()){
                    Toast.makeText(getActivity(), "This search does not return any results", Toast.LENGTH_LONG).show();
                } else {
                    updateUISearch(value.getResponse().getDocs());
                    dismissProgressDialog();
                }
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
        if (this.disposable != null && this.disposable.isDisposed()) this.disposable.dispose();
    }

    // Display the list in the RecyclerView and refresh the adapter
    private void updateUISearch(List<Doc> docs){
        mDocList.clear();
        mDocList.addAll(docs);
        if(mDocList.size() == 0){
            Toast.makeText(getContext(), "No article found with current parameters", Toast.LENGTH_LONG).show();
        }
        searchAdapter.notifyDataSetChanged();
    }

    // Create a new ProgressDialog for loading screen
    public void showProgressDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage("Wait a moment...");
        mProgressDialog.show();
    }

    // Dismiss the progress dialog when loading is finished
    public void dismissProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
            }
        }
    }