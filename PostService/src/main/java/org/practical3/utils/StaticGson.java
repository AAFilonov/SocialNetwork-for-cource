package org.practical3.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Collection;

public class StaticGson {
    private static final GsonBuilder builder = new GsonBuilder();
    public static Gson gson = builder.create();


    public static String toJson(Object o){
        return StaticGson.gson.toJson(o);
    }
    public static <T> Collection<T> fromJson(String s, Type dataType){

        return StaticGson.gson.fromJson(s,dataType);
    }
    public static <T> T fromJson(String s,Class<T> dataType){
        return StaticGson.gson.fromJson(s,dataType);
    }



}
