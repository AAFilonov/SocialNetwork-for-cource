package com.github.michael_sharko.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidParameterException;

public class StaticGson {
    private static final GsonBuilder builder = new GsonBuilder();
    private static Gson gson = builder.create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T readObjectFrom(HttpServletRequest request, Class<T> classOfObject) throws IOException {
        String requestString = IOUtils.toString(request.getInputStream());
        if (StringUtils.isBlank(requestString)) {
            throw new InvalidParameterException("UserService: The request cannot be empty!");
        }
        return StaticGson.fromJson(requestString, classOfObject);
    }
}
