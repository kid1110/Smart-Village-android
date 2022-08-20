package com.example.smartvillage.client;

import java.net.URI;


public class ChatHelper {

    private ChatWebSocketClient chatWebSocketClient;


    public ChatHelper(String username) {
        URI url = URI.create("ws://112.74.93.38:8899/websocket/"+username);
       this.chatWebSocketClient = new ChatWebSocketClient(url);
    }

    public ChatWebSocketClient getChatWebSocketClient() {
        return this.chatWebSocketClient;
    }
}
