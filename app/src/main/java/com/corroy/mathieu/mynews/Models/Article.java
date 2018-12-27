package com.corroy.mathieu.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Article {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("new_desk")
    @Expose
    private String newDesk;
    @SerializedName("headline")
    @Expose
    private String typeOfMaterial;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;

    @SerializedName("media")

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getNewDesk() {
        return newDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSnippet() {
        return snippet;
    }
}
