package com.example.gallery.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import com.example.gallery.DatabaseHelper;
import com.example.gallery.adapter.PhotoAdapter;
import com.example.gallery.model.Memory;
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
    private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_in_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadData(this, getIntent().getIntExtra("MEMORY_ID", 0));
        ImageButton backBtn = this.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("CURRENT_TAB", 2);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.photo_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        photosAdapter = new PhotoAdapter(photos, getApplicationContext());
        recyclerView.setAdapter(photosAdapter);


    }
    private void loadData(Context context, int memoryId) {
        photos.clear();
        Cursor cursor = DatabaseHelper.getInstance(context).getPhotosByMemory(memoryId);
        while (cursor.moveToNext()) {
            Photo photo = new Photo();
            photo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TITLE)));
            photo.setCreatedDate(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CREATED_DATE)));
            photo.setPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATH)));
            photo.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ALBUM)));
            photo.setMemoryId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MEMORY_ID)));
            photos.add(photo);
        }
        cursor.close();
    }
}
