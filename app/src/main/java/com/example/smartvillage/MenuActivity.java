package com.example.smartvillage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.smartvillage.databinding.ActivityMenuBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private SharedPreferences sharedPreferences;
    private String token;
    private  TimerTask Jwttask;
    private SharedPreferences.Editor edit;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        edit = sharedPreferences.edit();
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    @Override
    public void onBackPressed() {
        if(token == null){
            super.onBackPressed();
        }else{
            Toast.makeText(MenuActivity.this,"已经在主页面了",Toast.LENGTH_LONG).show();
            return;
        }

    }
    public void init(){
        Jwttask = new TimerTask() {
            @Override
            public void run() {
                token = sharedPreferences.getString("user",null);
                System.out.println("menuInit: "+token);
                BaseResponse<String> stringBaseResponse = UserHelper.userJwtLogin(token);
                System.out.println("menu: "+stringBaseResponse);
               if(stringBaseResponse.getCode() == Status.AnoterDeviceHasLogin.getCode()){
                    Looper.prepare();
                    anotherDeviceLogin();
                    Looper.loop();
                }else if(stringBaseResponse.getCode() ==Status.JwtError.getCode()){
                    Looper.prepare();
                    tokenError();
                    Looper.loop();
                }
            }
        };
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(Jwttask,0,2000, TimeUnit.MILLISECONDS);
    }
    public void anotherDeviceLogin(){
        materialAlertDialogBuilder.setTitle("该账户已在另一台设备登录").setMessage("警告！！该账号已在另一台设备登录，请重新登录!").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit.clear();
                edit.apply();
                finish();
            }
        }).setCancelable(false).create();
        materialAlertDialogBuilder.show();
    }
    public void tokenError(){
        materialAlertDialogBuilder.setTitle("cookie过期警告").setMessage("警告！！cookie过期警告，请重新登录!").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit.clear();
                edit.apply();
                finish();
            }
        }).setCancelable(false).create();
        materialAlertDialogBuilder.show();

    }




}