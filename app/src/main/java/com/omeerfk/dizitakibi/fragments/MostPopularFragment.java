package com.omeerfk.dizitakibi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.omeerfk.dizitakibi.R;
import com.omeerfk.dizitakibi.adapters.ShowAdapter;
import com.omeerfk.dizitakibi.events.ListEvent;
import com.omeerfk.dizitakibi.events.ProgressEvent;
import com.omeerfk.dizitakibi.model.Show;
import com.omeerfk.dizitakibi.services.DownloadMostPopularList;
import com.omeerfk.dizitakibi.utils.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MostPopularFragment extends Fragment {
    @BindView(R.id.most_popular_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar bar;

    private Unbinder unbinder;

    private ShowAdapter adapter;

    private List<Show> shows = new ArrayList<>();

    public MostPopularFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_most_popular, container, false);
        unbinder = ButterKnife.bind(this, v);

         boolean hasNetworkAccess = NetworkHelper.hasNetworkAccess(getActivity());
        if (!hasNetworkAccess){
            //show dialog fragment
            NetworkDialogFragment dialogFragment = new NetworkDialogFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
        }else{
            Intent intent = new Intent(getActivity(), DownloadMostPopularList.class);
            getActivity().startService(intent);
        }

        adapter = new ShowAdapter(getActivity(), shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListEvent(ListEvent listEvent){
        bar.setVisibility(View.INVISIBLE);
        setRecyclerViewAdapter(listEvent.getShows());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(ProgressEvent progressEvent){
        bar.setProgress(progressEvent.getProgress());
    }

    private void setRecyclerViewAdapter(List<Show> tvShows){
        adapter.setData(tvShows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
