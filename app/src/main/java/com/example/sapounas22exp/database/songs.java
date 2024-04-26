package com.example.sapounas22exp.database;

import androidx.room.ColumnInfo;
import  androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "song")
public class songs {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "song_name")
    private String sname;

    @ColumnInfo(name = "song_artist")
    private String sartist;

    @ColumnInfo(name = "mp3filepath")
    private String mp3filepath;

    public int getId() {
        return id;
    }

    public String getSname() {
        return sname;
    }

    public String getSartist() {
        return sartist;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSartist(String sartist) {
        this.sartist = sartist;
    }

    public String getMp3filepath() {return mp3filepath;}

    public void setMp3filepath(String mp3filepath) {this.mp3filepath = mp3filepath;}
}
