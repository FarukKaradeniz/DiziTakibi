package com.omeerfk.dizitakibi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.omeerfk.dizitakibi.R;
import com.omeerfk.dizitakibi.database.Database;
import com.omeerfk.dizitakibi.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Faruk Karadeniz on 3.08.2017.
 */

public class FavoriteShowsAdapter extends RecyclerView.Adapter<FavoriteShowsAdapter.ViewHolder>{
    private List<TvShow> shows;
    private Context context;
    private Database db;

    public FavoriteShowsAdapter(Context context, List<TvShow> shows){
        this.context = context;
        db = new Database(context);
        db.open();
        setShows(shows);
        setHasStableIds(true);
    }


    public void setShows(List<TvShow> shows) {
        this.shows = shows;
        Collections.sort(this.shows, new Comparator<TvShow>() {
            @Override
            public int compare(TvShow tvShow, TvShow t1) {
                return tvShow.getName().compareTo(t1.getName());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item_view, viewGroup, false);
        return new FavoriteShowsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TvShow show = shows.get(position);

        holder.fav.setChecked(show.isFavorited());
        holder.network.setText(show.getNetwork());
        holder.name.setText(show.getName());
        Picasso.with(context)
                .load(show.getImageUrl())
                .placeholder(R.mipmap.image_place_holder)
                .error(R.mipmap.error_image)
                .into(holder.image);

        if (show.getCountdown() != null){
            holder.count.setText(show.getCountdown().getAirDate());
        }else{
            holder.count.setText(R.string.no_episode);
        }

        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!holder.fav.isChecked()){
                    shows.get(position).setFavorited(false);
                    db.removeTvShow(shows.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    @Override
    public long getItemId(int position) {
        return shows.get(position).hashCode();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fav_item_image)
        ImageView image;

        @BindView(R.id.fav_item_name)
        TextView name;

        @BindView(R.id.fav_item_network)
        TextView network;

        @BindView(R.id.fav_item_is_favorited)
        ToggleButton fav;

        @BindView(R.id.fav_item_countdown)
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
