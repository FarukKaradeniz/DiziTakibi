package com.omeerfk.dizitakibi.services;

import android.app.IntentService;
import android.content.Intent;


public class DownloadToDatabaseService extends IntentService {

    public DownloadToDatabaseService() {
        super("DownloadToDatabaseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* todo
        * intent icerisinde yer alan id için GET request yapılacak.
        * bu sefer internetten alınan veri içerisinde countdown içerecek
        * internetten alınan bu veri database içerisinde kaydedilecek
        *
        * */



    }
}
