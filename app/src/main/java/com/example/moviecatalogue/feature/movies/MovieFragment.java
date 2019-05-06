package com.example.moviecatalogue.feature.movies;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviecatalogue.MovieClickSupport;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MovieAdapter;
import com.example.moviecatalogue.feature.detailMovie.DetailMovie;
import com.example.moviecatalogue.entity.MovieItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecatalogue.utils.Constant.KEY_MOVIE_ID;
import static com.example.moviecatalogue.utils.Constant.SEARCH_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieListContract.View, MovieClickSupport {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private static final String MOVIE_LIST_STATE = "movieListState";
    private static final String TAG = "MovieFragment";
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<MovieItem> movieItems = new ArrayList<>();

    private Context context;
    private MoviePresenter moviePresenter;
    private int pageNo = 1;
    private int previousTotal = 0;
    private boolean loading = true;

    int firstVisibleItem, visibleItemCount, totalItemCount;

    RecyclerView rvMovies;
    MovieAdapter movieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            moviePresenter = new MoviePresenter(this);
            moviePresenter.requestDataFromServer();
        }  else {
            movieItems = savedInstanceState.getParcelableArrayList(MOVIE_LIST_STATE);
            moviePresenter = new MoviePresenter(this);
            moviePresenter.requestDataFromServer();
            progressBar.setVisibility(View.GONE);
        }

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        rvMovies = view.findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(linearLayoutManager);
        movieAdapter = new MovieAdapter(this, movieItems);
        rvMovies.setAdapter(movieAdapter);

        setListener();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rvMovies.setAdapter(null);
    }

    private void setListener() {
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvMovies.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleItemCount)) {
                    moviePresenter.getMoreData(pageNo);
                    loading = true;

                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_label));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchMovie.class);
                    intent.putExtra(SEARCH_MOVIE, query);
                    startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }



    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<MovieItem> movieArrayList) {
        movieItems.addAll(movieArrayList);
        movieAdapter.notifyDataSetChanged();

        pageNo++;
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST_STATE, movieItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMovieItemClick(int position) {

        if (position == -1) {
            return;
        }
        Intent intent = new Intent(getActivity(), DetailMovie.class);
        intent.putExtra(KEY_MOVIE_ID, movieItems.get(position));
        startActivity(intent);
    }
}
