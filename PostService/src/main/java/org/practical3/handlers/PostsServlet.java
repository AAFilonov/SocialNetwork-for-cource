package org.practical3.handlers;

import com.google.gson.Gson;
import org.practical3.common.PostsDataBaseManager;
import org.practical3.model.Field;
import org.practical3.model.Post;
import org.practical3.model.PostsAnswer;
import org.practical3.model.PostsRequest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class PostsServlet extends HttpServlet {

    //  Database credentials
    Gson gson = new Gson();
    private PostsDataBaseManager dataBaseManager;

    public PostsServlet(PostsDataBaseManager db){
        dataBaseManager =db;
    }



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Method service enter\n");
        super.service(req, resp);
        resp.getWriter().write("Method service exit\n");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String,String[]> args =  req.getParameterMap();

        try {

            PostsRequest request= new PostsRequest(args);
            PostsAnswer  answer = new PostsAnswer(
                    dataBaseManager.getPosts(request.ids, request.fields, request.count, request.offset),
            "OK");

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(gson.toJson(answer));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(gson.toJson(new PostsAnswer(null,"Error: wrong arguments")));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(gson.toJson(new PostsAnswer(null,"Error: internal server error\n"+e.getMessage())));

        }
    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }



}