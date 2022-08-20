package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.smartvillage.client.ChatHelper;
import com.example.smartvillage.client.ChatWebSocketClient;
import com.example.smartvillage.dto.MessageUser;
import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.Message;
import com.example.smartvillage.entity.User;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.JwtUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.java_websocket.WebSocket;
import org.java_websocket.enums.ReadyState;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommunityActivity extends AppCompatActivity {
    private ChatHelper chatHelper;
    private MessageInput messageInput;
    private MessagesList messagesList;
    private MessagesListAdapter<Message> adapter;
    private MessageUser user;
    private SharedPreferences sharedPreferences;
    private String token;
    private Jwt userInfo;
    private Message message;
    private ChatWebSocketClient chatWebSocketClient;
    private TimerTask Msgtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        init();
        new Thread(()->{
            while (!chatWebSocketClient.isOpen() || chatWebSocketClient.isClosed()){
                try {
                    chatWebSocketClient.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                message = new Message(userInfo.getUuid(),input.toString(),user,new Date());
                new Thread(()->{
                    //websocket连接
                    if (chatWebSocketClient == null) {
                        return;
                    }
                    if (!chatWebSocketClient.isOpen()) {
                        if (chatWebSocketClient.getReadyState().equals(ReadyState.NOT_YET_CONNECTED)) {
                            try {
                                chatWebSocketClient.connectBlocking();
                            } catch (IllegalStateException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (chatWebSocketClient.getReadyState().equals(ReadyState.CLOSING) || chatWebSocketClient.getReadyState().equals(ReadyState.CLOSED)) {
                            try {
                                chatWebSocketClient.reconnectBlocking();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //包装返回信息
                    Type messageResult = new TypeToken<BaseResponse<Message>>(){}.getType();
                    BaseResponse<Message> returnData = new BaseResponse<>(Status.SendMessage.getCode(),Status.SendMessage.getMsg(),message);
                    String pushMessage = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(returnData, messageResult);
                    chatWebSocketClient.send(pushMessage);
                }).start();
                System.out.println(chatWebSocketClient.getMessages());
                if(chatWebSocketClient.getMessages() != null){
                    adapter.addToEnd(chatWebSocketClient.getMessages(),true);
                }

                adapter.addToStart(message,true);
                return true;
            }
        });
        getOthersMsg();
    }
    void getOthersMsg(){
        Msgtask = new TimerTask(){
            @Override
            public void run() {
                if(chatWebSocketClient.getNewMessage() != null){
                    List<Message> newMsg = new LinkedList<>();
                    newMsg.add(chatWebSocketClient.getNewMessage());
                    adapter.addToEnd(newMsg,true);
                }
            }
        };
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(Msgtask,0,2000, TimeUnit.MILLISECONDS);
    }
    void init(){
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        userInfo = JwtUtils.getJwtInfo(token);
        user = new MessageUser(userInfo.getUuid(),userInfo.getUsername());
        messagesList = findViewById(R.id.messagesList);
        adapter = new MessagesListAdapter<Message>(user.getId(), null);
        adapter.onLoadMore(1,10);
        messagesList.setAdapter(adapter);
        messageInput = findViewById(R.id.input);
        chatHelper = new ChatHelper(userInfo.getUuid());
        chatWebSocketClient = chatHelper.getChatWebSocketClient();


    }
}