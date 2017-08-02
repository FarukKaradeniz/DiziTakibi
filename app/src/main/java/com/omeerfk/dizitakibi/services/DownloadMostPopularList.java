package com.omeerfk.dizitakibi.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.omeerfk.dizitakibi.ShowsApi;
import com.omeerfk.dizitakibi.events.ListEvent;
import com.omeerfk.dizitakibi.events.ProgressEvent;
import com.omeerfk.dizitakibi.model.ShowsList;
import com.omeerfk.dizitakibi.model.Show;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class DownloadMostPopularList extends IntentService {
    private final String TAG = getClass().getSimpleName();
    private List<Show> shows = new ArrayList<>();

    public DownloadMostPopularList() {
        super("DownloadMostPopularList");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e(TAG, "Download Service Starting..");
        int progress = 0;
        ShowsApi api = ShowsApi.Reference.getInstance();

        try {
            for (int i = 1; i <= 3; i++) {

                Call<ShowsList> call = api.getMostPopularPage(i);
                Response<ShowsList> response = call.execute();

                if (response.isSuccessful()) {
                    shows.addAll(response.body().getShows());
                    progress += 100/3;
                    EventBus.getDefault().post(new ProgressEvent(progress));
                }

            }
        }
        catch (IOException e){
            return;
        }

        Log.e(TAG, String.format("Download of %d items are completed.", shows.size()));

        EventBus.getDefault().post(new ListEvent(shows));
    }

}
