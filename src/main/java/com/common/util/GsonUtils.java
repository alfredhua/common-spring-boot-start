package com.common.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class GsonUtils {

    static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static String toJSONString(Map<String,Object> map){
        return gson.toJson(map);
    }

    public static String toJSON(Object m){
        return gson.toJson(m);
    }

    public static Map<String,Object> jsonStringToMap(String str){
        return gson.fromJson(str,new TypeToken<Map<String, Object>>() {}.getType());
    }

    public static Object toObject(String json){
        return gson.fromJson(json, new TypeToken<Object>(){}.getType());
    }

}
