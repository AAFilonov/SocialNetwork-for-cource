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

import static org.practical3.utils.StaticGson.fromJson;

public class Commons {
    public static PostsDataBaseManager dataBaseManager;




    public  static <T> T getRequestBodyAsObject (HttpServletRequest req, Class<T> type) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return  fromJson(requestString, type );
    }

}
