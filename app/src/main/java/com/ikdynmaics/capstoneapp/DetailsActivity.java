package com.ikdynmaics.capstoneapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ikdynmaics.capstoneapp.favourite.FavouriteFragment;
import com.ikdynmaics.capstoneapp.places.PlacesFragment;
import com.ikdynmaics.capstoneapp.weather.WeatherFragment;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFrag(new WeatherFragment(), getResources().getString(R.string.weather));
        adapter.addFrag( PlacesFragment.newInstance("restaurant"), getResources().getString(R.string.restaurant));
        adapter.addFrag( PlacesFragment.newInstance("museum"), getResources().getString(R.string.museum));
        adapter.addFrag(new FavouriteFragment(), getResources().getString(R.string.favourite));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
}
