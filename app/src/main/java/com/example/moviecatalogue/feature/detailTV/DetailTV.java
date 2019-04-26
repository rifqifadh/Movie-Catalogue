package com.example.moviecatalogue.feature.detailTV;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.DatabaseContract;
import com.example.moviecatalogue.database.TvHelper;
import com.example.moviecatalogue.model.TVDetailtem;
import com.example.moviecatalogue.model.TvSeriesItem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.utils.DateTime;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecatalogue.database.DatabaseContract.CONTENT_URI_TV;
import static com.example.moviecatalogue.utils.Constant.KEY_TV_ID;

public class DetailTV extends AppCompatActivity implements DetailTvContract.View {

    public static final String MOVIE_ITEM = "movie_item";

    private Boolean isFavorite = false;
    private TvSeriesItem item;
    private TvHelper tvHelper;

    private final String TAG = "Details: ";

    @BindView(R.id.image_poster_tv)
    ImageView imagePoster;

    @BindView(R.id.tv_detail_name)
    TextView tvName;

    @BindView(R.id.season)
    TextView season;

    @BindView(R.id.genre_tv)
    TextView tvGenre;

    @BindView(R.id.popularity_tv)
    TextView popularity;

    @BindView(R.id.first_air_date)
    TextView firstAirDate;

    @BindView(R.id.last_air_date)
    TextView lastAirDate;

    @BindView(R.id.number_of_eps)
    TextView numberOfEps;

    @BindView(R.id.overview_tv)
    TextView overview;

    @BindView(R.id.shimmer_view_container_tv)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.scroll_tv)
    ScrollView scrollView;

    @BindView(R.id.fav_tv)
    FloatingActionButton favButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        ButterKnife.bind(this);

        item = getIntent().getParcelableExtra(KEY_TV_ID);

        DetailTvPresenter detailTvPresenter = new DetailTvPresenter(this);
        detailTvPresenter.requestTvData(item.getId());

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) FavoriteRemove();
                else FavoriteSave();

                isFavorite = !isFavorite;
                setFavorite();
            }
        });
    }

    private void setFavorite() {
        if (isFavorite) favButton.setImageResource(R.drawable.ic_favorite);
        else favButton.setImageResource(R.drawable.ic_favorite_border);
    }

    @Override
    public void showProgress() {
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgress() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToView(TVDetailtem tvDetailtem) {

        loadDataSQL();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(tvDetailtem.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int size = 0;

        String genres = "";
        size = tvDetailtem.getGenres().size();
        for (int i = 0; i < size; i++) {
            genres += tvDetailtem.getGenres().get(i).getName() + (i + 1 < size ? ", " : "");
        }
        tvGenre.setText(genres);
        tvName.setText(tvDetailtem.getName());
        season.setText(String.valueOf(tvDetailtem.getSeasons()));
        popularity.setText(String.valueOf(tvDetailtem.getRating()));
        firstAirDate.setText(DateTime.getLongDate(tvDetailtem.getFirstAirDate()));
        lastAirDate.setText(DateTime.getLongDate(tvDetailtem.getLastAirDate()));
        numberOfEps.setText(String.valueOf(tvDetailtem.getNumberOfEpisode()));
        overview.setText(tvDetailtem.getOverview());

        Glide.with(this).load(ApiClient.IMAGE_BASE_URL + tvDetailtem.getThumbPath()).into(imagePoster);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvHelper != null) tvHelper.close();
    }

    private void loadDataSQL() {
        tvHelper = new TvHelper(this);
        tvHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_TV + "/" + item.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }

    private void FavoriteSave() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.TvColumns.TV_ID, item.getId());
        contentValues.put(DatabaseContract.TvColumns.TITLE_TV, item.getOriginalName());
        contentValues.put(DatabaseContract.TvColumns.OVERVIEW_TV, item.getOverview());
        contentValues.put(DatabaseContract.TvColumns.POSTER_TV, item.getPosterPath());

        getContentResolver().insert(CONTENT_URI_TV, contentValues);
        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI_TV + "/" + item.getId()),
                null,
                null
        );
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
