package com.example.moviecatalogue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.feature.movies.MovieFragment;
import com.example.moviecatalogue.model.MovieItem;
import com.example.moviecatalogue.network.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private MovieFragment movieFragment;
    private List<MovieItem> movieItemList;
    private Context context;
//    private List<MovieItem> originalMovieList;

    private final String TAG = "Adapter";


    public MovieAdapter(MovieFragment movieFragment, List<MovieItem> movieItemList) {
        this.movieFragment = movieFragment;
        this.movieItemList = movieItemList;
//        this.originalMovieList = movieItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final MovieItem movieItem = movieItemList.get(position);

        holder.tvTitle.setText(movieItem.getTitle());
        holder.tvDesc.setText(movieItem.getOverview());

        Glide.with(movieFragment).load(ApiClient.IMAGE_BASE_URL + movieItem.getPosterPath()).into(holder.imgPoster);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieFragment.onMovieItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItemList.size();
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
