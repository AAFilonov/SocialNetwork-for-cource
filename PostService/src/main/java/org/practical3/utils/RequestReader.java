package org.practical3.utils;

import org.apache.commons.io.IOUtils;
import org.practical3.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public class RequestReader {


    public  static <T> Collection<T> getRequestBodyAsCollection(HttpServletRequest req, Type userListType) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return StaticGson.fromJson(requestString,userListType);
    }
    public  static <T> T getRequestBodyAsObject (HttpServletRequest req, Class<T> type) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        T data = StaticGson.fromJson(requestString, type );
        if (data== null) throw  new IllegalArgumentException();
        return data;
    }


    public static Integer getArgAsInt(Map<String, String[]> args, String argname) {
        try {
            return new Integer(args.get(argname)[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static String getArgAsString(Map<String, String[]> args, String argname) {
        try {
            return args.get(argname)[0];
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    public static Instant getArgAsInstant(Map<String, String[]> args, String argname) {
        try {
            return   Instant.parse(args.get(argname)[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }


}
