package com.example.gallery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;

import com.example.gallery.R;
import com.example.gallery.adapter.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_PERMISSION_KEY = 1;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout)findViewById(R.id.tab_main);
        viewPager = (ViewPager)findViewById(R.id.view_main);
        viewPager.setCurrentItem(2);
        tabLayout.addTab(tabLayout.newTab().setText("ALBUM"));
        tabLayout.addTab(tabLayout.newTab().setText("PHOTOS"));
        tabLayout.addTab(tabLayout.newTab().setText("MEMORIES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabLayoutAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
//        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
    }
}
