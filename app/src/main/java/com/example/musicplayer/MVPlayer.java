package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MVPlayer extends AppCompatActivity {

    private SurfaceView sv;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private RelativeLayout rl;
    private Timer timer;
    private TimerTask task;
    private SeekBar sb;
    private ImageView back,play;
    private TextView tv1,tv2,tv3;
    private Spinner s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mvplayer);

        sv=findViewById(R.id.surface_view);
        rl=findViewById(R.id.layout);
        back=findViewById(R.id.back);
        play=findViewById(R.id.play);
        sb=findViewById(R.id.sb);
        tv1=findViewById(R.id.music_progress);
        tv2=findViewById(R.id.music_total);
        tv3=findViewById(R.id.music_name);
        s1=findViewById(R.id.speed);

        holder=sv.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Uri uri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"
                            +getPackageName()+"/"+getIntent().getIntExtra("mvId",R.raw.uno));
                    try { mediaPlayer.setDataSource(MVPlayer.this,uri); }
                    catch (IOException e){
                        Toast.makeText(MVPlayer.this,"播放失败",Toast.LENGTH_SHORT).show();
                        e.printStackTrace(); }
                    mediaPlayer.setDisplay(holder);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
                    tv3.setText(getIntent().getStringExtra("mvName"));
                 }
                catch (Exception e){
                    Toast.makeText(MVPlayer.this,"播放失败",Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); }
            }
            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }
            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                if (mediaPlayer.isPlaying()) { mediaPlayer.stop(); }
            }
        });

        back.setOnClickListener(v -> finish());
        play.setOnClickListener(v -> {
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                play.setImageResource(R.drawable.play_one); }
            else {
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause); }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int position=seekBar.getProgress();
                if (mediaPlayer!=null) mediaPlayer.seekTo(position);
            }
        });
        s1.setSelection(1);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changePlayerSpeed((position+1)*0.5f);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                    int total=mediaPlayer.getDuration();
                    sb.setMax(total);
                    int minute1=total/1000/60;
                    int second1=total/1000%60;
                    String strMinute1;
                    String strSecond1;
                    if(minute1<10){strMinute1="0"+minute1;}
                    else {strMinute1=minute1+"";}
                    if(second1<10){strSecond1="0"+second1;}
                    else{strSecond1=second1+"";}
                    tv2.post(() -> tv2.setText(strMinute1 + ":" + strSecond1));
                    int progress=mediaPlayer.getCurrentPosition();
                    sb.setProgress(progress);
                    String strMinute;
                    String strSecond;
                    int minute=progress/1000/60;
                    int second=progress/1000%60;
                    if(minute<10){strMinute="0"+minute;}
                    else {strMinute=minute+"";}
                    if(second<10){strSecond="0"+second;}
                    else{strSecond=second+"";}
                    tv1.post(() -> tv1.setText(strMinute + ":" + strSecond));
                }
                else { play.setImageResource(R.drawable.play_one); }
            }
        };
        timer.schedule(task,500,500);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (rl.getVisibility() == View.INVISIBLE) {
                rl.setVisibility(View.VISIBLE);
                CountDownTimer cdt = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        System.out.println(millisUntilFinished);
                    }
                    @Override
                    public void onFinish() {
                        rl.setVisibility(View.INVISIBLE);
                    }
                };
                cdt.start();
            } else if (rl.getVisibility() == View.VISIBLE) {
                rl.setVisibility(View.INVISIBLE);
            }
        }
        return super.onTouchEvent(event);
    }

    private void changePlayerSpeed(float speed) {
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        task.cancel();
        timer.cancel();
        timer=null;
        task=null;
        mediaPlayer.release();
        mediaPlayer=null;
    }


}