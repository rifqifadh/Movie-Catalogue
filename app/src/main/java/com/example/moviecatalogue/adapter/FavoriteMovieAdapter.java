package com.example.moviecatalogue.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.feature.detailMovie.DetailMovie;
import com.example.moviecatalogue.feature.favorites.favoriteMovie.FavoriteMovieFragment;
import com.example.moviecatalogue.entity.MovieItem;
import com.example.moviecatalogue.network.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecatalogue.utils.Constant.KEY_MOVIE_ID;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private Cursor cursor;



    private FavoriteMovieFragment favoriteMovieFragment;
    private final String TAG = "FavoriteMovieAdapter";


    public void setLismovies( Cursor lismovies) {
        this.cursor = lismovies;
    }

    public FavoriteMovieAdapter(FavoriteMovieFragment favoriteMovieFragment, Cursor list) {
        this.favoriteMovieFragment = favoriteMovieFragment;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MovieItem movieItem = getItem(position);

        holder.tvTitle.setText(movieItem.getTitle());
        holder.tvDesc.setText(movieItem.getOverview());
        Log.d(TAG, "Movie ID: " + movieItem.getId());
        Glide.with(favoriteMovieFragment).load(ApiClient.IMAGE_BASE_URL + movieItem.getPosterPath()).into(holder.imgPoster);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailMovie.class);
                intent.putExtra(KEY_MOVIE_ID, movieItem);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
         if (cursor == null) return 0;
         return cursor.getCount();
    }

    private MovieItem getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(cursor);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvTitle;

        @BindView(R.id.tv_desc)
        TextView tvDesc;

        @BindView(R.id.iv_poster)
        ImageView imgPoster;

        @BindView(R.id.card_view)
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
