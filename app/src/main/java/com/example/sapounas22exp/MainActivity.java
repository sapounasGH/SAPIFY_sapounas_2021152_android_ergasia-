package com.example.sapounas22exp;

import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import android.widget.TextView;
import com.example.sapounas22exp.R.id;
import com.example.sapounas22exp.database.MyAppDatabase;
import com.example.sapounas22exp.database.songs;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;

    final static int RQS_PERMISSION_READ_EXTERNAL_STORAGE = 4;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 5;
    LinearLayout lLayout;
    private DrawerLayout drawerLayout;
    TextView songD;
    TextView sn, an;
    private MediaPlayer mediaPlayer;
    public static com.example.sapounas22exp.database.MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*edw eiani to Library pou thelw na dhmiourghsw*/
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "SongDBf").allowMainThreadQueries().build();
        List<songs> songsQ = MainActivity.myAppDatabase.myDao().getsongs();
        String result = "";
        lLayout = findViewById(R.id.Library);
        an = findViewById(id.SongArtactivitymain);
        sn = findViewById(id.Songnameactivitymain);
        for (songs i : songsQ) {
            int code = i.getId();
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
                    /*selectedUri= Uri.parse(Smp3path);*/
                    PlaySong(SongN, SArtistName, Smp3path);
                }
            });
        }
        if (checkPermission() == false) {
            requestPermission();
        }
        final RelativeLayout songDisplay = findViewById(R.id.smollSongDisplay);
        songDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, songdis.class);
                startActivity(intent);
            }
        });
        final boolean[] voi = {true};
        final ImageButton ppb = findViewById(R.id.PLayPauseb);
        ppb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voi[0] == true) {
                    ppb.setImageResource(android.R.drawable.ic_media_pause);
                    voi[0] = false;
                } else {
                    ppb.setImageResource(android.R.drawable.ic_media_play);
                    voi[0] = true;
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

    }

    public void PlaySong(String SongN, String SArtistName, String Smp3path) {
        Uri uri = Uri.parse(Smp3path);
        String filePath = null;
        String[] projection = {MediaStore.Audio.Media.DATA};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
