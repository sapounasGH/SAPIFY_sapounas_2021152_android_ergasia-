package com.example.sapounas22exp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import android.widget.TextView;
import com.example.sapounas22exp.R.id;
import com.example.sapounas22exp.database.Lhistory;
import com.example.sapounas22exp.database.MyAppDatabase;
import com.example.sapounas22exp.database.songs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout lLayout;
    private DrawerLayout drawerLayout;
    TextView songD;
    public static TextView  sn, an;
    public static com.example.sapounas22exp.database.MyAppDatabase myAppDatabase;
    final boolean[] voi = {true};
    private MediaPlayer mediaPlayer = Mediaplayershare.getInstance();
    public static int playingnow=-1,playnext=-1,maxid=-1,playprevious=-1;
    public static ImageButton ppb;

    private NavigationView navigationView;
    public static FirebaseFirestore firestoreDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ppb = findViewById(R.id.PLayPauseb);
        /*edw eiani to Library pou thelw na dhmiourghsw*/
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "SongDBf4").allowMainThreadQueries().build();
        List<songs> songsQ = MainActivity.myAppDatabase.myDao().getsongs();
        lLayout = findViewById(R.id.Library);
        an = findViewById(id.SongArtactivitymain);
        sn = findViewById(id.Songnameactivitymain);
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
                    sn.setText(SongN);
                    an.setText(SArtistName);
                    ppb.setImageResource(android.R.drawable.ic_media_pause);
                    voi[0] = false;
                    saveListenignHistory(i.getId(), SongN, SArtistName, Smp3path);
                }
            });
        }
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

        ImageButton pps = findViewById(R.id.playprevious);
        ImageButton pns = findViewById(R.id.playnext);
        final songs[] whattoplay = {new songs()};
        List<songs> songsQ2 = MainActivity.myAppDatabase.myDao().getsongs();
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
                    sn.setText(whattoplay[0].getSname());
                    an.setText(whattoplay[0].getSartist());
                    PlaySong(whattoplay[0].getMp3filepath());
                    playingnow=whattoplay[0].getId();
                    if (playingnow==maxid){
                        playnext=0;
                    }else {playnext=playingnow+1;}
                    if (playingnow==0){
                        playprevious=maxid;
                    }else {playprevious=playingnow-1;}
                    saveListenignHistory(whattoplay[0].getId(), whattoplay[0].getSname(), whattoplay[0].getSartist(), whattoplay[0].getMp3filepath());
                }
            }
        });
        pps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null){
                    for (songs k : songsQ2) {
                        if (k.getId() == playprevious) {
                            whattoplay[0] = k;
                            break;
                        }
                    }
                    sn.setText(whattoplay[0].getSname());
                    an.setText(whattoplay[0].getSartist());
                    PlaySong(whattoplay[0].getMp3filepath());
                    playingnow=whattoplay[0].getId();
                    if (playingnow==maxid){
                        playnext=0;
                    }else {playnext=playingnow+1;}
                    if (playingnow==0){
                        playprevious=maxid;
                    }else {playprevious=playingnow-1;}
                    saveListenignHistory(whattoplay[0].getId(), whattoplay[0].getSname(), whattoplay[0].getSartist(), whattoplay[0].getMp3filepath());
                }
            }
        });

        ImageButton addsongg = findViewById(R.id.addsong);
        addsongg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_song as = new Add_song();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, as).addToBackStack(null).commit();
            }
        });
        /*edw eiani to drawer*/
        drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton opendr = findViewById(id.menubtn);
        opendr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final RelativeLayout songDisplay = findViewById(R.id.smollSongDisplay);
        songDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, songdis.class);
                intent.putExtra("songname", sn.getText());
                intent.putExtra("artistname", an.getText());
                intent.putExtra("playingnow", playingnow);
                intent.putExtra("playnext",playnext);
                intent.putExtra("playprev", playprevious);
                startActivity(intent);
            }
        });

        navigationView = findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id =menuItem.getItemId();
                if (id == R.id.lib) {
                            Intent intent = new Intent(MainActivity.this, LibraryOfSongs.class);
                            intent.putExtra("songname", sn.getText());
                            intent.putExtra("artistname", an.getText());
                            startActivity(intent);
                } else if (id == R.id.acc) {
                    login lf = new login();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, lf).addToBackStack(null).commit();
                    drawerLayout.closeDrawers();
                } else if (id == R.id.createacc) {
                    CreateAccount lf = new CreateAccount();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, lf).addToBackStack(null).commit();
                    drawerLayout.closeDrawers();
                } else if (id == R.id.Search) {
                    search lf = new search();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, lf).addToBackStack(null).commit();
                    drawerLayout.closeDrawers();
                }
                return true;
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
}
