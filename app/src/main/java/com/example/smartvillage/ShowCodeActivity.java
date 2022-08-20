package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.smartvillage.utils.QrCodeUtils;

public class ShowCodeActivity extends AppCompatActivity {
    private String token;
    private SharedPreferences sharedPreferences;
    private ImageView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);
        init();
        Bitmap h = QrCodeUtils.createQRCodeBitmap(token, 800, 800, "UTF-8", "H", "1", Color.GREEN, Color.WHITE);
        code.setImageBitmap(h);
    }

    void init(){
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        code = findViewById(R.id.codeImage);
    }
}