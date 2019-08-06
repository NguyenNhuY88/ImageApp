package com.example.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gallery.R;
import com.example.gallery.utils.Function;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SingleAlbumAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> data;

    public SingleAlbumAdapter(Context mContext, ArrayList<HashMap<String, String>> d) {
        this.mContext = mContext;
        this.data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SingleAlbumViewHolder holder = null;
        if (convertView == null) {
            holder = new SingleAlbumViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.single_album_row, parent, false);

            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);

            convertView.setTag(holder);
        } else {
            holder = (SingleAlbumViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        try {

            Glide.with(mContext)
                    .load(new File(song.get(Function.KEY_PATH))) // Uri of the picture
                    .into(holder.galleryImage);


        } catch (Exception ignored) {
        }
        return convertView;
    }

    static class SingleAlbumViewHolder {
        ImageView galleryImage;
    }
}
