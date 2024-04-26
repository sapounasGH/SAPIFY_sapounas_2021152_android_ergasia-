package com.example.sapounas22exp.database;

import  androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {songs.class}, version = 1)
public abstract class MyAppDatabase extends  RoomDatabase{
    public abstract MyDao myDao();
}
