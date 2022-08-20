package com.example.smartvillage.entity;

import java.io.Serializable;

public class Jwt implements Serializable {
    private Integer uid;
    private Integer exp;
    private Integer iat;
    private  String uuid;
    private String username;
    private Integer admin;

    public Jwt(Integer uid,Integer exp, Integer iat, String uuid, String username, Integer admin) {
        this.exp = exp;
        this.iat = iat;
        this.uuid = uuid;
        this.username = username;
        this.admin = admin;
        this.uid = uid;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Jwt{" +
                "uid=" + uid +
                ", exp=" + exp +
                ", iat=" + iat +
                ", uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", admin=" + admin +
                '}';
    }
}
