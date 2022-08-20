package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.Order;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private static final float dark = 0.5f;
    private static final float bright = 1.0f;
    private RecyclerView orderRecycleView;
    private List<Order> orderCardList;
    private OrderAdapter orderAdapter;
    private TextView ordertip;
    private SharedPreferences sharedPreferences;
    private CircularProgressIndicator progressIndicator;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private Jwt jwt;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orderCardList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        jwt = JwtUtils.getJwtInfo(token);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
    }

    @Override
    protected void onResume() {
        progressIndicator = findViewById(R.id.order_progress_circular);
        super.onResume();
        backgroundAlpha(dark);
        Log.i("progressIndicator", "onResume: "+progressIndicator);
        progressIndicator.setVisibility(View.VISIBLE);
        ordertip = findViewById(R.id.order_text_dashboard);
        new Thread(()->{
            initCard();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!orderCardList.isEmpty()){
                        ordertip.setVisibility(View.INVISIBLE);
                        init();
                        progressIndicator.setVisibility(View.INVISIBLE);
                        backgroundAlpha(bright);
                    }else{
                        ordertip.setText("暂无预约");
                        ordertip.setVisibility(View.VISIBLE);
                        progressIndicator.setVisibility(View.INVISIBLE);
                        backgroundAlpha(bright);
                    }
                }
            });
        }).start();
    }

    void initCard(){
        orderCardList.clear();
        if(jwt.getAdmin() == 1){
            orderCardList = OrderHelper.getAllOrders(token);
        }else{
            orderCardList = OrderHelper.getUserOrders(token,jwt.getUid());
        }
        if(orderCardList == null){
            orderCardList = new ArrayList<>();
        }
    }
    void init(){
        orderRecycleView = (RecyclerView)findViewById(R.id.orderRecycler);
        orderAdapter = new OrderAdapter(OrderListActivity.this, orderCardList);
        orderRecycleView.setAdapter(orderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderListActivity.this,LinearLayoutManager.VERTICAL,false);
        orderRecycleView.setLayoutManager(layoutManager);

    }
    /***
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}