package org.practical3.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;

;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public  class Commons<T> {

    static public HttpClientManager HttpClientManager = new HttpClientManager();
    static public Gson gson = new Gson();


    public static void sendOk(Object data, HttpServletResponse resp) throws Exception {

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(Commons.gson.toJson( new Answer("OK", data)));
    }

    public static Collection<Integer> parseIds(String IdsString){
        String[] post_ids_s = IdsString.split(",");
        ArrayList<Integer> ids= new ArrayList<>();
        for (String id:post_ids_s) {
            ids.add(new Integer(id));
        }
        return ids;
    }

    public static void processAndReply(HttpServletRequest req, HttpServletResponse resp, RequestProcessor processor) throws IOException {
        resp.setContentType("application/json");
        try {

            processor.process(req,resp);

        }
        catch (ClassNotFoundException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: no posts found for provided ids")));
        }
        catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: wrong arguments")));
        }
        catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: internal server error:"+e.getMessage())));

        }



    }


    public  static <T>  Collection<T>  getCollectionFromRequest(HttpServletRequest req,Class<T> type) throws IOException {
        String reqStr = IOUtils.toString(req.getInputStream());
        Type userListType = new TypeToken<Collection<T>>() {}.getType();
        return Commons.gson.fromJson(reqStr,userListType);
    }

    public  static <T> T getObjectFromRequest (HttpServletRequest req, Class<T> type) throws IOException {
        String reqStr = IOUtils.toString(req.getInputStream());
        return Commons.gson.fromJson(reqStr, type);
    }
}
