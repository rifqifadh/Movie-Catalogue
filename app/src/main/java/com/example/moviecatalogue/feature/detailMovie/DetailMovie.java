package com.example.moviecatalogue.feature.detailMovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.DatabaseContract;
import com.example.moviecatalogue.database.MovieHelper;
import com.example.moviecatalogue.model.MovieDetailItem;
import com.example.moviecatalogue.model.MovieItem;
import com.example.moviecatalogue.network.ApiClient;
import com.example.moviecatalogue.utils.DateTime;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecatalogue.database.DatabaseContract.CONTENT_URI;
import static com.example.moviecatalogue.utils.Constant.KEY_MOVIE_ID;

public class DetailMovie extends AppCompatActivity implements DetailMovieContract.View {

    public static final String MOVIE_ITEM = "movie_item";

    private MovieHelper movieHelper;
    private Boolean isFavorite = false;
    private MovieItem item;

    private final String TAG = "Details: ";


    @BindView(R.id.image_poster)
    ImageView imagePoster;

    @BindView(R.id.film_name)
    TextView filmName;

    @BindView(R.id.tagline)
    TextView tagline;

    @BindView(R.id.overview)
    TextView overview;

    @BindView(R.id.popularity)
    TextView popularity;

    @BindView(R.id.release_date)
    TextView releaseDate;

    @BindView(R.id.genre)
    TextView Genre;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.fav_movie)
    FloatingActionButton favButton;

    @BindView(R.id.scroll_movie)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

//        if (!isFavorite) {
//            isFavorite = true;
//        }

//        Log.d(TAG, "IsFavorite: " + isFavorite);


        item = getIntent().getParcelableExtra(KEY_MOVIE_ID);

        DetailMoviePresenter detailPresenter = new DetailMoviePresenter(this);
        detailPresenter.requestMovieData(item.getId());

//        FavoriteSave();
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
    public void setDataToView(MovieDetailItem movieDetailItem) {

        loadDataSQL();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {


            actionBar.setTitle(movieDetailItem.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        int size = 0;

        String genres = "";
        size = movieDetailItem.getGenres().size();
        for (int i = 0; i < size; i++) {
            genres += movieDetailItem.getGenres().get(i).getName() + (i + 1 < size ? ", " : "");
        }

        Genre.setText(genres);
        filmName.setText(movieDetailItem.getTitle());
        tagline.setText(movieDetailItem.getTagline());
        overview.setText(movieDetailItem.getOverview());
        popularity.setText(String.valueOf(movieDetailItem.getRating()));
        releaseDate.setText(DateTime.getLongDate(movieDetailItem.getReleaseDate()));


        Glide.with(this).load(ApiClient.IMAGE_BASE_URL + movieDetailItem.getPoster_path()).into(imagePoster);



    }


    private void loadDataSQL() {
        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null,
                null,
                null
        );

        Log.d(TAG, "Cursor: " + cursor);

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }


    private void FavoriteSave() {

        Log.d(TAG, " Detail Movie: " + item.getId());

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.MovieColumns.MOVIE_ID, item.getId());
        contentValues.put(DatabaseContract.MovieColumns.TITLE, item.getTitle());
        contentValues.put(DatabaseContract.MovieColumns.OVERVIEW, item.getOverview());
        contentValues.put(DatabaseContract.MovieColumns.POSTER, item.getPosterPath());

        getContentResolver().insert(CONTENT_URI, contentValues);
        Log.d(TAG, "Content Values: " + contentValues);
        Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + item.getId()),
                null,
                null
        );
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResponseFailure(Throwable throwable) {

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
