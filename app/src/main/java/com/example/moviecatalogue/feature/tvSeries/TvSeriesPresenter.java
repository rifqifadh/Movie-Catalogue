package com.example.moviecatalogue.feature.tvSeries;

import com.example.moviecatalogue.model.TvSeriesItem;

import java.util.List;

public class TvSeriesPresenter implements TvSeriesContract.Presenter, TvSeriesContract.Model.OnFinishedListener {

    private TvSeriesContract.View tvListView;
    private TvSeriesContract.Model tvListModel;

    public TvSeriesPresenter(TvSeriesContract.View tvListView) {
        this.tvListView = tvListView;
        tvListModel = new TvSeriesModel();
    }

    @Override
    public void onFinished(List<TvSeriesItem> tvSeriesItemList) {
        tvListView.setDataToRecyclerView(tvSeriesItemList);
        if (tvListView != null) {
            tvListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        tvListView.onResponseFailure(throwable);
        if (tvListView != null) {
            tvListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.tvListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {
        if (tvListView != null) {
            tvListView.showProgress();
        }
        tvListModel.getTvSeriesList(this, pageNo);
    }

    @Override
    public void requestDataFromServer() {
        if (tvListView != null) {
            tvListView.showProgress();
        }
        tvListModel.getTvSeriesList(this, 1);
    }
}
