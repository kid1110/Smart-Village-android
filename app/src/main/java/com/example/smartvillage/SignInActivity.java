package com.example.smartvillage;

import android.os.Bundle;

import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicInteger;

public class SignInActivity extends AppCompatActivity {
    Button sign;
    TextInputEditText userNameInput,passWordInput,passWordInputAgain;
    TextInputLayout passWordInputLayOut,passWordAgainLayOut;
    MaterialToolbar signBar;
    boolean isQuery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userNameInput = findViewById(R.id.UserInput);
        passWordInput = findViewById(R.id.PswInput);
        passWordInputAgain = findViewById(R.id.PswInputAgain);

        sign = findViewById(R.id.Sign);
        passWordInputLayOut = findViewById(R.id.PswInputLayout);
        passWordAgainLayOut = findViewById(R.id.PswInputAgainLayout);
        signBar = findViewById(R.id.SignAppBar);
        signBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQuery) return;
                String username,password,passWordAgain;
                username = userNameInput.getText().toString();
                password = passWordInput.getText().toString();
                passWordAgain = passWordInputAgain.getText().toString();
                System.out.println(username);
                AtomicInteger isNameRepeat= new AtomicInteger(0);

                try {
                    UserHelper.CheckNameLegal(username);
                } catch (Exception e) {
                    userNameInput.setError(e.getMessage());
                    isQuery = false;
                    return;
                }
                new Thread(()->{
                    if(UserHelper.CheckNameRepeat(username)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userNameInput.setError("用户名已存在");
                            }
                        });
                        isNameRepeat.set(1);
                    }else{
                        isNameRepeat.set(-1);
                    }

                }).start();
                try {
                    UserHelper.CheckPasswordlegal(password);
                } catch (Exception e) {
                    passWordInputLayOut.setEndIconVisible(false);
                    passWordInput.setError(e.getMessage());
                    e.printStackTrace();
                    return;
                }
                if(!password.equals(passWordAgain)){
                    passWordAgainLayOut.setEndIconVisible(false);
                    passWordInputAgain.setError("密码不一致");
                    isQuery = false;
                    return;
                }

                new Thread(()->{
                    while(isNameRepeat.get() == 0);
                    if(isNameRepeat.get() ==1){
                        return;
                    }
                    BaseResponse baseResponse = UserHelper.SignUp(username, password);
                    isQuery = false;
                    if(baseResponse.getCode() == Status.Success.getCode()){
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),"Sign OK!  "+username,Toast.LENGTH_SHORT).show();

                        finish();

                    }
                    else{
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),"错误用户: "+username,Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });
        passWordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passWordInputLayOut.setError(null);
                passWordInputLayOut.setEndIconVisible(true);
            }
        });
        passWordInputAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passWordAgainLayOut.setError(null);
                passWordAgainLayOut.setEndIconVisible(true);
            }
        });





    }


}