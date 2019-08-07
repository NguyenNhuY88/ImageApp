package com.example.gallery.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
import com.example.gallery.adapter.MemoryAdapter;
import com.example.gallery.adapter.PhotoAdapter;
import com.example.gallery.model.Memory;
import com.example.gallery.model.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PhotoFragment extends Fragment {
    private List<Photo> memories = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private PhotoAdapter memoryAdapter;
    public PhotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.photos_list);
        recyclerView.setHasFixedSize(true);
        loadData(view.getContext());
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        memoryAdapter = new PhotoAdapter(memories, getContext());
        recyclerView.setAdapter(memoryAdapter);
        return view;
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
            photo.setCreatedDate(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)));
            memories.add(photo);
        }
        cursor.close();
    }
}
