package com.omeerfk.dizitakibi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omeerfk.dizitakibi.model.Show;
import com.omeerfk.dizitakibi.model.TelevisionShow;
import com.omeerfk.dizitakibi.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder>{
    private List<Show> shows;
    private Context context;

    public TvShowAdapter(Context context, List<Show> shows){
        this.context = context;
        this.shows = shows;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Show tvShow = shows.get(position);

        holder.name.setText(tvShow.getName());
        holder.genres.setText(tvShow.getNetwork());
        Picasso.with(context)
                .load(tvShow.getImageUrl())
                .placeholder(R.mipmap.image_place_holder)
                .error(R.mipmap.error_image)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public void setData(List<Show> newShows){
        this.shows = newShows;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_image)
        ImageView image;

        @BindView(R.id.list_item_name)
        TextView name;

        @BindView(R.id.list_item_network)
        TextView genres;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
