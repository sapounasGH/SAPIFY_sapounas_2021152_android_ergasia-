package com.example.sapounas22exp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sapounas22exp.database.Lhistory;
import com.example.sapounas22exp.database.likedsong;
import com.example.sapounas22exp.database.songs;

import java.io.IOException;
import java.util.List;

public class songdis extends AppCompatActivity {

    MediaPlayer mediaPlayer=Mediaplayershare.getInstance();
    SeekBar seekBar;
    Runnable runnable;
    Handler handler;
    ImageButton likedbutton;
    boolean flag=false;
    public int playingnow=-1,playnext=-1,maxid=-1,playprev=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_songdis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ssdis), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        seekBar=findViewById(R.id.seekBar);
        handler=new Handler();
        String sn=getIntent().getStringExtra("songname");
        String an=getIntent().getStringExtra("artistname");
        playingnow = getIntent().getIntExtra("playingnow", 0);
        playnext = getIntent().getIntExtra("playnext", 0);
        playprev = getIntent().getIntExtra("playprev", 0);
        TextView textViewsongNAME = findViewById(R.id.songN);
        textViewsongNAME.setText(sn);
        TextView textViewsongARTIST = findViewById(R.id.songA);
        textViewsongARTIST.setText(an);
        likedbutton=findViewById(R.id.like);
        final boolean[] voi = {true};
        final ImageButton ppb = findViewById(R.id.PLayPauseb2);
        ppb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voi[0] == true) {
                    if ((mediaPlayer!=null)){
                        mediaPlayer.start();
                        ppb.setImageResource(android.R.drawable.ic_media_pause);
                        voi[0] = false;
                    }
                } else {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        ppb.setImageResource(android.R.drawable.ic_media_play);
                        voi[0] = true;
                    }
                }
            }
        });
        if (mediaPlayer!=null){
            if (mediaPlayer.isPlaying()){
                ppb.setImageResource(android.R.drawable.ic_media_pause);
                voi[0] = false;
            }else {ppb.setImageResource(android.R.drawable.ic_media_play);
                voi[0] = true;}
        }

        ImageButton pps = findViewById(R.id.prev);
        ImageButton pns = findViewById(R.id.next);
        final songs[] whattoplay = {new songs()};
        List<songs> songsQ2 = MainActivity.myAppDatabase.myDao().getsongs();
        for(songs u:songsQ2){
            int code = u.getId();
            if (maxid<=code){
                maxid=code;
            }
        }
        pns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null){
                    for (songs k : songsQ2) {
                        if (k.getId() == playnext) {
                            whattoplay[0] = k;
                            break;
                        }
                    }
                    textViewsongNAME.setText(whattoplay[0].getSname());
                    textViewsongARTIST.setText(whattoplay[0].getSartist());
                    MainActivity.sn.setText(whattoplay[0].getSname());
                    MainActivity.an.setText(whattoplay[0].getSartist());
                    PlaySong(whattoplay[0].getMp3filepath());
                    playingnow =whattoplay[0].getId();
                    if (playingnow ==maxid){
                        playnext =0;
                    }else {
                        playnext = playingnow +1;}
                    if (playingnow ==0){
                        playprev =maxid;
                    }else {
                        playprev = playingnow -1;}
                    ppb.setImageResource(android.R.drawable.ic_media_pause);
                    voi[0] = false;
                    MainActivity.playingnow=playingnow;
                    MainActivity.playnext=playnext;
                    MainActivity.playprevious=playprev;
                    saveListenignHistory(whattoplay[0].getId(), whattoplay[0].getSname(), whattoplay[0].getSartist(), whattoplay[0].getMp3filepath());
                    updatestar();
                }
            }
        });

        pps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null){
                    for (songs k : songsQ2) {
                        if (k.getId() == playprev) {
                            whattoplay[0] = k;
                            break;
                        }
                    }
                    textViewsongNAME.setText(whattoplay[0].getSname());
                    textViewsongARTIST.setText(whattoplay[0].getSartist());
                    MainActivity.sn.setText(whattoplay[0].getSname());
                    MainActivity.an.setText(whattoplay[0].getSartist());
                    PlaySong(whattoplay[0].getMp3filepath());
                    playingnow =whattoplay[0].getId();
                    if (playingnow ==maxid){
                        playnext =0;
                    }else {
                        playnext = playingnow +1;}

                    if (playingnow ==0){
                        playprev =maxid;
                    }else {
                        playprev = playingnow -1;}
                    ppb.setImageResource(android.R.drawable.ic_media_pause);
                    voi[0] = false;
                    MainActivity.playingnow=playingnow;
                    MainActivity.playnext=playnext;
                    MainActivity.playprevious=playprev;
                    saveListenignHistory(whattoplay[0].getId(), whattoplay[0].getSname(), whattoplay[0].getSartist(), whattoplay[0].getMp3filepath());
                    updatestar();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateSeekbar();
        List<likedsong> songcheck23 = MainActivity.myAppDatabase.myDao().getlikedsong();
        updatestar();
        likedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (likedsong l : songcheck23 ){
                    if ((playingnow == l.getLikedid()) && (l.isLiked())) {
                        likedbutton.setImageResource(android.R.drawable.btn_star_big_off);
                        MainActivity.myAppDatabase.myDao().updateFLiked(l.getLikedid());
                    }else if((playingnow == l.getLikedid()) && (!l.isLiked())) {
                        likedbutton.setImageResource(android.R.drawable.btn_star_big_on);
                        MainActivity.myAppDatabase.myDao().updateTLiked(l.getLikedid());
                    }
                }
            }
        });
    }

    public void PlaySong(String Smp3path) {
        if(mediaPlayer != null){
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(Smp3path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                double ratio= percent/100.0;
                int bufferingLevel=(int)(mp.getDuration()*ratio);
                seekBar.setSecondaryProgress(bufferingLevel);
            }
        });
    }

    public void updateSeekbar(){
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()) {
            MainActivity.ppb.setImageResource(android.R.drawable.ic_media_pause);
        }
    }
    public  void saveListenignHistory(int histid, String SongN, String SArtistName, String Smp3path){
        /*vazoume to song sto listening histori*/
        Lhistory histsong = new Lhistory();
        histsong.setLHid(histid);
        histsong.setLHsname(SongN);
        histsong.setLHsartist(SArtistName);
        histsong.setLHmp3filepath(Smp3path);
        MainActivity.myAppDatabase.myDao().addLhistory(histsong);
    }
    public void updatestar(){
        List<likedsong> songcheck2 = MainActivity.myAppDatabase.myDao().getlikedsong();
        for (likedsong l : songcheck2 ){
            if ((playingnow == l.getLikedid()) && (l.isLiked())) {
                likedbutton.setImageResource(android.R.drawable.btn_star_big_on);
            }else if((playingnow == l.getLikedid()) && (!l.isLiked())) {likedbutton.setImageResource(android.R.drawable.btn_star_big_off);}
        }
    }
}