package com.example.smartvillage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class HallActivity extends AppCompatActivity {
    private LinearLayout illness,fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        init();
        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HallActivity.this, PayFeeActivity.class);
                startActivity(intent);
            }
        });
        illness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HallActivity.this, IllnessCenterActivity.class);
                startActivity(intent);
            }
        });

    }

    void init(){
        illness =findViewById(R.id.hall_illness_first);
        fee = findViewById(R.id.hall_fee_first);
    }



}