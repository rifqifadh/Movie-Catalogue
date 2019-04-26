package com.example.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviecatalogue.feature.favorites.FavoritesFragment;
import com.example.moviecatalogue.feature.movies.MovieFragment;
import com.example.moviecatalogue.feature.tvSeries.TvSeriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;

    Fragment fragment = new TvSeriesFragment();
    public static final String KEY_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.movies);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, fragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tv_series:
                fragment = new TvSeriesFragment();
                break;
            case R.id.movies:
                fragment = new MovieFragment();
                break;
            case R.id.favorites:
                fragment = new FavoritesFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, fragment).commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        }
        super.onSaveInstanceState(outState);
    }
}
