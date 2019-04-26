package com.example.moviecatalogue.feature.detailMovie;

import com.example.moviecatalogue.model.MovieDetailItem;

public class DetailMoviePresenter implements DetailMovieContract.Presenter, DetailMovieContract.Model.OnFinishedListener {

    private DetailMovieContract.View detailView;
    private DetailMovieContract.Model detailModel;

    public DetailMoviePresenter(DetailMovieContract.View detailView) {
        this.detailView = detailView;
        this.detailModel = new MovieDetailModel();
    }

    @Override
    public void onFinished(MovieDetailItem movieDetailItem) {
        if (detailView != null) {
            detailView.hideProgress();
        }
        detailView.setDataToView(movieDetailItem);
    }

    @Override
    public void onFailure(Throwable throwable) {
        detailView.onResponseFailure(throwable);
        if (detailView != null) {
            detailView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.detailView = null;
    }

    @Override
    public void requestMovieData(int movieId) {
        if (detailView != null) {
            detailView.showProgress();
        }
        detailModel.getDetail(this, movieId);
    }
}
