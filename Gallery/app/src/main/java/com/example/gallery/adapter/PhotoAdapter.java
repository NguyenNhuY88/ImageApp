package com.example.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
import com.example.nhom5.Model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view_holder, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.title.setText(photos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
