package com.example.smartvillage.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    public static Retrofit getRetrofit(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                                                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                                                .readTimeout(15000, TimeUnit.MILLISECONDS)
                                                .build();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
        return new Retrofit.Builder().baseUrl("http://112.74.93.38:8899/").client(client).addConverterFactory(gsonConverterFactory).build();
    }



}
