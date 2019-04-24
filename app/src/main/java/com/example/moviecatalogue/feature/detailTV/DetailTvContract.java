package com.example.moviecatalogue.feature.detailTV;

import com.example.moviecatalogue.model.TVDetailtem;

public interface DetailTvContract {

    interface Model {
        interface OnFinishedListener {
            void onFinished(TVDetailtem tvDetailtem);

            void onFailure(Throwable throwable);
        }

        void getDetail(OnFinishedListener onFinishedListener, int tvId);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToView(TVDetailtem tvDetailtem);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {

        void onDestroy();

        void requestTvData(int tvId);
    }
}
