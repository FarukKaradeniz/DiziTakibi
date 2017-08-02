package com.omeerfk.dizitakibi.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omeerfk.dizitakibi.R;
import com.omeerfk.dizitakibi.ShowsApi;
import com.omeerfk.dizitakibi.TvShowAdapter;
import com.omeerfk.dizitakibi.model.ShowsList;
import com.omeerfk.dizitakibi.model.Show;

import java.util.ArrayList;
import java.util.Collections;
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

    private TvShowAdapter adapter;

    private List<Show> shows = new ArrayList<>();

    private final String TAG = getClass().getSimpleName();

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

        adapter = new TvShowAdapter(getActivity(), shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        searchView.setOnQueryTextListener(this);

        return view;
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

        if (TextUtils.isEmpty(tv.getText())){
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.INVISIBLE);
        }

        Log.e(TAG, "Text Submitted");
        bar.setVisibility(View.VISIBLE);

        ShowsApi api = ShowsApi.Reference.getInstance();
        Call<ShowsList> call = api.searchByName(s, 1);

        call.enqueue(new Callback<ShowsList>() {
            @Override
            public void onResponse(Call<ShowsList> call, Response<ShowsList> response) {
                if (response.isSuccessful()) {
                    shows = response.body().getShows();
                    Log.e(TAG, "onResponse: " + shows.size());
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
