package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.musicplayer.Music;
import java.util.ArrayList;

public class MVList extends BaseAdapter {

    private MainActivity activity;
    private final Context context;
    private final ArrayList<Music> musics;
    private static class ViewHolder {
        LinearLayout ll;
        ImageView iv;
        TextView tv1,tv2;
    }

    public MVList(MainActivity activity,Context context, ArrayList<Music> musics) {
        this.activity = activity;
        this.context = context;
        this.musics = musics; }

    @Override
    public int getCount() { return musics.size(); }

    @Override
    public Object getItem(int i) { return musics.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder v;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mv_item,viewGroup,false);
            v = new ViewHolder();
            v.ll = convertView.findViewById(R.id.music_item);
            v.iv = convertView.findViewById(R.id.mv_picture);
            v.tv1 = convertView.findViewById(R.id.music_name);
            v.tv2 = convertView.findViewById(R.id.music_singer);
            convertView.setTag(v);}
        else { v = (ViewHolder) convertView.getTag(); }

        v.iv.setImageResource(musics.get(i).resId);
        v.tv1.setText(musics.get(i).name);
        v.tv2.setText(musics.get(i).singer);
        v.ll.setOnClickListener(v1 -> activity.toMVPlayer(musics.get(i)));

        return convertView;
    }

}
