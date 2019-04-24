package com.example.moviecatalogue.feature.tvSeries;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviecatalogue.MovieClickSupport;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TvSeriesAdapter;
import com.example.moviecatalogue.feature.detailMovie.DetailMovie;
import com.example.moviecatalogue.feature.detailTV.DetailTV;
import com.example.moviecatalogue.model.TvSeriesItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.example.moviecatalogue.utils.Constant.KEY_MOVIE_ID;
import static com.example.moviecatalogue.utils.Constant.KEY_TV_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvSeriesFragment extends Fragment implements TvSeriesContract.View, MovieClickSupport {

    @BindView(R.id.progress_bar_tv)
    ProgressBar progressBar;

    private static final String TV_LIST_STATE = "tvListState";
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<TvSeriesItem> tvSeriesItems = new ArrayList<>();

    private Context context;
    private TvSeriesPresenter tvSeriesPresenter;
    private int pageNo = 1;
    private int previousTotal = 0;
    private boolean loading = true;

    int firstVisibleItem, visibleItemCount, totalItemCount;

    RecyclerView rvTvseries;
    TvSeriesAdapter tvSeriesAdapter;


    public TvSeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_series, container,false);

        ButterKnife.bind(this, view);

        Log.d(TAG, "view: " + view);

//        if (savedInstanceState == null) {
            tvSeriesPresenter = new TvSeriesPresenter(this);
            tvSeriesPresenter.requestDataFromServer();
            Log.d(TAG, "presenter :" + tvSeriesPresenter);
//        } else {
//            tvSeriesItems = savedInstanceState.getParcelableArrayList(TV_LIST_STATE);
//            tvSeriesPresenter = new TvSeriesPresenter(this);
//            tvSeriesPresenter.requestDataFromServer();
//            progressBar.setVisibility(View.GONE);
//        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        rvTvseries = view.findViewById(R.id.rv_list_tv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvTvseries.setHasFixedSize(true);
        rvTvseries.setLayoutManager(linearLayoutManager);
        tvSeriesAdapter = new TvSeriesAdapter(this, tvSeriesItems);
        rvTvseries.setAdapter(tvSeriesAdapter);

//        rvTvseries = view.findViewById(R.id.rv_list_tv);
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        rvTvseries.setHasFixedSize(true);
//        rvTvseries.setLayoutManager(linearLayoutManager);
//        tvSeriesAdapter = new TvSeriesAdapter(this, tvSeriesItems);
//        rvTvseries.setAdapter(tvSeriesAdapter);

//        Log.d(TAG, "adapter: " + rvTvseries.setAdapter(tvSeriesAdapter););

//        setListener();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        rvTvseries.setAdapter(null);
    }

    private void setListener() {
        rvTvseries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvTvseries.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleItemCount)) {
                    tvSeriesPresenter.getMoreData(pageNo);
                    loading = true;
                }
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
    public void setDataToRecyclerView(List<TvSeriesItem> tvSeriesItemList) {
        tvSeriesItems.addAll(tvSeriesItemList);
        tvSeriesAdapter.notifyDataSetChanged();

        Log.d(TAG, "setDataToRecyclerView: " + tvSeriesItemList);

        pageNo++;
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(TV_LIST_STATE, tvSeriesItems);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onMovieItemClick(int position) {

        if  (position == -1) {
            return;
        }
        Intent intent = new Intent(getActivity(), DetailTV.class);
        intent.putExtra(KEY_TV_ID, tvSeriesItems.get(position));
        startActivity(intent);
    }
}
