package com.example.nhom5.View;

import android.os.Bundle;

import com.example.nhom5.Adapter.PhotoAdapter;
import com.example.nhom5.Model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.nhom5.R;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photo_list);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < 10; i++) photos.add(new Photo("Photo" + i));
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        photosAdapter = new PhotoAdapter(photos, getApplicationContext());
        recyclerView.setAdapter(photosAdapter);
    }
}
