package org.practical3.utils;

import org.apache.commons.io.IOUtils;
import org.practical3.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class RequestReader {


    public  static <T> Collection<T> getRequestBodyAsCollection(HttpServletRequest req, Type userListType) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return StaticGson.fromJson(requestString,userListType);
    }
    public  static <T> T getRequestBodyAsObject (HttpServletRequest req, Class<T> type) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return StaticGson.fromJson(requestString, type );
    }
}
