package com.example.smartvillage.dto;

import com.stfalcon.chatkit.commons.models.IUser;

public class MessageUser implements IUser {
    private String uuid;
    private String username;
    private String avatar;


    public MessageUser(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "MessageUser{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
