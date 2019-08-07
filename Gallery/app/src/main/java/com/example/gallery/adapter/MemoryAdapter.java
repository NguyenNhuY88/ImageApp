package com.example.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
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
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public class MemoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.memory_title);
        }
    }
}
