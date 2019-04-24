package com.example.moviecatalogue.feature.detailTV;

import com.example.moviecatalogue.model.TVDetailtem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;

public class TvDetailModel implements DetailTvContract.Model{

    @Override
    public void getDetail(final OnFinishedListener onFinishedListener, int tvId) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TVDetailtem> call = apiService.getTVDetails(tvId, API_KEY);
        call.enqueue(new Callback<TVDetailtem>() {
            @Override
            public void onResponse(Call<TVDetailtem> call, Response<TVDetailtem> response) {
                TVDetailtem tvDetailtem = response.body();
                onFinishedListener.onFinished(tvDetailtem);
            }

            @Override
            public void onFailure(Call<TVDetailtem> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
