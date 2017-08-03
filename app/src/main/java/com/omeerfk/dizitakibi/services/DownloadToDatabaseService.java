package com.omeerfk.dizitakibi.services;

import android.app.IntentService;
import android.content.Intent;

import com.omeerfk.dizitakibi.ShowsApi;
import com.omeerfk.dizitakibi.database.Database;
import com.omeerfk.dizitakibi.model.TelevisionShow;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;


public class DownloadToDatabaseService extends IntentService {

    private Database database;

    public DownloadToDatabaseService() {
        super("DownloadToDatabaseService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = new Database(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        database.open();

        int id = intent.getIntExtra("id", 0);
        if (database.isTvShowInDatabase(id)){
            return;
        }
        ShowsApi api = ShowsApi.Reference.getInstance();
        Call<TelevisionShow> call = api.getTvShow(String.valueOf(id));

        try {
            Response<TelevisionShow> response = call.execute();
            if (response.isSuccessful()){
                database.addTvShow(response.body().getTvShow());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        database.close();
    }
}
