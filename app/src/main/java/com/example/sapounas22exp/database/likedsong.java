package com.example.sapounas22exp.database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "likedsong",
        primaryKeys = {"likedid"},
        foreignKeys = {
                @ForeignKey(entity = songs.class,
                        parentColumns = "id",
                        childColumns = "likedid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),})
public class likedsong {
    @ColumnInfo (name = "likedid") @NonNull
    private int likedid;

    @ColumnInfo (name = "liked") @NonNull
    private boolean liked;

    public int getLikedid() {
        return likedid;
    }

    public void setLikedid(int likedid) {
        this.likedid = likedid;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
