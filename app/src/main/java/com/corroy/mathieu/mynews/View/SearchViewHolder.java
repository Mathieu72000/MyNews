package com.corroy.mathieu.mynews.View;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.corroy.mathieu.mynews.Models.Doc;
import com.corroy.mathieu.mynews.Models.Multimedium;
import com.corroy.mathieu.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
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
        List<Multimedium> multimediumList;
        multimediumList = doc.getMultimedia();
        if(multimediumList.isEmpty()){
            this.imageSearch.setImageResource(R.drawable.nytlogo);
        } else {
            for (int i = 0; i < multimediumList.size(); i++){
                if(multimediumList.get(i).getSubtype().equals("thumbnail")){
                    String url = "https://static01.nyt.com/" + multimediumList.get(i).getUrl();
                    glide.load(url).into(this.imageSearch);
                }
             }
         }
    }
}