package com.example.moviecatalogue.feature.favorites;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TabAdapter;
import com.example.moviecatalogue.feature.favorites.favoriteMovie.FavoriteMovieFragment;
import com.example.moviecatalogue.feature.favorites.favoriteTV.FavoriteTVFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

//    private TabAdapter tabAdapter;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;

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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_favorites, container, false);

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_fav);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout_fav);
        tabLayout.setupWithViewPager(viewPager);

//        tabAdapter = new TabAdapter(getChildFragmentManager());
//        tabAdapter.addFragment(new FavoriteMovieFragment(), "Movie");
//        tabAdapter.addFragment(new FavoriteTVFragment(), "TV Series");
//
//        viewPager.setAdapter(tabAdapter);
//        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new FavoriteMovieFragment(), "Movie");
        tabAdapter.addFragment(new FavoriteTVFragment(), "TV Series");

        viewPager.setAdapter(tabAdapter);
    }
}
