package com.example.moviecatalogue.feature.tvSeries;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviecatalogue.MovieClickSupport;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TvSeriesAdapter;
import com.example.moviecatalogue.feature.detailTV.DetailTV;
import com.example.moviecatalogue.entity.SearchTvModel;
import com.example.moviecatalogue.entity.TvSeriesItem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;
import static com.example.moviecatalogue.utils.Constant.KEY_TV_ID;
import static com.example.moviecatalogue.utils.Constant.SEARCH_MOVIE;

public class SearchTV extends AppCompatActivity implements MovieClickSupport {

    private static String TAG = "SearchMovieModel";

    @BindView(R.id.rv_search_tv)
    RecyclerView recyclerView;

    TvSeriesAdapter tvSeriesAdapter;
    private ArrayList<TvSeriesItem> tvSeriesItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);

        ButterKnife.bind(this);

        String query = getIntent().getStringExtra(SEARCH_MOVIE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(query);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpList();
        loadData(query);
    }

    private void setUpList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tvSeriesAdapter = new TvSeriesAdapter(this, tvSeriesItems);
        recyclerView.setAdapter(tvSeriesAdapter);
    }

    private void loadData(String query) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<SearchTvModel> call = apiService.getSearchTv(API_KEY, query);
        call.enqueue(new Callback<SearchTvModel>() {
            @Override
            public void onResponse(Call<SearchTvModel> call, Response<SearchTvModel> response) {
                if (response.isSuccessful()) {
                    tvSeriesItems.addAll(Objects.requireNonNull(response.body()).getTvSeriesItems());
                    tvSeriesAdapter.notifyDataSetChanged();
                } else {
                    loadFailed();
                }
            }

            @Override
            public void onFailure(Call<SearchTvModel> call, Throwable t) {
                loadFailed();
            }
        });

    }

    private void loadFailed() {
        Toast.makeText(this, "Load Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieItemClick(int position) {

        if (position == -1) {
            return;
        }
            Intent intent = new Intent(getApplicationContext(), DetailTV.class);
            intent.putExtra(KEY_TV_ID, tvSeriesItems.get(position));
            startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
