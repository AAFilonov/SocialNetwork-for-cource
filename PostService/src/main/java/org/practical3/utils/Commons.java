package org.practical3.utils;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.practical3.logic.PostsDataBaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class Commons {
    public static PostsDataBaseManager dataBaseManager;
    public static Gson gson= new Gson();


    public static String toJson(Object o){
        return  gson.toJson(o);
    }
    public static <T> Collection<T>  fromJson(String s, Type dataType){

        return gson.fromJson(s,dataType);
    }
    public static <T> T fromJson(String s,Class<T> dataType){
        return gson.fromJson(s,dataType);
    }

    public  static <T> Collection<T> getRequestBodyAsCollection(HttpServletRequest req, Type userListType) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return Commons.fromJson(requestString,userListType);
    }




    public  static <T> T getRequestBodyAsObject (HttpServletRequest req, Class<T> type) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return Commons.fromJson(requestString, type );
    }

}
