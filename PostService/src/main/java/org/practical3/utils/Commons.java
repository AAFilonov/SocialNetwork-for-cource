package org.practical3.utils;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class Commons {
    public static PostsDataBaseManager dataBaseManager;
    public static Gson gson;


    public static String toJson(Object o){
        return  gson.toJson(o);
    }
    public static <T> T fromJson(String s, Type dataType){
        return gson.fromJson(s,dataType);
    }

    public  static <T> Collection<T> getRequestBodyAsCollection(HttpServletRequest req) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        Type userListType = new TypeToken<Collection<T>>() {}.getType();
        return Commons.fromJson(requestString,userListType);
    }
    public  static <T> T getRequestBodyAsObject (HttpServletRequest req) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        Type type = new TypeToken<Class<T>>() {}.getType();
        return Commons.fromJson(requestString, type );
    }

}
