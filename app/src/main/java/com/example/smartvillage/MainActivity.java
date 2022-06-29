package com.example.smartvillage;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.smartvillage.entity.User;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.JsonUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.tencent.bugly.Bugly;

public class MainActivity extends AppCompatActivity {
    MaterialButton login,sign;
    private TextInputEditText username,password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private String token;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bugly.init(getApplicationContext(),"224f67b092",false);
        setContentView(R.layout.activity_main);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        //代码初始化
        init();
        jwtLogin();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","init");
                Log.i("username",username.getText().toString());
                Log.i("password",password.getText().toString());

                System.out.println("main: "+token);
                    //第一次登录请求token
                    new Thread(()->{

                        //跟后台进行网络请求
                        BaseResponse<String> baseResponse = UserHelper.checkUserIn(username.getText().toString(), password.getText().toString());
                        if(baseResponse!= null &&baseResponse.getCode()==Status.Success.getCode()){
                            Log.i(Integer.toString(Status.Success.getCode()),Status.Success.getMsg());

                            token = baseResponse.getData();
                            System.out.println(token);
                            //存入用户信息
                            edit.clear();
                            edit.putString("user",token).apply();

                            //跳转到菜单页面
                            Intent intent = new Intent(MainActivity.this,MenuActivity.class);

                            startActivity(intent);
                        }else if (baseResponse!= null &&baseResponse.getCode() == Status.PassWordError.getCode()){
                            //记录报错
                            Log.i(Integer.toString(Status.PassWordError.getCode()),Status.PassWordError.getMsg());
                        }
                    }).start();



            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            }
        });

    }
    public void init(){
        login = findViewById(R.id.Login);
        username = findViewById(R.id.UserInput);
        password  = findViewById(R.id.PswInput);
        sign = findViewById(R.id.Sign);
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        token = sharedPreferences.getString("user",null);

    }

    public void anotherDeviceLogin(){
        materialAlertDialogBuilder.setTitle("该账户已在另一台设备登录").setMessage("警告！！该账号已在另一台设备登录，请重新登录!").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit.clear();
                edit.apply();
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
            }
        }).setCancelable(false).create();
        materialAlertDialogBuilder.show();

    }

    @Override
    protected void onResume() {
        password.setText("");
        username.setText("");
        super.onResume();
    }

    public void jwtLogin(){
        if(token != null && !token.isEmpty()){
            //跳转到菜单页面
            new Thread(()->{
                BaseResponse<String> stringBaseResponse = UserHelper.userJwtLogin(token);
                //如果返回新的token
                if(stringBaseResponse.getCode() == Status.Success.getCode()){
                    //跳转新的内容
                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);
                }else if(stringBaseResponse.getCode() == Status.AnoterDeviceHasLogin.getCode()){
                    Looper.prepare();
                    anotherDeviceLogin();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    tokenError();
                    Looper.loop();
                }
            }).start();
        }
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this,"在登录页面了",Toast.LENGTH_LONG).show();
        return;
    }

}