package com.example.smartvillage;

import com.example.smartvillage.dto.DCard;
import com.example.smartvillage.entity.Card;
import com.example.smartvillage.entity.Order;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpService {
    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> CheckUserIn(@Field("username") String username, @Field("password") String password);

    @POST("user/jwtLogin")
    @FormUrlEncoded
    Call<ResponseBody> userJwtLogin(@Field("token") String token);
    @GET("user/getUser")
    Call<ResponseBody> getUserInfo(@Query("username") String username,@Query("password") String password);

    @PUT("user/SignUp")
    @FormUrlEncoded
    Call<ResponseBody> userSignUp(@Field("username") String username, @Field("password") String password);


    @GET("user/checkUser")
    Call<ResponseBody> checkUser(@Query("username") String username);

    @PUT("user/updatePassword")
    @FormUrlEncoded
    Call<ResponseBody> updatePassword(@Header("Authorization") String token,@Field("username") String username,@Field("newPassword") String password);


    @GET("card/getAllCard")
    Call<ResponseBody> getAllCards(@Header("Authorization") String token);


    @PUT("card/insertCard")
    @FormUrlEncoded
    Call<ResponseBody> insertCard(@Header("Authorization") String token,@Field("image") String image, @Field("title") String title, @Field("author")String author, @Field("content")String content, @Field("timestamp")String timeStamp);

    @PUT("card/deleteCard")
    @FormUrlEncoded
    Call<ResponseBody> deleteCard(@Header("Authorization") String token,@Field("did") Integer did);


    @POST("card/updateCard")
    Call<ResponseBody> updateCard(@Header("Authorization") String token,@Body DCard card);

    @POST("order/insertOrder")
    Call<ResponseBody> insertOrder(@Header("Authorization") String token,@Body Order order);

    @GET("order/getUserOrders")
    Call<ResponseBody> getUserOrders(@Header("Authorization") String token,@Query("uid") Integer uid);

    @PUT("order/deleteOrder")
    @FormUrlEncoded
    Call<ResponseBody> deleteOrders(@Header("Authorization") String token,@Field("oid") Integer oid);

    @PUT("order/updateOrder")
    Call<ResponseBody> updateOrder(@Header("Authorization") String token,@Body Order order);

    @GET("order/getAllOrder")
    Call<ResponseBody> getAllOrder(@Header("Authorization") String token);






}
