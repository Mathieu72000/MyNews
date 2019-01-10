package com.corroy.mathieu.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Multimedium;
import com.corroy.mathieu.mynews.R;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {

    // DESIGN
    @BindView(R.id.fragment_main_item_section)
    TextView section;
    @BindView(R.id.fragment_main_item_title)
    TextView title;
    @BindView(R.id.fragment_main_item_date)
    TextView date;
    @BindView(R.id.fragment_main_item_image)
    ImageView image;


    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
