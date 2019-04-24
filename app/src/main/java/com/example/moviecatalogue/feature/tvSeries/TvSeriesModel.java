package com.example.moviecatalogue.feature.tvSeries;

import android.util.Log;

import com.example.moviecatalogue.feature.tvSeries.TvSeriesContract;
import com.example.moviecatalogue.model.TvListResponse;
import com.example.moviecatalogue.model.TvSeriesItem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;

public class TvSeriesModel implements TvSeriesContract.Model {

    private final String TAG = "MovieListModel";

    @Override
    public void getTvSeriesList(final OnFinishedListener onFinishedListener, int pageNo) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TvListResponse> call = apiService.getTvDiscover(API_KEY, pageNo);
        call.enqueue(new Callback<TvListResponse>() {
            @Override
            public void onResponse(Call<TvListResponse> call, Response<TvListResponse> response) {
                List<TvSeriesItem> tvSeriesItems = response.body().getResults();
                Log.d(TAG, "Tv Series: " + tvSeriesItems);
                onFinishedListener.onFinished(tvSeriesItems);
            }

            @Override
            public void onFailure(Call<TvListResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
