package com.example.moviecatalogue.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvListResponse {

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TvSeriesItem> getResults() {
        return results;
    }

    public void setResults(List<TvSeriesItem> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<TvSeriesItem> results;

    @SerializedName("tatal_results")
    private int totalResults;
}
