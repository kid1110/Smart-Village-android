package com.example.smartvillage.entity;

import com.example.smartvillage.dto.MessageUser;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {
    private String id;
    private String text;
    private MessageUser user;
    private Date date;

    public Message(String id, String text, MessageUser user, Date date) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.date = date;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public IUser getUser() {
        return this.user;
    }

    @Override
    public Date getCreatedAt() {
        return this.date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
