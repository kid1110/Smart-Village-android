package com.example.smartvillage;

import com.example.smartvillage.entity.Card;
import com.example.smartvillage.entity.Order;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.example.smartvillage.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderHelper {
    private static Retrofit retrofit;
    private static HttpService httpService;

    static {
        retrofit = HttpUtils.getRetrofit();
        httpService = retrofit.create(HttpService.class);
    }

    public static BaseResponse insertOrder(String token,Order order){
        Call<ResponseBody> call = httpService.insertOrder(token,order);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Order> getUserOrders(String token,Integer uid){
        Call<ResponseBody> userOrders = httpService.getUserOrders(token,uid);
        try {
            Response<ResponseBody> execute = userOrders.execute();
            Type orderListType = new TypeToken<BaseResponse<List<Order>>>(){}.getType();
            BaseResponse<List<Order>> baseResponse = new Gson().fromJson(execute.body().string(),orderListType);
            List<Order> data = baseResponse.getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BaseResponse deleteOrder(String token,Integer oid){
        Call<ResponseBody> call = httpService.deleteOrders(token,oid);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static BaseResponse updateOrder(String token,Order order){
        Call<ResponseBody> call = httpService.updateOrder(token,order);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Order> getAllOrders(String token){
        Call<ResponseBody> userOrders = httpService.getAllOrder(token);
        try {
            Response<ResponseBody> execute = userOrders.execute();
            Type orderListType = new TypeToken<BaseResponse<List<Order>>>(){}.getType();
            BaseResponse<List<Order>> baseResponse = new Gson().fromJson(execute.body().string(),orderListType);
            List<Order> data = baseResponse.getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
