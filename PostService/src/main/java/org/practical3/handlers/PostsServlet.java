package org.practical3.handlers;

import org.apache.commons.io.IOUtils;
import com.google.common.reflect.TypeToken;
import org.practical3.model.RequestWall;
import org.practical3.utils.Commons;
import org.practical3.model.Post;
import org.practical3.model.Answer;
import org.practical3.model.RequestPosts;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class PostsServlet extends HttpServlet {

    //  Database credentials
    Commons Common;


    public PostsServlet(Commons commons) {
        Common = commons;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            String action = req.getParameter("action");
            doAction(action, req, resp);

        } catch (ClassNotFoundException e) {

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(Common.gson.toJson(new Answer("Error: no posts found for provided ids" ,null )));
        } catch (IllegalArgumentException e) {

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.gson.toJson(new Answer("Error: wrong arguments",null)));
        } catch (Exception e) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Common.gson.toJson(new Answer( "Error: internal server error\n" + e.getMessage(),null)));

        }
    }

    private void doAction(String action, HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, SQLException {
        switch (action) {
            case "getPosts":
                getPosts(req, resp);
                break;
            case "getWall":
                getWall(req, resp);
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
            case "deletePosts":
                deletePosts(req, resp);
                break;

            default:
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println(Common.gson.toJson(new Answer("Error: wrong action or no action provided", null)));

        }
    }

    private void deletePosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String reqStr = IOUtils.toString(req.getInputStream());
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        Collection<Integer> ids = Common.gson.fromJson(reqStr, userListType);
        Common.dataBaseManager.deletePosts(ids);
    }

    private void updatePosts(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void getWall(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        String reqStr = IOUtils.toString(req.getInputStream());
        RequestWall requestWall = Common.gson.fromJson(reqStr, RequestWall.class);
        Collection<Post> posts =  Common.dataBaseManager.getWall(requestWall);

        sendOk(resp,posts);

    }

    private void getPosts(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {


        String reqStr = IOUtils.toString(req.getInputStream());
        RequestPosts request = Common.gson.fromJson(reqStr, RequestPosts.class);
        Collection<Post> posts = Common.dataBaseManager.getPosts(request.ids, request.count, request.offset);

        sendOk(resp,posts);
    }


    private void insertPosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {


        String reqStr = IOUtils.toString(req.getInputStream());
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = Common.gson.fromJson(reqStr, userListType);

        Common.dataBaseManager.insertPosts(posts);


        sendOk(resp,null);

    }


    private void reomovePosts(HttpServletRequest req, HttpServletResponse resp) {
    }

    void sendOk(HttpServletResponse resp, Object data) throws IOException {
        Answer a = new Answer(data,"OK");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(Common.gson.toJson(a));
    }
}