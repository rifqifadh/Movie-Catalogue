package com.example.moviecatalogue.network;

import com.example.moviecatalogue.entity.MovieDetailItem;
import com.example.moviecatalogue.entity.MovieListResponse;
import com.example.moviecatalogue.entity.SearchMovieModel;
import com.example.moviecatalogue.entity.SearchTvModel;
import com.example.moviecatalogue.entity.TVDetailtem;
import com.example.moviecatalogue.entity.TvListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieListResponse> getMovieDsicover(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("discover/tv")
    Call<TvListResponse> getTvDiscover(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/{movie_id}")
    Call<MovieDetailItem> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<TVDetailtem> getTVDetails(@Path("tv_id") int tvId, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<SearchMovieModel> getSearchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/tv")
    Call<SearchTvModel> getSearchTv(@Query("api_key") String apiKey, @Query("query") String query);
}
