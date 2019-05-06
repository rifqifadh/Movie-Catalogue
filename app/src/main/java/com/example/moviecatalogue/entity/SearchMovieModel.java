package com.example.moviecatalogue.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMovieModel {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("results")
    private List<MovieItem> movieItems;

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<MovieItem> getMovieItems() {
        return movieItems;
    }
}
