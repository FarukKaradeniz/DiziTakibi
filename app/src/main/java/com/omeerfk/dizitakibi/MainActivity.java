package com.omeerfk.dizitakibi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.omeerfk.dizitakibi.fragments.FavoritesFragment;
import com.omeerfk.dizitakibi.fragments.MostPopularFragment;
import com.omeerfk.dizitakibi.fragments.NetworkDialogFragment;
import com.omeerfk.dizitakibi.fragments.SearchFragment;
import com.omeerfk.dizitakibi.services.DownloadMostPopularList;
import com.omeerfk.dizitakibi.utils.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(viewPager);

        tab.setupWithViewPager(viewPager);


        boolean hasNetworkAccess = NetworkHelper.hasNetworkAccess(this);
        if (!hasNetworkAccess){
            //show dialog fragment
            NetworkDialogFragment dialogFragment = new NetworkDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());

        }else{
            Intent intent = new Intent(this, DownloadMostPopularList.class);
            startService(intent);
        }

    }

    private void setupViewPager(ViewPager pager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        adapter.addFragment(new MostPopularFragment(), "Popular Shows");
        adapter.addFragment(new SearchFragment(), "Search Show");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
     private final List<Fragment> mFragmentList = new ArrayList<>();
     private final List<String> mFragmentTitleList = new ArrayList<>();

     public ViewPagerAdapter(FragmentManager fm) {
         super(fm);
     }

     @Override
     public Fragment getItem(int i) {
         return mFragmentList.get(i);
     }

     @Override
     public int getCount() {
         return mFragmentList.size();
     }

     public void addFragment(Fragment fragment, String title){
         mFragmentList.add(fragment);
         mFragmentTitleList.add(title);
     }

     @Override
     public CharSequence getPageTitle(int position) {
         return mFragmentTitleList.get(position);
     }
 }


}
