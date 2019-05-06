package com.example.moviecatalogue.feature.favorites.favoriteTV;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.FavoriteTvAdapter;
import com.example.moviecatalogue.entity.TvSeriesItem;

import static com.example.moviecatalogue.database.DatabaseContract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment {

    private FavoriteTvAdapter favoriteTvAdapter;
    private Cursor list;
    private RecyclerView recyclerView;
    private TvSeriesItem tvSeriesItem;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FavoriteTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh_tv);
        recyclerView = view.findViewById(R.id.rv_list_fav_tv);

        new loadData().execute();
        showListData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadData().execute();
                showListData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void showListData() {
        favoriteTvAdapter = new FavoriteTvAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteTvAdapter);
        favoriteTvAdapter.setListTvs(list);
        recyclerView.setHasFixedSize(true);
    }

    private class loadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(
                    CONTENT_URI_TV,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            favoriteTvAdapter.setListTvs(list);
            favoriteTvAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
