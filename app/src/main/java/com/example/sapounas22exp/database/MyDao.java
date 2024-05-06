package com.example.sapounas22exp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public void addSong(songs songs);

    @Query("select * from song")
    public List<songs> getsongs();

    @Delete
    public void deleteSong(songs songs);
    @Query("SELECT * FROM song WHERE song_artist LIKE 'Kanye West%'")
    public List<songs> getKanyeWestSongs();

    @Query("SELECT * FROM song WHERE id <5")
    public List<songs> getfirst();

    @Query("SELECT * FROM song WHERE song_artist LIKE '%Ft.%'")
    public List<songs> getFeature();
}
