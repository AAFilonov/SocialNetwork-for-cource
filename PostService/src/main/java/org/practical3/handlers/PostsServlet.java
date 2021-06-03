package org.practical3.handlers;

import org.apache.commons.io.IOUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.model.Post;
import org.practical3.model.AnswerPosts;
import org.practical3.model.RequestPosts;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String,String[]> args =  req.getParameterMap();

        try {

            RequestPosts request= new RequestPosts(args);
            Collection<Post> posts=  dataBaseManager.getPosts(request.ids, request.postFields, request.count, request.offset);
            if(posts==null) throw new ClassNotFoundException();
            AnswerPosts answer = new AnswerPosts(posts,"OK");

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(gson.toJson(answer));
        }
        catch (ClassNotFoundException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(gson.toJson(new AnswerPosts(null,"Error: no posts found for provided ids")));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(gson.toJson(new AnswerPosts(null,"Error: wrong arguments")));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(gson.toJson(new AnswerPosts(null,"Error: internal server error\n"+e.getMessage())));

        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {

            String reqStr = IOUtils.toString(req.getInputStream());
            Type userListType = new TypeToken<ArrayList<Post>>(){}.getType();
            Collection<Post> posts = gson.fromJson (reqStr, userListType);

            dataBaseManager.insertPosts(posts);
            AnswerPosts answer = new AnswerPosts(null,"OK");

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(gson.toJson(answer));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(gson.toJson(new AnswerPosts(null,"Error: wrong arguments")));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(gson.toJson(new AnswerPosts(null,"Error: internal server error\n"+e.getMessage())));

        }
    }



    @Override
    public void destroy() {
        log("Method destroy =)");
    }



}