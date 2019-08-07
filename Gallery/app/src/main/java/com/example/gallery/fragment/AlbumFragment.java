package com.example.gallery.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gallery.R;
import com.example.gallery.adapter.AlbumAdapter;
import com.example.gallery.utils.Function;
import com.example.gallery.utils.MapComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class AlbumFragment extends Fragment {

    GridView galleryGridView;
    ArrayList<HashMap<String, String>> albumList = new ArrayList<HashMap<String, String>>();
    String album_name = "";
    boolean pickintent = false;

    public AlbumFragment() { }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_fragment, container, false);
        super.onCreate(savedInstanceState);

        galleryGridView = (GridView) view.findViewById(R.id.galleryGridView);
        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = getContext().getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if (dp < 360) {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, getContext().getApplicationContext());
            galleryGridView.setColumnWidth(Math.round(px));
        }
        LoadAlbumImages(view.getContext());

        AlbumAdapter adapter = new AlbumAdapter(getContext(), albumList);
        galleryGridView.setAdapter(adapter);
        return view;
    }

    public void LoadAlbumImages(Context mContext) {

        albumList.clear();
        String path = null;
        String album = null;
        String timestamp = null;
        String countPhoto = null;
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;


        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};
        Cursor cursorExternal = mContext.getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);
        Cursor cursorInternal = mContext.getContentResolver().query(uriInternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});

        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
            countPhoto = Function.getCount(mContext.getApplicationContext(), album);
            albumList.add(Function.mappingInbox(album, path, timestamp, Function.converToTime(timestamp), countPhoto));
        }
        cursor.close();
        Collections.sort(albumList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
    }
}





