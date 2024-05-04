package com.example.sapounas22exp;

import android.media.MediaPlayer;

public class Mediaplayershare {
    private static MediaPlayer mediaPlayer;

    public Mediaplayershare() {
    }

    public static MediaPlayer getInstance(){
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
        }
        return mediaPlayer;
    }
}
