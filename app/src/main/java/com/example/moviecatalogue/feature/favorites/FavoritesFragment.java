package com.example.moviecatalogue.feature.favorites;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TabAdapter;
import com.example.moviecatalogue.feature.favorites.favoriteMovie.FavoriteMovieFragment;
import com.example.moviecatalogue.feature.favorites.favoriteTV.FavoriteTVFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager_fav);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_fav);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new FavoriteMovieFragment(), "Movie");
        tabAdapter.addFragment(new FavoriteTVFragment(), "TV Series");

        viewPager.setAdapter(tabAdapter);
    }
}
