package org.practical3.handlers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.practical3.model.Post;
import org.practical3.model.RequestPosts;
import org.practical3.model.RequestWall;
import org.practical3.utils.Commons;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.model.AnswerPosts;

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


public class WallServlet extends HttpServlet {


    Commons Common ;


    public WallServlet(Commons commons){
        Common = commons;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("get ok");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {


       String action = req.getParameter("action");
       if ("getWall".equals(action)) {
           getWall(req, resp);
       } else {
           resp.setContentType("application/json");
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           resp.getWriter().println(Common.gson.toJson(new AnswerPosts(null, "Error: wrong action or no action provided")));
       }

    }

    protected void getWall(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            String reqStr = IOUtils.toString(req.getInputStream());
            RequestWall requestWall = Common.gson.fromJson(reqStr, RequestWall.class);
            AnswerPosts answer = new AnswerPosts(Common.dataBaseManager.getWall(requestWall), "OK");


            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(Common.gson.toJson(answer));
        } catch (IllegalArgumentException e) {

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.gson.toJson(new AnswerPosts(null, "Error: wrong arguments")));
        } catch (Exception e) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Common.gson.toJson(new AnswerPosts(null, "Error: internal server error\n" + e.getMessage())));

        }


    }


}