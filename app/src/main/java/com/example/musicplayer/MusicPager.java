package com.example.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MusicPager extends RecyclerView.Adapter<MusicPager.ViewHolder> {

    private final MainActivity activity;
    public MusicPager(MainActivity activity) {
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.music_pager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder v, int position) {
        if(position==0) v.listView.setAdapter(new MusicList(activity,v.listView.getContext(),activity.mM.get(position)));
        if(position==1) v.listView.setAdapter(new MVList(activity,v.listView.getContext(),activity.mM.get(position)));
    }

    @Override
    public int getItemCount() { return 2; }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListView listView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listView=itemView.findViewById(R.id.music_list); }
    }

}

