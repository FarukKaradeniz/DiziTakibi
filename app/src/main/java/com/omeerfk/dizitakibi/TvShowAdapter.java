package com.omeerfk.dizitakibi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.omeerfk.dizitakibi.database.Database;
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
    private Database db;

    public TvShowAdapter(Context context, List<Show> shows){
        this.context = context;
        this.shows = shows;
        db = new Database(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        db.open();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        db.close();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Show tvShow = shows.get(position);

        holder.name.setText(tvShow.getName());
        holder.genres.setText(tvShow.getNetwork());
        Picasso.with(context)
                .load(tvShow.getImageUrl())
                .placeholder(R.mipmap.image_place_holder)
                .error(R.mipmap.error_image)
                .into(holder.image);
        //// TODO: 2.08.2017
        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.fav.isChecked()){

                }else{

                }
            }
        });
        //database kontrolü eger db'de varsa checked yapılacak
        //uncheched yapılırsa db'den silinecek
        //eger unchecked ise checked yapılırsa db'ye eklenecek
        //holder.fav.setChecked();

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

        @BindView(R.id.item_is_favorited)
        ToggleButton fav;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
