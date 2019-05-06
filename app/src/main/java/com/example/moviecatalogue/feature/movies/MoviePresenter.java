package com.example.moviecatalogue.feature.movies;

import com.example.moviecatalogue.entity.MovieItem;

import java.util.List;

public class MoviePresenter implements MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private MovieListContract.View movieListView;
    private MovieListContract.Model movieListModel;

    public MoviePresenter(MovieListContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MovieListModel();
    }

    @Override
    public void onFinished(List<MovieItem> movieItemList) {
        movieListView.setDataToRecyclerView(movieItemList);
        if (movieListView != null) {
            movieListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        movieListView.onResponseFailure(throwable);
        if (movieListView != null) {
            movieListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getMovieList(this, pageNo);
    }

    @Override
    public void requestDataFromServer() {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getMovieList(this, 1);
    }
}
