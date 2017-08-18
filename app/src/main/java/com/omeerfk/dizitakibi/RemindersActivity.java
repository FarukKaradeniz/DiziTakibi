package com.omeerfk.dizitakibi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.omeerfk.dizitakibi.events.RemindersEvent;
import com.omeerfk.dizitakibi.model.TvShow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindersActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_reminders)
    Toolbar toolbar;

    @BindView(R.id.reminders_list)
    ListView listView;

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
        for (TvShow show : event.getShows()){
            info.add(show.getShowInfo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
        listView.setAdapter(adapter);


    }
}
