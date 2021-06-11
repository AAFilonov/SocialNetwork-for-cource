package org.practical3.utils;


import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import org.practical3.model.transfer.Answer;

;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public  class Commons {

    static public HttpClientManager HttpClientManager = new HttpClientManager();





    public  static <T> Collection<T> getRequestBodyAsCollection(HttpServletRequest req, Type userListType) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return StaticGson.fromJson(requestString,userListType);
    }
    public  static <T> T getRequestBodyAsObject (HttpServletRequest req, Class<T> type) throws IOException {
        String requestString = IOUtils.toString(req.getInputStream());
        return StaticGson.fromJson(requestString, type );
    }

    public static void sendOk(Object data, HttpServletResponse resp) throws Exception {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(StaticGson.toJson( new Answer("OK", data)));
    }
    public static void sendOk(Answer a, HttpServletResponse resp) throws Exception {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(StaticGson.toJson( a));
    }

    public static Collection<Integer> parseIds(String IdsString){
        String[] post_ids_s = IdsString.split(",");
        ArrayList<Integer> ids= new ArrayList<>();
        for (String id:post_ids_s) {
            ids.add(new Integer(id));
        }
        return ids;
    }

    public static void executeAndCatchExceptions(HttpServletRequest req, HttpServletResponse resp, RequestProcessor processor) throws IOException {
        resp.setContentType("application/json");
        try {

            processor.process(req,resp);

        }
        catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(StaticGson.toJson(new Answer("Error: wrong arguments",null)));
        }
        catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(StaticGson.toJson(new Answer("Error: internal server error:"+e.getMessage(),null)));

        }
    }



}
