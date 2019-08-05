package com.example.gallery.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gallery.fragment.AlbumFragment;
import com.example.gallery.fragment.MemoryFragment;
import com.example.gallery.fragment.PhotoFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    public TabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PhotoFragment photoFragment = new PhotoFragment();
                return photoFragment;

            case 1:
                AlbumFragment albumFragment = new AlbumFragment(myContext);
                return albumFragment;
            case 2:

                MemoryFragment memoryFragment = new MemoryFragment();
                return memoryFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return totalTabs;
    }
}
