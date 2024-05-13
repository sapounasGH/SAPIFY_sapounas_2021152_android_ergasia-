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

    @Insert
    public void addlikedSong(likedsong likedsong);

    @Insert
    public void addLhistory(Lhistory lhistory);

    @Insert
    public void addliked(likedsong likedsong);

    @Query("select * from song")
    public List<songs> getsongs();
    @Query("UPDATE likedsong SET liked=true WHERE likedid=:id")
    void updateTLiked(int id);
    @Query("UPDATE likedsong SET liked=false WHERE likedid=:id")
    void updateFLiked(int id);
    @Query("select * from likedsong")
    public List<likedsong> getlikedsong();
    @Query("select * from Lhist")
    public  List<Lhistory> getLhistory();
    @Delete
    public void deleteLHSong(Lhistory lhistory);
    @Delete
    public void deleteSong(songs songs);
    @Delete
    public void deleteLiked(likedsong likedsong);
    @Query("SELECT * FROM song WHERE song_artist LIKE 'Kanye West%'")
    public List<songs> getKanyeWestSongs();

    @Query("SELECT * FROM song WHERE id <5")
    public List<songs> getfirst();

    @Query("SELECT * FROM song WHERE song_artist LIKE '%Ft.%'")
    public List<songs> getFeature();
}
