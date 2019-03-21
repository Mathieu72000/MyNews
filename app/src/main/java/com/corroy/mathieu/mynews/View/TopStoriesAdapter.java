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

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    private List<Result> mResultList;

    public TopStoriesAdapter(List<Result> mResultList){
        this.mResultList = mResultList;
    }

    @Override
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_top_stories_item, parent, false);
        return new TopStoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder viewHolder, int position) {
        viewHolder.updateWithNews(this.mResultList.get(position));
            }

    @Override
    public int getItemCount() {
        return this.mResultList.size();
    }
}