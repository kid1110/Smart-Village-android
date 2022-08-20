package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PayFeeActivity extends AppCompatActivity {
    private TextInputEditText number;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee);
        init();
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data  = number.getText().toString();
                System.out.println("hello: "+data);
                if(data.isEmpty()){
                    number.setError("请输入充值额度");
                }else if(!data.matches("[0-9]+")){
                    number.setError("输入不是数字");
                    return;
                }else{
                    Toast.makeText(PayFeeActivity.this,"支付成功!",Toast.LENGTH_LONG).show();
                }

            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void init(){
        number = findViewById(R.id.pay_bill_number);
        payButton = findViewById(R.id.pay_bill_comfirm);
    }
}