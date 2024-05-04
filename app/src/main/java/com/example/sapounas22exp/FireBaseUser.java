package com.example.sapounas22exp;

public class FireBaseUser {
    private String Username;
    private String Surrname;
    private String Password;
    private  String Musitaste;
    private String avatar;
    private String Name;

    public String getUsername() {
        return Username;
    }

    public String getPassWord() {
        return Password;
    }

    public String getMusicTaste() {
        return Musitaste;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassWord(String passWord) {
        Password = passWord;
    }

    public String getSurrname() {
        return Surrname;
    }

    public void setSurrname(String surrname) {
        Surrname = surrname;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return Name;
    }

    public void setMusicTaste(String musicTaste) {
        Musitaste = musicTaste;
    }
}


