package com.omeerfk.dizitakibi;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.omeerfk.dizitakibi.events.ListEvent;
import com.omeerfk.dizitakibi.events.ProgressEvent;
import com.omeerfk.dizitakibi.fragments.NetworkDialogFragment;
import com.omeerfk.dizitakibi.model.TvShow;
import com.omeerfk.dizitakibi.services.DownloadMostPopularList;
import com.omeerfk.dizitakibi.utils.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.most_popular_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar bar;

    private TvShowAdapter adapter;

    private List<TvShow> shows = new ArrayList<>();

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        boolean hasNetworkAccess = NetworkHelper.hasNetworkAccess(this);
        if (!hasNetworkAccess){
            //show dialog fragment
            FragmentManager manager = getFragmentManager();
            NetworkDialogFragment dialogFragment = new NetworkDialogFragment();
            dialogFragment.show(manager, dialogFragment.getClass().getSimpleName());

        }else{
            Intent intent = new Intent(this, DownloadMostPopularList.class);
            startService(intent);
        }

        adapter = new TvShowAdapter(this, shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }

    @Subscribe
    public void onListEvent(ListEvent listEvent){
        bar.setVisibility(View.INVISIBLE);
        setRecyclerViewAdapter(listEvent.getShows());
    }

    @Subscribe
    public void onProgressEvent(ProgressEvent progressEvent){
        bar.setProgress(progressEvent.getProgress());
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setRecyclerViewAdapter(List<TvShow> tvShows){
        adapter.setData(tvShows);
        adapter.notifyDataSetChanged();
    }

}
