package com.corroy.mathieu.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("material_type_facet")
    @Expose
    private String materialTypeFacet;
    @SerializedName("kicker")
    @Expose
    private String kicker;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    public String getSection () {
            return section;
        }

        public void setSection (String section){
            this.section = section;
        }

        public String getSubsection () {
            return subsection;
        }

        public String getTitle () {
            return title;
        }

        public String getUrl () {
            return url;
        }

        public String getPublishedDate () {
            return publishedDate;
        }

        public List<Multimedium> getMultimedia () {
            return multimedia;
        }

        public List<Medium> getMedia() {
        return media;
    }
    }