package com.corroy.mathieu.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.corroy.mathieu.mynews.R;
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
