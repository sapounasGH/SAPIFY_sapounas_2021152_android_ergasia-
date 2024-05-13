package com.example.sapounas22exp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Lhist")
public class Lhistory {
        @PrimaryKey(autoGenerate = true)
        private int id;
        @ColumnInfo(name = "LHid")
        private int LHid;
        @ColumnInfo(name = "LHsong_name")
        private String LHsname;
        @ColumnInfo(name = "LHsong_artist")
        private String LHsartist;
        @ColumnInfo(name = "LHmp3filepath")
        private String LHmp3filepath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLHid(int LHid) {
        this.LHid = LHid;
    }

    public void setLHsartist(String LHsartist) {
        this.LHsartist = LHsartist;
    }

    public void setLHmp3filepath(String LHmp3filepath) {
        this.LHmp3filepath = LHmp3filepath;
    }

    public void setLHsname(String LHsname) {
        this.LHsname = LHsname;
    }

    public int getLHid() {
        return LHid;
    }

    public String getLHsname() {
        return LHsname;
    }

    public String getLHsartist() {
        return LHsartist;
    }

    public String getLHmp3filepath() {
        return LHmp3filepath;
    }
}
