package com.corroy.mathieu.mynews.View;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void updateWithNews(Result nYTimesResult, RequestManager glide){
        // ---------------- /SECTION/SUBSECTION/ --------------------------------
        String mSection;
        mSection = nYTimesResult.getSection();
        if(nYTimesResult.getSubsection() != null && !nYTimesResult.getSubsection().isEmpty()){
               mSection = mSection + " > " + nYTimesResult.getSubsection();
            }
        this.section.setText(mSection);
        // -------------------------- /DATE/ -----------------------------
        String parseDate = nYTimesResult.getPublishedDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(parseDate);
            parseDate = newFormat.format(date);
            this.date.setText(parseDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        // --------------------------- /TITLE/ ------------------------------
        this.title.setText(nYTimesResult.getTitle());

        // --------------------------- /MEDIA/ ------------------------------
        // TODO > Change drawable if no image available
        if (nYTimesResult.getMultimedia().size() > 0) {
            glide.load(nYTimesResult.getMultimedia().get(0).getUrl()).apply(new RequestOptions().fallback(R.drawable.ic_launcher_background)).into(image);
        } else {
            glide.clear(image);
            image.setImageResource(R.drawable.ic_launcher_background);
        }
            }
        }