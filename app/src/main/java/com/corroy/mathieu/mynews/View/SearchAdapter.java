package com.corroy.mathieu.mynews.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<Result> mResultList;

    public SearchAdapter(List<Result> mResultList){
        this.mResultList = mResultList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =  parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int position) {
        searchViewHolder.updateWithResult(this.mResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mResultList.size();
    }

    public Result getResult(int position){
        return this.mResultList.get(position);
    }
}
