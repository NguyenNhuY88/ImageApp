package com.example.gallery.activity;

import android.os.Bundle;

import com.example.gallery.adapter.PhotoAdapter;
import com.example.gallery.model.Photo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gallery.R;
import java.util.ArrayList;
import java.util.List;

public class MemoryInDetailActivity extends AppCompatActivity {
    private List<Photo> photos = new ArrayList<>();
    private PhotoAdapter photosAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_in_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photo_list);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < 10; i++) {
            Photo photo = new Photo();
            photo.setTitle("Photo" + i);
            photos.add(photo);
        }
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        photosAdapter = new PhotoAdapter(photos, getApplicationContext());
        recyclerView.setAdapter(photosAdapter);
    }
}
