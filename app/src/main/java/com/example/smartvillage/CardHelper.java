package com.example.smartvillage;

import android.graphics.Bitmap;

import com.example.smartvillage.dto.DCard;
import com.example.smartvillage.entity.Card;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.BitmapUtils;
import com.example.smartvillage.utils.HttpUtils;
import com.example.smartvillage.utils.TimeStampUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CardHelper {
    private static Retrofit retrofit;
    private static HttpService httpService;
    static {
        retrofit = HttpUtils.getRetrofit();
        httpService = retrofit.create(HttpService.class);
    }

    public static List<Card> GetCards(String token){
        Call<ResponseBody> allCards = httpService.getAllCards(token);
        try {
            Response<ResponseBody> execute = allCards.execute();
            Type cardListType = new TypeToken<BaseResponse<List<Card>>>(){}.getType();

            System.out.println(execute.body());
            BaseResponse<List<Card>> baseResponse = new Gson().fromJson(execute.body().string(), cardListType);
            //card 类型转换

            System.out.println(baseResponse);
            List<Card> data = baseResponse.getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Boolean InsertCard(String token,String title, String content, String author, String image){
        long l = System.currentTimeMillis();
        String timeStamp = TimeStampUtils.stampToDate(Long.toString(l));
        Call<ResponseBody> call = httpService.insertCard(token,image,title,author,content,timeStamp);
        try {
            Response<ResponseBody> execute = call.execute();
            System.out.println("insert"+execute);
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            if(baseResponse.getCode() == Status.Success.getCode()){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deleteCard(String token,Integer did){
        Call<ResponseBody> responseBodyCall = httpService.deleteCard(token,did);
        try {
            Response<ResponseBody> execute = responseBodyCall.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            if(baseResponse.getCode() == Status.Success.getCode()){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateCard(String token,DCard dCard){
        Call<ResponseBody> call = httpService.updateCard(token,dCard);
        long l = System.currentTimeMillis();
        String timeStamp = TimeStampUtils.stampToDate(Long.toString(l));
        dCard.setTimeStamp(timeStamp);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            if(baseResponse.getCode() == Status.Success.getCode()){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
