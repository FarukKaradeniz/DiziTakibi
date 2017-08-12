package com.omeerfk.dizitakibi.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.omeerfk.dizitakibi.model.Show;
import com.omeerfk.dizitakibi.services.DownloadToDatabaseService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder>{
    private List<Show> shows;
    private Context context;
    private Database db;

    public ShowAdapter(Context context, List<Show> shows){
        this.context = context;
        db = new Database(context);
        db.open();
        setData(shows);
        setHasStableIds(true);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        db.close();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Show tvShow = shows.get(position);

        holder.fav.setChecked(tvShow.isFavorited());
        holder.name.setText(tvShow.getName());
        holder.network.setText(tvShow.getNetwork());
        Picasso.with(context)
                .load(tvShow.getImageUrl())
                .placeholder(R.mipmap.image_place_holder)
                .error(R.mipmap.error_image)
                .into(holder.image);

        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.fav.isChecked()){
                    tvShow.setFavorited(true);
                    Intent i = new Intent(context, DownloadToDatabaseService.class);
                    i.putExtra("id", tvShow.getId());
                    context.startService(i);
                }else{
                    tvShow.setFavorited(false);
                    db.removeShow(tvShow);
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return shows.get(position).hashCode();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public void setData(List<Show> newShows){
        this.shows = newShows;
        for (int i=0 ; i<shows.size() ; i++){
            if (db.getTvShowById(shows.get(i).getId()) != null){
                shows.get(i).setFavorited(true);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_image)
        ImageView image;

        @BindView(R.id.list_item_name)
        TextView name;

        @BindView(R.id.list_item_network)
        TextView network;

        @BindView(R.id.item_is_favorited)
        ToggleButton fav;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
