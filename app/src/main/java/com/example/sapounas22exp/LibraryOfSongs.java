package com.example.sapounas22exp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    public static TextView  sn, an;
    final boolean[] voi = {true};
    private MediaPlayer mediaPlayer = Mediaplayershare.getInstance();
    public static int playingnow=-1,playnext=-1,maxid=-1,playprevious=-1;
    public static ImageButton ppb;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_of_songs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.biglibrary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            lLayout = findViewById(R.id.Library2);
            myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "SongDBf").allowMainThreadQueries().build();
            List<songs> songsQ = MainActivity.myAppDatabase.myDao().getsongs();
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
                    }
                });
            }
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
}