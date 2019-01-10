package com.corroy.mathieu.mynews.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.R;
import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    private List<Article> article;

    public TopStoriesAdapter(List<Article> article){
        this.article = article;
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
        final Article mArticle = article.get(position);
        viewHolder.title.setText(mArticle.getTitle());
        viewHolder.section.setText(mArticle.getSection());
        viewHolder.date.setText(mArticle.getPublishedDate());
        viewHolder.image.setImageDrawable((Drawable) mArticle.getMultimedia());
    }

    @Override
    public int getItemCount() {
        return this.article.size();
    }
}