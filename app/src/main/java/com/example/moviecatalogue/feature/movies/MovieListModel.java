package com.example.moviecatalogue.feature.movies;

import com.example.moviecatalogue.model.MovieItem;
import com.example.moviecatalogue.model.MovieListResponse;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;

public class MovieListModel implements MovieListContract.Model {

    private final String TAG = "MovieListModel";


    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener, int pageNo) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieListResponse> call = apiService.getMovieDsicover(API_KEY, pageNo);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                List<MovieItem> movieItems = response.body().getResults();

                if (response.body() != null) {
                    onFinishedListener.onFinished(movieItems);
                }
//                Log.d(TAG, "movieList: " + movieItems);
//                Log.d(TAG, "LINK: " + apiService);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
