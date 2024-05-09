package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer extends AppCompatActivity {

    private final static int PlayCycle=0,PlayOnce=1,ShuffleOne=2;
    private final ArrayList<ArrayList<Music>> mM = Music.initMM();
    private Music currentMusic;
    private MediaPlayer player;
    private ObjectAnimator animator;
    private Timer timer;
    private TimerTask task;
    private RelativeLayout rl;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private ImageView iv;
    private SeekBar sb;
    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7;
    private int playType=PlayCycle,background=0,like=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        currentMusic=mM.get(0).get(getIntent().getIntExtra("number",1)-1);
        rl=findViewById(R.id.background);
        tv1=findViewById(R.id.music_name);
        tv2=findViewById(R.id.music_type);
        tv3=findViewById(R.id.music_singer);
        tv4=findViewById(R.id.music_progress);
        tv5=findViewById(R.id.music_total);
        sb=findViewById(R.id.sb);
        iv=findViewById(R.id.music_picture);
        ib1=findViewById(R.id.back);
        ib2=findViewById(R.id.play_type);
        ib3=findViewById(R.id.last);
        ib4=findViewById(R.id.play);
        ib5=findViewById(R.id.next);
        ib6=findViewById(R.id.play_list);
        ib7=findViewById(R.id.love);

        tv1.setText(currentMusic.name);
        tv2.setText(currentMusic.type);
        tv3.setText(currentMusic.singer);
        iv.setImageResource(currentMusic.resId);

        player=new MediaPlayer();
        animator= ObjectAnimator.ofFloat(iv,"rotation",0f,360.0f);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        play(currentMusic.rawId);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p=progress/1000/60,m=seekBar.getMax()/1000/60;
                int p1=progress/1000%60,m1=seekBar.getMax()/1000%60;
                if((p==m)&&(p1==m1||p1==(m1-1))){
                    if(playType==PlayCycle) changeMusic(mM.get(0).get(currentMusic.number%10));
                    else if(playType==PlayOnce) changeMusic(mM.get(0).get(currentMusic.number-1));
                    else changeMusic(mM.get(0).get((currentMusic.number+new Random().nextInt(9))%10)); }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
        ib1.setOnClickListener(v -> finish());
        ib2.setOnClickListener(v -> {
            if(playType==PlayCycle) {
                ib2.setImageResource(R.drawable.play_once);
                playType=PlayOnce;}
            else if(playType==PlayOnce) {
                ib2.setImageResource(R.drawable.shuffle_one);
                playType=ShuffleOne;}
            else if(playType==ShuffleOne) {
                ib2.setImageResource(R.drawable.play_cycle);
                playType=PlayCycle;}
        });
        ib3.setOnClickListener(v -> changeMusic(mM.get(0).get((currentMusic.number+8)%10)));
        ib4.setOnClickListener(v -> {
            if(player.isPlaying()) pause();
            else continuePlay();
        });
        ib5.setOnClickListener(v -> changeMusic(mM.get(0).get(currentMusic.number%10)));
        ib6.setOnClickListener(v -> {
            switch (background){
                case 0:
                    changeBackGround(R.mipmap.background_black_fish);
                    changeTextColor(this.getResources().getColor(R.color.white));
                    background=1;
                    break;
                case 1:
                    changeBackGround(R.mipmap.background_pink_ice_cream);
                    changeTextColor(this.getResources().getColor(R.color.white));
                    background=2;
                    break;
                case 2:
                    changeBackGround(R.mipmap.background_tree_shadow);
                    changeTextColor(this.getResources().getColor(R.color.white));
                    background=3;
                    break;
                case 3:
                    changeBackGround(R.mipmap.background_purple);
                    changeTextColor(this.getResources().getColor(R.color.white));
                    background=4;
                    break;
                case 4:
                    changeBackGround(R.mipmap.background_pink_oil_painting);
                    changeTextColor(this.getResources().getColor(R.color.white));
                    background=5;
                    break;
                case 5:
                    changeBackGround(R.color.white);
                    changeTextColor(this.getResources().getColor(R.color.black));
                    background=0;
                    break;
            }
        });
        ib7.setOnClickListener(v -> {
            if(like==0) { ib7.setImageResource(R.drawable.love); like=1; }
            else { ib7.setImageResource(R.drawable.like); like=0; }
        });

        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                if(player!=null&&player.isPlaying()) {
                    int currentPosition=player.getCurrentPosition();
                    sb.setProgress(currentPosition);
                    String strMinute;
                    String strSecond;
                    int minute=currentPosition/1000/60;
                    int second=currentPosition/1000%60;
                    if(minute<10){strMinute="0"+minute;}
                    else {strMinute=minute+"";}
                    if(second<10){strSecond="0"+second;}
                    else{strSecond=second+"";}
                    tv4.post(() -> tv4.setText(strMinute + ":" + strSecond));
                }
            }
        };
        timer.schedule(task,5,500);

    }

    private void play(int id){
        tv1.setText(currentMusic.name);
        tv2.setText(currentMusic.type);
        tv3.setText(currentMusic.singer);
        iv.setImageResource(currentMusic.resId);
        player.reset();
        player=MediaPlayer.create(getApplicationContext(), id);
        player.start();
        int duration=player.getDuration();
        sb.setMax(duration);
        int minute=duration/1000/60;
        int second=duration/1000%60;
        String strMinute;
        String strSecond;
        if(minute<10){strMinute="0"+minute;}
        else {strMinute=minute+"";}
        if(second<10){strSecond="0"+second;}
        else{strSecond=second+"";}
        tv5.setText(strMinute + ":" + strSecond);
        animator.start();
        ib4.setImageResource(R.drawable.pause);
    }

    private void pause(){
        player.pause();
        animator.pause();
        ib4.setImageResource(R.drawable.play_one);
    }

    private void continuePlay(){
        player.start();
        animator.resume();
        ib4.setImageResource(R.drawable.pause);
    }

    private void changeMusic(Music music){
        pause();
        currentMusic=music;
        play(currentMusic.rawId);
    }

    private void changeBackGround(int id){
        rl.setBackgroundResource(id);
    }

    private void changeTextColor(int id){
        tv1.setTextColor(id);
        tv2.setTextColor(id);
        tv3.setTextColor(id);
        tv4.setTextColor(id);
        tv5.setTextColor(id);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        task.cancel();
        timer.cancel();
        timer=null;
        task=null;
        player.release();
        player=null;
    }

}