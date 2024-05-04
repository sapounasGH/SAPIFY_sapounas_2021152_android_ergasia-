package com.example.sapounas22exp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;import android.net.Uri;
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

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import com.example.sapounas22exp.database.songs;


import java.io.File;

public class Add_song extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CODE_OPEN_FILE = 123;

    String namethefile="";
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
    EditText editText1,editText2,editText3;
    Button submit,delete;
    songs song = new songs();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_song, container, false);

        Button mp3insert=view.findViewById(R.id.mp3path);
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
                Toast.makeText(getActivity(),"Your song has been saved :) ",Toast.LENGTH_LONG).show();
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        });
        delete=view.findViewById(R.id.deletebt);
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
                Toast.makeText(getActivity(),"Song deleted ",Toast.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==REQUEST_CODE_OPEN_FILE && resultCode == Activity.RESULT_OK){
            if(data != null && data.getData() != null){
                String fileloc=data.getData().toString();
                Uri uri = Uri.parse(fileloc);
                namethefile = editText2.getText().toString(); /*prepei na kaneis na prosthetei arithmo*/
                song.setMp3filepath(FileManager.saveuriTointernalStorage(getContext(), uri, namethefile+".mp3"));
            }

        }
    }

}