package com.example.gallery.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.gallery.adapter.SingleAlbumAdapter;
import com.example.gallery.utils.Function;
import com.example.gallery.utils.MapComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class AlbumFragment extends Fragment {

    GridView galleryGridView;
    ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    String album_name = "";
    LoadAlbumImages loadAlbumTask;
    boolean pickintent = false;
    Context mContext;
    public AlbumFragment(Context mContext) {
        this.mContext = mContext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_fragment, container, false);
        super.onCreate(savedInstanceState);
        // album_name="download";
       // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

       /* Intent intent = getIntent();
        album_name = intent.getStringExtra("name");

        //for other app pick intent
        pickintent = intent.getExtras().getBoolean("pickintent", false);

        setTitle(album_name);*/
        galleryGridView = (GridView) view.findViewById(R.id.galleryGridView);
        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = mContext.getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if (dp < 360) {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, mContext.getApplicationContext());
            galleryGridView.setColumnWidth(Math.round(px));
        }


        loadAlbumTask = new LoadAlbumImages();
        loadAlbumTask.execute();
        return view;
    }


    @SuppressLint("StaticFieldLeak")
    class LoadAlbumImages extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageList.clear();
        }

        protected String doInBackground(String... args) {
            imageList.clear();
            String xml = "";

            String path = null;
            String album = null;
            String timestamp = null;
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};

            Cursor cursorExternal = mContext.getContentResolver().query(uriExternal, projection, "bucket_display_name = \"" + album_name + "\"", null, null);
            Cursor cursorInternal = mContext.getContentResolver().query(uriInternal, projection, "bucket_display_name = \"" + album_name + "\"", null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});
            while (cursor.moveToNext()) {

                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

                imageList.add(Function.mappingInbox(album, path, timestamp, Function.converToTime(timestamp), null));
            }
            cursor.close();
            Collections.sort(imageList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            SingleAlbumAdapter adapter = new SingleAlbumAdapter(mContext, imageList);
            galleryGridView.setAdapter(adapter);
//            galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        final int position, long id) {
//                    if (!pickintent) {
//                        Intent intent = new Intent(AlbumActivity.this, GalleryPreview.class);
//                        //intent.putExtra("path", imageList.get(+position).get(Function.KEY_PATH));
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("list", imageList);
//                        intent.putExtras(bundle);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
//                    } else {
//                        // Pick image result here
//
//                        String imgPath = imageList.get(+position).get(Function.KEY_PATH);
//                        Uri imguri = Uri.fromFile(new File(imgPath));
//                        Intent intentResult = new Intent();
//                        intentResult.setData(imguri);
//                        setResult(Activity.RESULT_OK,intentResult);
//                        finish();
//                    }
//                }
//            });
        }
    }
}
