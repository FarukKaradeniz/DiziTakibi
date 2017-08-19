package com.omeerfk.dizitakibi;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mng = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String title = intent.getStringExtra(RemindersActivity.TVSHOW_NAME);
        String text = intent.getStringExtra(RemindersActivity.TVSHOW_COUNTDOWN);

        Notification not = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.app_icon)
                .setAutoCancel(true)
                .build();

        mng.notify(1, not);
    }
}
