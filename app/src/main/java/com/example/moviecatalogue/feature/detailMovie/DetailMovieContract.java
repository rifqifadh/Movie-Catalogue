package com.example.moviecatalogue.feature.detailMovie;

import com.example.moviecatalogue.model.MovieDetailItem;

public interface DetailMovieContract {

    interface Model {
        interface OnFinishedListener {
            void onFinished(MovieDetailItem movieDetailItem);

            void onFailure(Throwable throwable);
        }

        void getDetail(OnFinishedListener onFinishedListener, int movieId);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToView(MovieDetailItem movieDetailItem);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {

        void onDestroy();

        void requestMovieData(int movieId);
    }


}
