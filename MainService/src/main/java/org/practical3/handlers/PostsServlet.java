package org.practical3.handlers;


import javafx.geometry.Pos;
import org.practical3.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.Commons;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class PostsServlet extends HttpServlet {
    public PostsServlet() {

    }

    //TODO реализовать свои классы исключений
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            getPosts(req,resp);
        }
        catch (ClassNotFoundException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: no posts found for provided ids")));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: wrong arguments")));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Commons.gson.toJson(new Answer(null,"Error: internal server error:"+e.getMessage())));

        }



    }

    private void getPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String,String[]> args =  req.getParameterMap();
        PostsRequest request= new PostsRequest(
                args.get("post_ids")[0],
                new Integer(args.get("count")[0]),
                new Integer(args.get("offset")[0])
        );
        Collection<Post> posts  = PostServiceAPI.getPosts(request);

        Commons.sendOk(posts, resp);
    }






}