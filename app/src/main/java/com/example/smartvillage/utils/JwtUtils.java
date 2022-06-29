package com.example.smartvillage.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.example.smartvillage.entity.Jwt;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Map<String,Object> getMap(String token){
        Map<String, Claim> decode = JWT.decode(token).getClaims();
        HashMap<String, Object> map = new HashMap<>(4);
        decode.forEach((k,v)->{
            if (k.equals("iat") ||k.equals("exp")){
                map.put(k,v.asInt());
            }else{
                map.put(k,v.asString());
            }
        });
        return map;
    }

    public static Jwt getJwtInfo(String token){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Map<String, Object> map = getMap(token);
            Integer exp = (Integer) map.get("exp");
            Integer iat = (Integer) map.get("iat");
            String username = (String) map.get("username");
            String uuid = (String) map.get("uuid");
            Integer admin = Integer.decode((String) map.get("admin"));
            return  new Jwt(exp,iat,uuid,username,admin);
        }else{
            return null;
        }

    }
}
