package com.example.sapounas22exp;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import android.database.Cursor;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import com.example.sapounas22exp.database.songs;


import java.io.File;

public class Add_song extends Fragment {
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CODE_OPEN_FILE = 123;

    String namethefile = "";

    public Add_song() {
    }

    public static Add_song newInstance(String param1, String param2) {
        Add_song fragment = new Add_song();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText editText1, editText2, editText3;
    Button submit, delete;
    songs song = new songs();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createNotificationChannel();
        View view = inflater.inflate(R.layout.add_song, container, false);

        Button mp3insert = view.findViewById(R.id.mp3path);
        mp3insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickfile();
            }
        });

        editText1 = view.findViewById(R.id.idET);
        editText2 = view.findViewById(R.id.songnameET);
        editText3 = view.findViewById(R.id.aristET);
        submit = view.findViewById(R.id.addbt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int songid = 0;
                try {
                    songid = Integer.parseInt(editText1.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String songname = editText2.getText().toString();
                String songartist = editText3.getText().toString();
                song.setId(songid);
                song.setSname(songname);
                song.setSartist(songartist);
                MainActivity.myAppDatabase.myDao().addSong(song);
                Toast.makeText(getActivity(), "Your song has been saved :) ", Toast.LENGTH_LONG).show();
                displayNotification();
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        });
        delete = view.findViewById(R.id.deletebt);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_songid = 0;
                try {
                    Var_songid = Integer.parseInt(editText1.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                songs song = new songs();
                song.setId(Var_songid);
                MainActivity.myAppDatabase.myDao().deleteSong(song);
                Toast.makeText(getActivity(), "Song deleted ", Toast.LENGTH_LONG).show();
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        });
        return view;
    }

    /*edw epilegei o xrhsths to file kai to location toy mpainei sto room database*/
    private void pickfile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/mpeg");
        startActivityForResult(intent, REQUEST_CODE_OPEN_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                String fileloc = data.getData().toString();
                Uri uri = Uri.parse(fileloc);
                namethefile = editText2.getText().toString(); /*prepei na kaneis na prosthetei arithmo*/
                song.setMp3filepath(FileManager.saveuriTointernalStorage(getContext(), uri, namethefile + ".mp3"));
            }

        }
    }
    private static final int MY_PERMISSIONS_REQUEST_NOTIFICATION = 100;
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sapify Notifications";
            String description = "Notifications for important updates and alerts from Sapify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void displayNotification() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    MY_PERMISSIONS_REQUEST_NOTIFICATION);
            return;
        }

        Intent intent = new Intent(requireContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Song added")
                .setContentText("Your song has been successfully added to your library")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}