package org.practical3.handlers;

import org.practical3.model.postService.AnswerPostService;
import org.practical3.model.postService.RequestPosts;
import org.practical3.utils.Commons;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class PostsServlet extends HttpServlet {

    Commons Common;

    public PostsServlet(Commons commons) {
        Common = commons;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            getPosts(req,resp);
        }
        catch (ClassNotFoundException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: no posts found for provided ids",HttpServletResponse.SC_NOT_FOUND)));
        }
        catch (IllegalArgumentException e){

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: wrong arguments",HttpServletResponse.SC_BAD_REQUEST)));
        }
        catch (Exception e){
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Common.gson.toJson(new AnswerPostService(null,"Error: internal server error:"+e.getMessage(),HttpServletResponse.SC_INTERNAL_SERVER_ERROR)));

        }



    }

    private void getPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String,String[]> args =  req.getParameterMap();
        RequestPosts request= new RequestPosts(args);
        AnswerPostService postServiceAnswer =  Common.httpGetPosts(request);
        doReply(postServiceAnswer, resp);
    }

    private void doReply(AnswerPostService postServiceAnswer, HttpServletResponse resp) throws Exception {
        resp.setContentType("application/json");
        resp.setStatus(postServiceAnswer.Code);
        resp.getWriter().println(Common.gson.toJson(postServiceAnswer));
    }




}