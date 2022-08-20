package com.example.smartvillage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartvillage.entity.Jwt;
import com.example.smartvillage.entity.User;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.JwtUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdatePasswordActivity extends AppCompatActivity {

    private TextInputEditText oldPassword,newPassword,newPasswordAgain;
    private Button finishConfirm;
    private Boolean isUpdate;
    private TextInputLayout oldPasswordLayOut,newPasswordLayout,newPasswordAgainLayout;
    private String token;
    private SharedPreferences sharedPreferences;
    private Jwt jwtInfo;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        init();
        finishConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUpdate) return;
                String oldPasswordText,newPasswordText,newPasswordAgainText;
                oldPasswordText = oldPassword.getText().toString();
                newPasswordText = newPassword.getText().toString();
                newPasswordAgainText = newPasswordAgain.getText().toString();


                if (oldPasswordText.length() == 0 || newPasswordText.length() == 0){
                    if(oldPasswordText.length() == 0){
                        oldPasswordLayOut.setError("旧密码必填");
                    }else if(newPasswordText.length() == 0){
                        newPasswordLayout.setError("新密码不能为空");
                    }
                    return;
                }else if(oldPasswordText.equals(newPasswordText)){
                    newPasswordLayout.setError("不可以与旧密码一样");
                }else if(oldPasswordText.length() >50 ||newPasswordText.length() >50 ||newPasswordAgainText.length() >50){
                   //太长就返回
                    return;
                } else if(!newPasswordText.equals(newPasswordAgainText)){
                    //密码不一致就报错
                    newPasswordLayout.setError("新密码确认不一");
                    newPasswordAgainLayout.setError("新密码确认不一样");
                    return;
                }else{
                    //进行网络请求
                    new Thread(()->{
                        BaseResponse<User> userFromDataBase = UserHelper.getUserFromDataBase(jwtInfo.getUsername(), oldPasswordText);
                        System.out.println(userFromDataBase);
                        if (userFromDataBase.getCode() == Status.Success.getCode()){
                            //验证本人成功则尝试修改密码
                            BaseResponse baseResponse = UserHelper.updatePassword(token,jwtInfo.getUsername(), newPasswordAgainText);
                            System.out.println(baseResponse);
                            if(baseResponse.getCode() == Status.Success.getCode()){
                                Looper.prepare();
                                isUpdate = false;
                                Toast.makeText(UpdatePasswordActivity.this,"更新密码成功",Toast.LENGTH_LONG).show();;
                                //修改密码成功，清空数据
                                edit.clear();
                                edit.apply();
                                //返回登录页面
                                Intent intent = new Intent(UpdatePasswordActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                //失败的话
                                Looper.prepare();
                                isUpdate = false;
                                Toast.makeText(UpdatePasswordActivity.this,"修改密码失败",Toast.LENGTH_LONG).show();;
                                Looper.loop();
                            }
                        }else{
                            Looper.prepare();
                            isUpdate = false;
                            Toast.makeText(UpdatePasswordActivity.this,"旧密码输入错误",Toast.LENGTH_LONG).show();;
                            Looper.loop();
                        }
                    }).start();
                }


            }
        });
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldPasswordLayOut.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPasswordAgainLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void init(){
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user",null);
        edit = sharedPreferences.edit();
        jwtInfo = JwtUtils.getJwtInfo(token);
        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        newPasswordAgain = findViewById(R.id.new_password_again);
        finishConfirm = findViewById(R.id.update_comfirm);
        isUpdate = false;
        oldPasswordLayOut = findViewById(R.id.update_old_password);
        newPasswordLayout = findViewById(R.id.update_new_password);
        newPasswordAgainLayout = findViewById(R.id.update_new_passwordAgain);
    }



}