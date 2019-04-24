package com.example.moviecatalogue.feature.tvSeries;

import com.example.moviecatalogue.model.TvSeriesItem;

import java.util.List;

public interface TvSeriesContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<TvSeriesItem> tvSeriesItemList);

            void onFailure(Throwable throwable);
        }

        void getTvSeriesList(OnFinishedListener onFinishedListener, int pageNo);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<TvSeriesItem> tvSeriesItemList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}
