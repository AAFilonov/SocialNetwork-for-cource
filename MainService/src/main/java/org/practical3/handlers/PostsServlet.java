package org.practical3.handlers;

import org.practical3.model.AnswerPosts;
import org.practical3.utils.Commons;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PostsServlet extends HttpServlet {

    Commons Common;

    public PostsServlet(Commons commons) {
        Common = commons;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "getWall":
                getWall(req, resp);
                break;
            case "getPosts":
                getPosts(req, resp);
                break;
            case "insertPosts":
                insertPosts(req, resp);
                break;
            case "updatePosts":
                updatePosts(req, resp);
                break;
            case "removePosts":
                reomovePosts(req, resp);
                break;

            default:
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(Common.gson.toJson(new AnswerPosts(null, "Error: wrong action or no action provided")));
        }
    }

    private void updatePosts(HttpServletRequest req, HttpServletResponse resp) {

    }
    private void reomovePosts(HttpServletRequest req, HttpServletResponse resp) {

    }
    private void insertPosts(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void getPosts(HttpServletRequest req, HttpServletResponse resp) {
    }




    private void getWall(HttpServletRequest req, HttpServletResponse resp) {


    }


}