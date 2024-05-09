package com.example.musicplayer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MusicView extends Fragment {

    private final MainActivity activity;
    private ViewPager2 viewpager2;

    public MusicView(MainActivity activity) {this.activity=activity;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_music_view, container, false);
        viewpager2=fragment.findViewById(R.id.music_pager);
        viewpager2.setAdapter(new MusicPager(activity));
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                activity.tabLayout.selectTab(activity.tabLayout.getTabAt(position)); }
        });
        return fragment;
    }

    public void changePage(int i){
        viewpager2.setCurrentItem(i,false);
    }

}