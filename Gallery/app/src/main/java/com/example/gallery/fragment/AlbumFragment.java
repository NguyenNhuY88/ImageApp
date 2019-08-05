package com.example.gallery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gallery.R;
import com.example.gallery.adapter.SingleAlbumAdapter;

public class AlbumFragment extends Fragment {

    Context mContext;
    GridView gridView;
    String[] cheeses = {"Parmesan", "Ricotta", "Fontina", "Mozzarella", "Cheddar"};
    SingleAlbumAdapter singleAlbumAdapter;

    public AlbumFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_fragment, container, false);
        super.onCreate(savedInstanceState);
        gridView = (GridView)getActivity().findViewById(R.id.galleryGridView);
        SingleAlbumAdapter singleAlbumAdapter = new SingleAlbumAdapter(cheeses, mContext);
        gridView.setAdapter(singleAlbumAdapter);
        return view;

    }
}
