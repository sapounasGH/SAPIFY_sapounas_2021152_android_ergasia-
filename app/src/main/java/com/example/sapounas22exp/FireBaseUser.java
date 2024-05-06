package com.example.sapounas22exp;

public class FireBaseUser {

    private String avatar;
    private  String Musitaste;
    private String Name;
    private String Password;
    private String Surrname;
    private String Username;


    public void setUsername(String username) {
        Username = username;
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
    public String getName() {
        return Name;
    }

    public String getMusitaste() {
        return Musitaste;
    }

    public String getUsername() {
        return Username;
    }

    public String getSurrname() {
        return Surrname;
    }

    public String getPassword() {
        return Password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setMusitaste(String musitaste) {
        Musitaste = musitaste;
    }
}


