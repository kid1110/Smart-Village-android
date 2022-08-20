package com.example.smartvillage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.Order;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOrderActivity extends AppCompatActivity {
    private TextInputEditText whereText;
    private TextInputLayout where;
    private TextView dateShow;
    private Button addTime,confirm;
    private MaterialDatePicker<Long> selectDate;
    private String getDate,token,address;
    private SharedPreferences sharedPreferences;
    private Jwt jwt;
    private Order preOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        init();
        System.out.println(preOrder);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate.show(getSupportFragmentManager(),"tag");
            }
        });
        if(getDate == null || getDate.isEmpty()){
            dateShow.setText("请选择日期");
        }
        selectDate.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                System.out.println(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//要转换的时间格式
                Date date;
                try {
                    date =sdf.parse(sdf.format(selection));
                    getDate =  sdf.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateShow.setText(getDate);

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = whereText.getText().toString();
                if(address == null || address.isEmpty()){
                    where.setError("请输入地址");
                    return;
                }else if(getDate == null || getDate.isEmpty()){
                    Toast.makeText(CheckOrderActivity.this,"请选择日期",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    new Thread(()->{
                        if(preOrder != null){
                            Order order = new Order(preOrder.getOid(),jwt.getUid(),address,preOrder.getTypeId(),getDate);
                            BaseResponse baseResponse = OrderHelper.updateOrder(token,order);
                            if (baseResponse.getCode() == Status.Success.getCode()){
                                Looper.prepare();
                                Toast.makeText(CheckOrderActivity.this,"修改预约成功",Toast.LENGTH_LONG).show();
                                finish();
                                Looper.loop();
                            }else{
                                Looper.prepare();
                                Toast.makeText(CheckOrderActivity.this,baseResponse.getMsg(),Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }else{
                            Order order = new Order(jwt.getUid(),address,1,getDate);
                            BaseResponse baseResponse = OrderHelper.insertOrder(token,order);
                            if(baseResponse.getCode() == Status.Success.getCode()){
                                Looper.prepare();
                                Toast.makeText(CheckOrderActivity.this,"预约成功",Toast.LENGTH_LONG).show();
                                finish();
                                Looper.loop();
                            }else{
                                Looper.prepare();
                                Toast.makeText(CheckOrderActivity.this,baseResponse.getMsg(),Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }

            }
        });

        whereText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                where.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void init(){
        where = findViewById(R.id.check_illness_where);
        whereText = findViewById(R.id.check_illness_where_text);
        addTime = findViewById(R.id.add_illness_time);
        dateShow = findViewById(R.id.illness_show_date);
        confirm = findViewById(R.id.illness__order_confirm);
        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        jwt = JwtUtils.getJwtInfo(token);
        CalendarConstraints builder = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build();
        selectDate = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(builder)
                .setTitleText("Select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        preOrder = (Order) intent.getSerializableExtra("Order");
        if(preOrder != null){
            getDate = preOrder.getOrderDate();
            whereText.setText(preOrder.getAddress());
            dateShow.setText(getDate);
        }
    }
}