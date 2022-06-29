package com.example.smartvillage.entity;

import java.io.Serializable;




public class User implements Serializable {
    private int uid;
    private  String username;
    private  String password;
    private  int admin;


    public User(int uid, String username, String password, int admin) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
