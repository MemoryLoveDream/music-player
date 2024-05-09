package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ArrayList<ArrayList<Music>> mM;
    public MusicView musicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){
        mM=Music.initMM();
        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("歌曲"));
        tabLayout.addTab(tabLayout.newTab().setText("MV"));
        musicView=new MusicView(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,musicView).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override public void onTabSelected(TabLayout.Tab tab) { musicView.changePage(tab.getPosition()); }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void toMusicPlayer(Music music) {
        Intent intent = new Intent();
        intent.setClass(this, MusicPlayer.class);
        intent.putExtra("number",music.number);
        startActivity(intent);
    }

    public void toMVPlayer(Music music) {
        Intent intent = new Intent();
        intent.setClass(this, MVPlayer.class);
        intent.putExtra("mvId",music.rawId);
        intent.putExtra("mvName",music.name);
        startActivity(intent);
    }



}