package com.example.smartvillage;

import com.example.smartvillage.entity.User;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserHelper {
    private static Retrofit retrofit;
    private static HttpService httpService;
    static {
        retrofit = HttpUtils.getRetrofit();
        httpService = retrofit.create(HttpService.class);
    }
    public static BaseResponse<String> checkUserIn(String username,String password){
        Call<ResponseBody> call = httpService.CheckUserIn(username, password);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse<String> baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BaseResponse<String> userJwtLogin(String token){
        Call<ResponseBody> call = httpService.userJwtLogin(token);
        try {
            Response<ResponseBody> execute = call.execute();
            BaseResponse<String> baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }


    public static BaseResponse<User> getUserFromDataBase(String name, String password){
        Call<ResponseBody> userInfo = httpService.getUserInfo(name, password);
        try {
            Response<ResponseBody> execute = userInfo.execute();

            Type userType = new TypeToken<BaseResponse<User>>(){}.getType();
            BaseResponse<User> baseResponse = new Gson().fromJson(execute.body().string(), userType);
            return baseResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CheckNameLegal(String name) throws Exception{
        if(name.length() <=1 ||name.length() >10) throw new Exception("用户名小于1或大于10");
    }
    public static boolean CheckNameRepeat(String name){
        Call<ResponseBody> responseBodyCall = httpService.checkUser(name);
        try {
        Response<ResponseBody> execute = responseBodyCall.execute();
            BaseResponse baseResponse = new Gson().fromJson(execute.body().string(), BaseResponse.class);
            if (baseResponse.getData() != null){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static BaseResponse SignUp(String name,String password){
        Call<ResponseBody> call = httpService.userSignUp(name, password);
        try {
            Response<ResponseBody> execute = call.execute();
            return new Gson().fromJson(execute.body().string(),BaseResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void CheckPasswordlegal(String password)throws Exception{
        if(password.length()<1 ||password.length()>10){
            throw new Exception("密码需要小于10大于1");
        }
    }
    public static BaseResponse updatePassword(String username,String newPassword){
        Call<ResponseBody> call = httpService.updatePassword(username, newPassword);
        try {
            Response<ResponseBody> execute = call.execute();
            return new Gson().fromJson(execute.body().string(),BaseResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
