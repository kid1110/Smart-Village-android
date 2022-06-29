package com.example.smartvillage.utils;

import com.example.smartvillage.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

    public static String objectToJson(Object obj){
        return new Gson().toJson(obj);
    }
    public static User jsonToObject(String json){
        return new Gson().fromJson(json,new TypeToken<User>(){}.getType());
    }
}
