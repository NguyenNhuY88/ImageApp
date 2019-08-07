package com.example.gallery.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.DatabaseHelper;
import com.example.gallery.R;
import com.example.gallery.activity.MemoryInDetailActivity;
import com.example.gallery.adapter.MemoryAdapter;
import com.example.gallery.model.Memory;

import java.util.ArrayList;
import java.util.List;

public class MemoryFragment extends Fragment {
    private List<Memory> memories = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private MemoryAdapter memoryAdapter;
    public MemoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memory_fragment, container, false);
        prepareData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.memories_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        memoryAdapter = new MemoryAdapter(view.getContext(), memories);
        recyclerView.setAdapter(memoryAdapter);
        return view;
    }

    private void prepareData() {
        memories.clear();
        Cursor cursor = DatabaseHelper.getInstance(getContext()).getAllMemories();
        while (cursor.moveToNext()) {
            Memory memory = new Memory();
            memory.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID)));
            memory.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEMORY_NAME)));
            memory.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.MEMORY_DATE)));
            memory.setPlace(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MEMORY_PLACE)));
            memories.add(memory);
        }
    }
}
