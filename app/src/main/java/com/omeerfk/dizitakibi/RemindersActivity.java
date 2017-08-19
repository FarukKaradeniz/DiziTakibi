package com.omeerfk.dizitakibi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.omeerfk.dizitakibi.events.RemindersEvent;
import com.omeerfk.dizitakibi.model.TvShow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindersActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_reminders)
    Toolbar toolbar;

    @BindView(R.id.reminders_list)
    ListView listView;

    public final static int REQUEST_CODE = 1;
    public final static String TVSHOW_NAME = "tvshow_name";
    public final static String TVSHOW_COUNTDOWN = "tvshow_countdown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        ButterKnife.bind(this);
        toolbar.setTitle("Reminders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Subscribe(sticky = true)
    public void onEvent(RemindersEvent event){

        ArrayList<String> info = new ArrayList<>();
        try{
            for (TvShow show : event.getShows()){
                setAlarm(show);
                info.add(show.getShowInfo());
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
        listView.setAdapter(adapter);
    }

    private void setAlarm(TvShow show) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.sdf_pattern));
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(show.getCountdown().getAirDate()));

        Intent i = new Intent(this, NotificationReceiver.class);
        i.putExtra(TVSHOW_NAME, show.getName());
        i.putExtra(TVSHOW_COUNTDOWN, show.getCountdown().getName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), REQUEST_CODE, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
