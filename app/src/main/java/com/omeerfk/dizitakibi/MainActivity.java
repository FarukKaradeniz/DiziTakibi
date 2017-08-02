package com.omeerfk.dizitakibi;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.omeerfk.dizitakibi.fragments.NetworkDialogFragment;
import com.omeerfk.dizitakibi.fragments.SearchFragment;
import com.omeerfk.dizitakibi.services.DownloadMostPopularList;
import com.omeerfk.dizitakibi.utils.NetworkHelper;

import butterknife.ButterKnife;

public class MainActivity extends Activity {


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

        getFragmentManager().beginTransaction()
                //.replace(R.id.container, new MostPopularFragment())
                .replace(R.id.container, new SearchFragment())
                .commit();
    }


}
