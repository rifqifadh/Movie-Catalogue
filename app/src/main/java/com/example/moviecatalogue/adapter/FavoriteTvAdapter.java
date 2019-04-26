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
import com.example.moviecatalogue.feature.detailTV.DetailTV;
import com.example.moviecatalogue.feature.favorites.favoriteTV.FavoriteTVFragment;
import com.example.moviecatalogue.model.TvSeriesItem;
import com.example.moviecatalogue.network.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecatalogue.utils.Constant.KEY_TV_ID;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    private Cursor cursor;

    private FavoriteTVFragment favoriteTVFragment;

    private final String TAG = "FavoriteTvAdapter";


    public void setListTvs(Cursor listTvs) {
        this.cursor = listTvs;
    }

    public FavoriteTvAdapter(FavoriteTVFragment favoriteTVFragment, Cursor list) {
        this.favoriteTVFragment = favoriteTVFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TvSeriesItem tvSeriesItem = getItem(position);

        Log.d(TAG, "FavoriteTvAdapter: " + tvSeriesItem.getOriginalName());

        holder.tvTitle.setText(tvSeriesItem.getOriginalName());
        holder.tvDesc.setText(tvSeriesItem.getOverview());
        Glide.with(favoriteTVFragment).load(ApiClient.IMAGE_BASE_URL + tvSeriesItem.getPosterPath()).into(holder.imgPoster);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailTV.class);
                intent.putExtra(KEY_TV_ID, tvSeriesItem);
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
        return  cursor.getCount();
    }

    private TvSeriesItem getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new TvSeriesItem(cursor);
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
