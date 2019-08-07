package com.example.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
import com.example.gallery.activity.MemoryInDetailActivity;
import com.example.gallery.activity.SquareImageView;
import com.example.gallery.model.Memory;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    private List<Memory> memoryList;
    private Context context;

    public MemoryAdapter(Context context, List<Memory> memoryList) {
        this.memoryList = memoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memory_view_holder, parent, false);
        return new MemoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryViewHolder holder, int position) {
        holder.title.setText(memoryList.get(position).getName());
        holder.place.setText(memoryList.get(position).getPlace());
        holder.date.setText(memoryList.get(position).getDate()+"");
        holder.cover_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemoryInDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public class MemoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView place;
        private TextView date;
        private SquareImageView cover_image;
        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            place = itemView.findViewById(R.id.place);
            date = itemView.findViewById(R.id.date);
            cover_image = itemView.findViewById(R.id.cover_image);
        }
    }
}
