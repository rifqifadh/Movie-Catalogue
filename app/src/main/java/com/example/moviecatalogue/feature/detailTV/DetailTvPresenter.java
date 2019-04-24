package com.example.moviecatalogue.feature.detailTV;

import com.example.moviecatalogue.model.TVDetailtem;

public class DetailTvPresenter implements DetailTvContract.Presenter, DetailTvContract.Model.OnFinishedListener {

    private DetailTvContract.View detailTvView;
    private DetailTvContract.Model detailTvModel;

    public DetailTvPresenter(DetailTvContract.View detailTvView) {
        this.detailTvView = detailTvView;
        this.detailTvModel = new TvDetailModel();
    }

    @Override
    public void onFinished(TVDetailtem tvDetailtem) {
        if (detailTvView != null) {
            detailTvView.hideProgress();
        }
        detailTvView.setDataToView(tvDetailtem);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (detailTvView != null) {
            detailTvView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.detailTvView = null;
    }

    @Override
    public void requestTvData(int tvId) {
        if (detailTvView != null) {
            detailTvView.showProgress();
        }
        detailTvModel.getDetail(this, tvId);
    }
}
