package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;

public class IllnessCenterActivity extends AppCompatActivity {
    private LinearLayout code,order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness_center);
        init();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IllnessCenterActivity.this,CheckOrderActivity.class);
                startActivity(intent);
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IllnessCenterActivity.this,ShowCodeActivity.class);
                startActivity(intent);
            }
        });
    }
    void init(){
        code = findViewById(R.id.illness_code_first);
        order = findViewById(R.id.illness_order_first);
    }
}