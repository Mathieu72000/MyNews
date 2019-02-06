package com.corroy.mathieu.mynews.View;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.corroy.mathieu.mynews.Models.Doc;
import com.corroy.mathieu.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_search_item_section)
    TextView sectionSearch;
    @BindView(R.id.fragment_search_item_title)
    TextView titleSearch;
    @BindView(R.id.fragment_search_item_date)
    TextView dateSearch;
    @BindView(R.id.fragment_search_item_image)
    ImageView imageSearch;


    public SearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithResult(Doc doc, RequestManager glide){
        // ---------------- /SECTION/ --------------------------------
        String section = doc.getSectionName();
        if (TextUtils.isEmpty(section)){
            section = doc.getNewsDesk();
            if(section == null || section.equals("None")){
                section = "News";
            }
        }
        this.sectionSearch.setText(section);
        // -------------------------- /DATE/ -----------------------------
        String parseDate = doc.getPubDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(parseDate);
            parseDate = newFormat.format(date);
            this.dateSearch.setText(parseDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        // --------------------------- /TITLE/ ------------------------------
        this.titleSearch.setText(doc.getSnippet());

        // --------------------------- /MEDIA/ ------------------------------
        }
        }

