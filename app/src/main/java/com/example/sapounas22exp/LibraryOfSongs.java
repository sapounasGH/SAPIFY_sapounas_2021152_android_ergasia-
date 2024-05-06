package com.example.sapounas22exp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import com.example.sapounas22exp.database.MyAppDatabase;
import com.example.sapounas22exp.database.songs;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

public class LibraryOfSongs extends AppCompatActivity {

    LinearLayout lLayout;
    public static com.example.sapounas22exp.database.MyAppDatabase myAppDatabase;

    private DrawerLayout drawerLayout;
    TextView songD;
    final boolean[] voi = {true};
    MediaPlayer mediaPlayer=Mediaplayershare.getInstance();
    public static int playingnow=-1,playnext=-1,maxid=-1,playprevious=-1;
    public TextView liban,libsn;
    Button getK,getF,get5;
    List<songs> songsQ2;

    RelativeLayout lsd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_of_songs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.biglibrary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            liban = findViewById(R.id.SongArtlibrary);
            libsn = findViewById(R.id.Songnamelibrary);
            String lsn=getIntent().getStringExtra("songname");
            String lan=getIntent().getStringExtra("artistname");
            libsn.setText(lsn);
            liban.setText(lan);
            lLayout = findViewById(R.id.Library2);
            myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "SongDBf").allowMainThreadQueries().build();
            List<songs> songsQ = MainActivity.myAppDatabase.myDao().getsongs();
            ImageButton ppblib= findViewById(R.id.PLayPausebLibrary);
            if (mediaPlayer.isPlaying()){
                ppblib.setImageResource(android.R.drawable.ic_media_pause);
            }

            ppblib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (voi[0] == true) {
                        if ((mediaPlayer!=null)){
                            mediaPlayer.start();
                            ppblib.setImageResource(android.R.drawable.ic_media_pause);
                            voi[0] = false;
                        }
                    } else {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            ppblib.setImageResource(android.R.drawable.ic_media_play);
                            voi[0] = true;
                        }
                    }
                }
            });

            for (songs i : songsQ) {
                int code = i.getId();
                if (maxid<=code){
                    maxid=code;
                }
                String SongN = i.getSname();
                String SArtistName = i.getSartist();
                String Smp3path = i.getMp3filepath();
                View view = getLayoutInflater().inflate(R.layout.card, null);
                songD = view.findViewById(R.id.txtquery);
                songD.setText(String.format("%s\n%s", SongN, SArtistName));
                lLayout.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlaySong(Smp3path);
                        playingnow=i.getId();
                        if (playingnow==maxid){
                            playnext=0;
                        }else {playnext=playingnow+1;}
                        if (playingnow==0){
                            playprevious=maxid;
                        }else {playprevious=playingnow-1;}
                        libsn.setText(SongN);
                        liban.setText(SArtistName);
                        ppblib.setImageResource(android.R.drawable.ic_media_pause);
                        voi[0] = false;
                    }
                });
            }

            getK=findViewById(R.id.getK);
            getF=findViewById(R.id.getF);
            get5=findViewById(R.id.get5);
            getK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lLayout.removeAllViews();
                    songsQ2=MainActivity.myAppDatabase.myDao().getKanyeWestSongs();
                    for (songs i : songsQ2) {
                        int code = i.getId();
                        if (maxid<=code){
                            maxid=code;
                        }
                        String SongN = i.getSname();
                        String SArtistName = i.getSartist();
                        String Smp3path = i.getMp3filepath();
                        View view = getLayoutInflater().inflate(R.layout.card, null);
                        songD = view.findViewById(R.id.txtquery);
                        songD.setText(String.format("%s\n%s", SongN, SArtistName));
                        lLayout.addView(view);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PlaySong(Smp3path);
                                playingnow=i.getId();
                                if (playingnow==maxid){
                                    playnext=0;
                                }else {playnext=playingnow+1;}
                                if (playingnow==0){
                                    playprevious=maxid;
                                }else {playprevious=playingnow-1;}
                                libsn.setText(SongN);
                                liban.setText(SArtistName);
                                ppblib.setImageResource(android.R.drawable.ic_media_pause);
                                voi[0] = false;
                            }
                        });
                    }
                }
            });

            getF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songsQ2=MainActivity.myAppDatabase.myDao().getFeature();
                    lLayout.removeAllViews();
                    for (songs i : songsQ2) {
                        int code = i.getId();
                        if (maxid<=code){
                            maxid=code;
                        }
                        String SongN = i.getSname();
                        String SArtistName = i.getSartist();
                        String Smp3path = i.getMp3filepath();
                        View view = getLayoutInflater().inflate(R.layout.card, null);
                        songD = view.findViewById(R.id.txtquery);
                        songD.setText(String.format("%s\n%s", SongN, SArtistName));
                        lLayout.addView(view);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PlaySong(Smp3path);
                                playingnow=i.getId();
                                if (playingnow==maxid){
                                    playnext=0;
                                }else {playnext=playingnow+1;}
                                if (playingnow==0){
                                    playprevious=maxid;
                                }else {playprevious=playingnow-1;}
                                libsn.setText(SongN);
                                liban.setText(SArtistName);
                                ppblib.setImageResource(android.R.drawable.ic_media_pause);
                                voi[0] = false;
                            }
                        });
                    }
                }
            });

            get5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lLayout.removeAllViews();
                    songsQ2=MainActivity.myAppDatabase.myDao().getfirst();
                    for (songs i : songsQ2) {
                        int code = i.getId();
                        if (maxid<=code){
                            maxid=code;
                        }
                        String SongN = i.getSname();
                        String SArtistName = i.getSartist();
                        String Smp3path = i.getMp3filepath();
                        View view = getLayoutInflater().inflate(R.layout.card, null);
                        songD = view.findViewById(R.id.txtquery);
                        songD.setText(String.format("%s\n%s", SongN, SArtistName));
                        lLayout.addView(view);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PlaySong(Smp3path);
                                playingnow=i.getId();
                                if (playingnow==maxid){
                                    playnext=0;
                                }else {playnext=playingnow+1;}
                                if (playingnow==0){
                                    playprevious=maxid;
                                }else {playprevious=playingnow-1;}
                                libsn.setText(SongN);
                                liban.setText(SArtistName);
                                ppblib.setImageResource(android.R.drawable.ic_media_pause);
                                voi[0] = false;
                            }
                        });
                    }
                }
            });

            lsd=findViewById(R.id.LibrarySongDisplay);
            lsd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LibraryOfSongs.this, songdis.class);
                    intent.putExtra("songname", libsn.getText());
                    intent.putExtra("artistname", liban.getText());
                    intent.putExtra("playingnow", playingnow);
                    intent.putExtra("playnext",playnext);
                    intent.putExtra("playprev", playprevious);
                    startActivity(intent);
                }
            });

            return insets;
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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()) {
            MainActivity.sn.setText(libsn.getText());
            MainActivity.an.setText(liban.getText());
            MainActivity.ppb.setImageResource(android.R.drawable.ic_media_pause);
        }
    }
}