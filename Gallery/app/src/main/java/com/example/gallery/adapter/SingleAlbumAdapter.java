package com.example.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gallery.R;

public class SingleAlbumAdapter extends BaseAdapter {

    private String[] data;
    Context mContext;


    public SingleAlbumAdapter(String[] data, Context mContext) {
            this.data = data;
            this.mContext = mContext;
    }

    @Override
    

    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SingleAlbumViewHolder holder = null;
        if (view == null) {
            holder = new SingleAlbumViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.single_album_row, viewGroup, false);

            holder.textView = (TextView) view.findViewById(R.id.tv_1);

            view.setTag(holder);
        } else {
            holder = (SingleAlbumViewHolder) view.getTag();
        }

        String tmp = data[i];
        holder.textView.setText(tmp);

        return view;
    }
    static class SingleAlbumViewHolder {
        TextView textView;
    }
}
