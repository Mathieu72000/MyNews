package com.corroy.mathieu.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<Result> getResults() {
        return results;
    }
}