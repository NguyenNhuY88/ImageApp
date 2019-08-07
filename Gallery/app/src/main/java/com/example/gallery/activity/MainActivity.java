package com.example.gallery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.gallery.DatabaseHelper;
import com.example.gallery.R;
import com.example.gallery.adapter.TabLayoutAdapter;
import com.example.gallery.model.Memory;
import com.example.gallery.model.Photo;
import com.google.android.material.tabs.TabLayout;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_PERMISSION_KEY = 1;
    TabLayout tabLayout;
    ViewPager viewPager;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseHelper.getInstance(this);
        db.onUpgrade(db.getWritableDatabase(),1,1);
        Memory m1 = new Memory("Memory 1", 636922656000000000L, "Ha Noi");
        Memory m2 = new Memory("Memory 2", 636922656000000000L, "Ho Chi Minh");
        Memory m3 = new Memory("Memory 3", 636922656000000000L, "Hai Phong");
        db.insertMemory(m1);
        db.insertMemory(m2);
        db.insertMemory(m3);
        loadData(this);
        tabLayout = (TabLayout)findViewById(R.id.tab_main);
        viewPager = (ViewPager)findViewById(R.id.view_main);
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
        Intent intent = getIntent();
        int curTab = intent.getIntExtra("CURRENT_TAB", 0);
        TabLayout.Tab tab = tabLayout.getTabAt(curTab);
        tab.select();
//        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
    }

    private void loadData(Context context) {
        ArrayList<Photo> photos = new ArrayList<>();
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorExternal = contentResolver.query(uriExternal, projection, null, null, null);
        Cursor cursorInternal = contentResolver.query(uriInternal, projection, null, null, null);

//        Cursor cursorExternal = context.getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
//                null, null);
//        Cursor cursorInternal = context.getContentResolver().query(uriInternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
//                null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});

        while (cursor.moveToNext()) {
            Photo photo = new Photo();
            photo.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
            photo.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
            photo.setCreatedDate(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)));
            photo.setMemoryId(photo.getAlbum().equals("Download") ? 1 : 2);
            photos.add(photo);
            db.insertPhoto(photo);
        }
        cursor.close();

    }
}
