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
    private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_in_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        loadData(getApplicationContext());
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        photosAdapter = new PhotoAdapter(photos, getApplicationContext());
        recyclerView.setAdapter(photosAdapter);


    }
    private void loadData(Context context) {
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
            photo.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)));
            photos.add(photo);
        }
        cursor.close();
    }
}
