package com.example.smartvillage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartvillage.dto.DCard;
import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.utils.BitmapUtils;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddCardActivity extends AppCompatActivity {
    private static final int addpphotos = 503;
    private Button addPhotos,addFinish;
    private TextInputEditText title,content;
    private TextInputLayout titleLayout,contentLayout;
    private String baseImage;
    private DCard dCard;
    private boolean confirmFlag;
    private String token;
    private SharedPreferences sharedPreferences;
    private Jwt jwtInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        //初始化
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        jwtInfo = JwtUtils.getJwtInfo(token);
        titleLayout = findViewById(R.id.add_card_title);
        contentLayout = findViewById(R.id.add_card_content);
        addPhotos = findViewById(R.id.add_photos);

        addFinish = findViewById(R.id.add_finish);
        title = findViewById(R.id.add_title);
        content = findViewById(R.id.add_content);
        dCard = (DCard) getIntent().getSerializableExtra("Dcard");
        confirmFlag = false;



        if( dCard!= null){
            title.setText(dCard.getTitle());
            content.setText(dCard.getContent());

        }
        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this,AddPhotosActivity.class);
                startActivityForResult(intent,addpphotos);
            }
        });
        addFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().length() == 0 || content.getText().length() == 0){
                    if(title.getText().length() == 0){
                        titleLayout.setError("标题不能为空");
                    }
                    if(content.getText().toString().length() == 0){
                        contentLayout.setError("内容不能为空");
                    }
                    return;

                }
                if(title.getText().length() >titleLayout.getCounterMaxLength() || content.getText().length()>contentLayout.getCounterMaxLength()){
                    if (title.getText().length() >titleLayout.getCounterMaxLength()){
                        titleLayout.setError("标题数字过多");
                    }
                    if(content.getText().length()>contentLayout.getCounterMaxLength()){
                        contentLayout.setError("内容数字过多");
                    }
                    return;

                }
                new Thread(()->{
                    //如果是第一次插入
                    if(dCard == null && !confirmFlag){
                        confirmFlag = true;
                        System.out.println(baseImage);
                        Boolean judge = CardHelper.InsertCard(token,title.getText().toString(), content.getText().toString(), jwtInfo.getUsername(), baseImage);
                        System.out.println(judge);
                        if(judge){
                            Looper.prepare();
                            showMsg("上传成功");
                            finish();
                            Looper.loop();

                        }else{
                            Looper.prepare();
                            showMsg("上传失败");
                            Looper.loop();

                        }
                    }else if(!confirmFlag){
                        confirmFlag = true;
                        //如果获取的image长度不为零
                        if(baseImage != null){
                            dCard.setImage(baseImage);
                        }
                        boolean judge = CardHelper.updateCard(token,dCard);
                        if(judge){
                            Looper.prepare();
                            showMsg("更新成功");
                            finish();
                            Looper.loop();

                        }else{
                            Looper.prepare();
                            showMsg("更新失败");
                            confirmFlag = false;
                            Looper.loop();
                        }
                    }else{
                        Looper.prepare();
                        showMsg("正在上传，请麻烦耐心等待");
                        Looper.loop();
                    }
                }).start();

            }
        });
    title.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            titleLayout.setError(null);

            System.out.println(title.getText());
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(dCard != null){
                dCard.setTitle(title.getText().toString());
            }

        }
    });
    content.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            contentLayout.setError(null);

            System.out.println(content.getText());
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(dCard != null){
                dCard.setContent(content.getText().toString());
            }

        }
    });
    }
    private void showMsg(String msg){
        Toast.makeText(AddCardActivity.this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case addpphotos:
                if(resultCode == RESULT_OK){
                    byte[] basePhotos = data.getByteArrayExtra("basePhotos");
                    Bitmap picFromBytes = BitmapUtils.getPicFromBytes(basePhotos, null);
                    baseImage = BitmapUtils.bitmapToBase64(picFromBytes);
//                    baseImage =android.util.Base64.encodeToString(baseImage.getBytes(), Base64.DEFAULT);
                    baseImage = "data:image/jpeg;base64,"+baseImage;
                    Log.i("t",baseImage);
                }
                break;
        }
    }



}