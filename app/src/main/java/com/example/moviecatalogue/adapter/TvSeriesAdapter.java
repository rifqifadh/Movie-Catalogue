package com.example.moviecatalogue.adapter;

import android.content.Context;
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
import com.example.moviecatalogue.feature.tvSeries.SearchTV;
import com.example.moviecatalogue.feature.tvSeries.TvSeriesFragment;
import com.example.moviecatalogue.entity.TvSeriesItem;
import com.example.moviecatalogue.network.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvSeriesAdapter extends RecyclerView.Adapter<TvSeriesAdapter.ViewHolder> {

    private TvSeriesFragment tvSeriesFragment;
    private SearchTV searchTV;

    public TvSeriesAdapter(SearchTV searchTV, List<TvSeriesItem> tvSeriesItemList) {
        this.searchTV = searchTV;
        this.tvSeriesItemList = tvSeriesItemList;
    }

    private List<TvSeriesItem> tvSeriesItemList;
    private Context context;

    private final String TAG = "Adapter";


    public TvSeriesAdapter(TvSeriesFragment tvSeriesFragment, List<TvSeriesItem> tvSeriesItemList) {
        this.tvSeriesFragment = tvSeriesFragment;
        this.tvSeriesItemList = tvSeriesItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final TvSeriesItem tvSeriesItem = tvSeriesItemList.get(position);

        holder.tvTitle.setText(tvSeriesItem.getOriginalName());
        holder.tvDesc.setText(tvSeriesItem.getOverview());

        Log.d(TAG, "TV: " + String.valueOf(tvSeriesItem.getId()));

        if (tvSeriesFragment != null) {
            Glide.with(tvSeriesFragment).load(ApiClient.IMAGE_BASE_URL + tvSeriesItem.getPosterPath()).into(holder.imgPoster);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvSeriesFragment.onMovieItemClick(holder.getAdapterPosition());
                }
            });
        } else if (searchTV != null) {
            Glide.with(searchTV).load(ApiClient.IMAGE_BASE_URL + tvSeriesItem.getPosterPath()).into(holder.imgPoster);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTV.onMovieItemClick(holder.getAdapterPosition());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return tvSeriesItemList.size();
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
