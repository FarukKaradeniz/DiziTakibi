package com.omeerfk.dizitakibi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.omeerfk.dizitakibi.adapters.FavoriteShowsAdapter;
import com.omeerfk.dizitakibi.R;
import com.omeerfk.dizitakibi.database.Database;
import com.omeerfk.dizitakibi.model.TvShow;
import com.omeerfk.dizitakibi.services.DownloadToDatabaseService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoritesFragment extends Fragment {
    @BindView(R.id.fav_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.during_refresh)
    ProgressBar bar;

    Unbinder unbinder;
    Database database;

    ArrayList<TvShow> shows;

    FavoriteShowsAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(getActivity());
        database.open();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        unbinder = ButterKnife.bind(this, v);
        shows = database.getTvShows();

        adapter = new FavoriteShowsAdapter(getActivity(), shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String msg){
        bar.setVisibility(View.VISIBLE);
        for (int i=0 ; i<shows.size() ; i++){
            if (shows.get(i).getCountdown() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                try {
                    Date countDownTime = format.parse(shows.get(i).getCountdown().getAirDate());
                    if (countDownTime.before(now)){
                        database.removeTvShow(shows.get(i));
                        Intent intent = new Intent(getActivity(), DownloadToDatabaseService.class);
                        intent.putExtra("id", shows.get(i).getId());
                        getActivity().startService(intent);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        shows = database.getTvShows();
        adapter.setShows(shows);
        database.removeCountdownIfShowDoesNotExist();
        bar.setVisibility(View.INVISIBLE);
    }

}
