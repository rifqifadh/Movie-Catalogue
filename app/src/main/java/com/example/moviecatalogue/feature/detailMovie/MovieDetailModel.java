package com.example.moviecatalogue.feature.detailMovie;

import android.util.Log;

import com.example.moviecatalogue.model.Genres;
import com.example.moviecatalogue.model.MovieDetailItem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;

public class MovieDetailModel implements DetailMovieContract.Model {

    private final String TAG = "Detail Model";

    @Override
    public void getDetail(final OnFinishedListener onFinishedListener, int movieId) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieDetailItem> call = apiService.getMovieDetails(movieId, API_KEY);
        Log.d(TAG, "link: " + call);
        call.enqueue(new Callback<MovieDetailItem>() {
            @Override
            public void onResponse(Call<MovieDetailItem> call, Response<MovieDetailItem> response) {
                MovieDetailItem movieDetailItem = response.body();
                onFinishedListener.onFinished(movieDetailItem);
            }

            @Override
            public void onFailure(Call<MovieDetailItem> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
