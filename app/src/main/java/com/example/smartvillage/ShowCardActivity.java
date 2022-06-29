package com.example.smartvillage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartvillage.dto.DCard;
import com.example.smartvillage.entity.Card;
import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.User;
import com.example.smartvillage.utils.BitmapUtils;
import com.example.smartvillage.utils.JsonUtils;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ShowCardActivity extends AppCompatActivity {
    private TextView title,author,timestamp,content,time;
    private SharedPreferences sharedPreferences;
    private Jwt user;
    private Card card;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    private ImageView cardImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user",null);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        //取用户信息
       user = (Jwt) JwtUtils.getJwtInfo(userJson);

        Intent intent = this.getIntent();
        card = (Card) intent.getSerializableExtra("showCard");


        title = (TextView) this.findViewById(R.id.show_card_title);
        author = (TextView)this.findViewById(R.id.show_card_author);
        content = (TextView)this.findViewById(R.id.show_card_content);
        time = (TextView)this.findViewById(R.id.show_card_time);
        cardImage = (ImageView) this.findViewById(R.id.show_cardImage);

        if(card.getImage()!= null && !card.getImage().isEmpty()){
            Glide.with(this).load("http://112.74.93.38:8899/cardImages/"+card.getImage()).centerCrop().into(cardImage);
        }else{
            Glide.with(this).load(R.drawable.test).centerCrop().into(cardImage);
        }
        title.setText(card.getTitle());
        author.setText(card.getAuthor());
        time.setText(card.getTimeStamp());
        content.setText(card.getContent());
        System.out.println(card);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("showCard"+user);

        if(user.getUsername().equals(card.getAuthor()) && user.getAdmin() ==1){
            getMenuInflater().inflate(R.menu.control_card,menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                DeleteCard(card.getCid());
                break;
            case R.id.menu2:
                //TODO
                DCard dCard = new DCard(card.getCid(),card.getImage(),card.getTitle(),card.getAuthor(),card.getContent(),card.getTimeStamp());
                updateCard(dCard);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    void DeleteCard(Integer did){
        materialAlertDialogBuilder.setTitle(getResources().getString(R.string.delete_card_confirm)).setMessage(R.string.delete_card_message).setNegativeButton(getResources().getString(R.string.delete_card_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //销毁内容
                dialog.dismiss();
            }
        }).setPositiveButton(getResources().getString(R.string.delete_card_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("确定了");
                System.out.println("dia"+did);
                //对后台进行请求
                new Thread(()->{
                    boolean judge = CardHelper.deleteCard(did);
                    if(judge){
                        Looper.prepare();
                        Toast.makeText(ShowCardActivity.this,"删除公告成功",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        finish();
                        Looper.loop();

                    }else{
                        Looper.prepare();
                        Toast.makeText(ShowCardActivity.this,"删除公告失败",Toast.LENGTH_LONG).show();
                        Looper.loop();
                        dialog.dismiss();
                    }
                }).start();
            }
        }).create();
        materialAlertDialogBuilder.show();
    }


    void updateCard(DCard card){
        Intent intent = new Intent(ShowCardActivity.this, AddCardActivity.class);
        intent.putExtra("Dcard",card);
        startActivity(intent);
        finish();
    }


}