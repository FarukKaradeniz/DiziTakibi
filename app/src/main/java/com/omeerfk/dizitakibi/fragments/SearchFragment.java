package com.omeerfk.dizitakibi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omeerfk.dizitakibi.R;
import com.omeerfk.dizitakibi.ShowsApi;
import com.omeerfk.dizitakibi.adapters.ShowAdapter;
import com.omeerfk.dizitakibi.model.Show;
import com.omeerfk.dizitakibi.model.ShowsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{
    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.bar)
    ProgressBar bar;

    @BindView(R.id.arama_uyari)
    TextView tv;

    private ShowAdapter adapter;

    private boolean isSearched;

    private List<Show> shows = new ArrayList<>();


    Unbinder unbinder;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        isSearched = false;
        adapter = new ShowAdapter(getActivity(), shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        searchView.setOnQueryTextListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSearched)
            tv.setVisibility(View.VISIBLE);
        else
            tv.setVisibility(View.INVISIBLE);
    }

    private void setRecyclerViewAdapter(List<Show> tvShows){
        adapter.setData(tvShows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        bar.setVisibility(View.VISIBLE);
        isSearched = true;
        ShowsApi api = ShowsApi.Reference.getInstance();
        Call<ShowsList> call = api.searchByName(s, 1);

        call.enqueue(new Callback<ShowsList>() {
            @Override
            public void onResponse(Call<ShowsList> call, Response<ShowsList> response) {
                if (response.isSuccessful()) {
                    shows = response.body().getShows();
                    bar.setVisibility(View.INVISIBLE);
                    setRecyclerViewAdapter(shows);
                }
            }

            @Override
            public void onFailure(Call<ShowsList> call, Throwable t) {
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
