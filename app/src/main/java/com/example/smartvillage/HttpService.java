package com.example.smartvillage;

import com.example.smartvillage.dto.DCard;
import com.example.smartvillage.entity.Card;

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
    Call<ResponseBody> updatePassword(@Field("username") String username,@Field("newPassword") String password);


    @GET("card/getAllCard")
    Call<ResponseBody> getAllCards();


    @PUT("card/insertCard")
    @FormUrlEncoded
    Call<ResponseBody> insertCard(@Field("image") String image, @Field("title") String title, @Field("author")String author, @Field("content")String content, @Field("timestamp")String timeStamp);

    @PUT("card/deleteCard")
    @FormUrlEncoded
    Call<ResponseBody> deleteCard(@Field("did") Integer did);


    @POST("card/updateCard")
    Call<ResponseBody> updateCard(@Body DCard card);

}
