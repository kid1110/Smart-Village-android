package com.example.smartvillage.client;

import com.example.smartvillage.entity.Message;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

public class ChatWebSocketClient extends WebSocketClient {
    private List<Message> messages;
    private Message newMessage;
    public ChatWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {


    }

    @Override
    public void onMessage(String message) {
        try{
            BaseResponse<List<Message>> test = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(message, BaseResponse.class);
            System.out.println(test.getCode());
            if(test.getCode() == Status.SendWebSocketList.getCode()){
                Type messageResult = new TypeToken<BaseResponse<List<Message>>>(){}.getType();
                BaseResponse<List<Message>> data = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(message, messageResult);
                this.messages = data.getData();
                System.out.println("msgs: "+messages);
            }else if(test.getCode() == Status.SendWebSocketMsg.getCode()){
                Type messageResult = new TypeToken<BaseResponse<Message>>(){}.getType();
                BaseResponse<Message> data = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(message, messageResult);
                this.newMessage = data.getData();
                System.out.println("msg: "+messages);
            }
            ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    public Message getNewMessage() {
        return newMessage;
    }
}
