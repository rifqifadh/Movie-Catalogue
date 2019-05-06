package com.example.moviecatalogue.feature.movies;

import com.example.moviecatalogue.entity.MovieItem;

import java.util.List;

public interface MovieListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<MovieItem> movieItemList);

            void onFailure(Throwable throwable);
        }

        void getMovieList(OnFinishedListener onFinishedListener, int pageNo);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<MovieItem> movieItemList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}
