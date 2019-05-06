package com.example.moviecatalogue.feature.movies;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviecatalogue.MovieClickSupport;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MovieAdapter;
import com.example.moviecatalogue.feature.detailMovie.DetailMovie;
import com.example.moviecatalogue.entity.MovieItem;
import com.example.moviecatalogue.entity.SearchMovieModel;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.network.ApiInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.network.ApiClient.API_KEY;
import static com.example.moviecatalogue.utils.Constant.KEY_MOVIE_ID;
import static com.example.moviecatalogue.utils.Constant.SEARCH_MOVIE;

public class SearchMovie extends AppCompatActivity implements MovieClickSupport {

    private static String TAG = "SearchMovieModel";

    @BindView(R.id.rv_search_movie)
    RecyclerView recyclerView;

    MovieAdapter movieAdapter;
    private ArrayList<MovieItem> movieItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        ButterKnife.bind(this);

        String query = getIntent().getStringExtra(SEARCH_MOVIE);
        Log.d(TAG, "search: " + query);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(query);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpList();
        loadData(query);
    }

    private void setUpList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        movieAdapter = new MovieAdapter(this, movieItems);
        recyclerView.setAdapter(movieAdapter);
    }

    private void loadData(String query) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<SearchMovieModel> call = apiService.getSearchMovie(API_KEY, query);
        call.enqueue(new Callback<SearchMovieModel>() {
            @Override
            public void onResponse(Call<SearchMovieModel> call, Response<SearchMovieModel> response) {
                if (response.isSuccessful()) {
                    movieItems.addAll(response.body().getMovieItems());
                    movieAdapter.notifyDataSetChanged();
                } else {
                    loadFailed();
                }
            }

            @Override
            public void onFailure(Call<SearchMovieModel> call, Throwable t) {
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
        Intent intent = new Intent(getApplicationContext(), DetailMovie.class);
        intent.putExtra(KEY_MOVIE_ID, movieItems.get(position));
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
