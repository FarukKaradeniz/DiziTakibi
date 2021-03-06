package com.omeerfk.dizitakibi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.omeerfk.dizitakibi.fragments.FavoritesFragment;
import com.omeerfk.dizitakibi.fragments.MostPopularFragment;
import com.omeerfk.dizitakibi.fragments.SearchFragment;

import org.greenrobot.eventbus.EventBus;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager();
        tab.setupWithViewPager(viewPager);
    }

    private void setupViewPager(){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh:
                EventBus.getDefault().post("REFRESH");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
