package org.practical3.handlers;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.practical3.logic.Actions;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.Commons;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.PostsRequest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;



public class PostsServlet extends HttpServlet {




    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[INFO]: Get request "+request.getMethod() +request.getRequestURI());
        super.service(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String action = req.getParameter("action");
            doAction(action, req, resp);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Commons.toJson(new Answer("Wrong arguments",e)));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Commons.toJson(new Answer("SQL error ",e)));
        } catch (Exception e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(Commons.toJson(new Answer( "Internal server error\n" + e.getMessage(),e)));

        }

    }

    private void doAction(String action, HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, SQLException {
        switch (action) {
            case "getPosts":
                Actions.getPosts(req, resp);
                break;
            case "getWall":
                Actions.getWall(req, resp);
                break;
            case "insertPosts":
                Actions.insertPosts(req, resp);
                break;
            case "updatePosts":
                Actions.updatePosts(req, resp);
                break;
            case "removePosts":
                Actions.removePosts(req, resp);
                break;
            case "deletePosts":
                Actions.deletePosts(req, resp);
                break;
            case "searchPosts":
               Actions.searchPosts(req, resp);
                break;

            default:

                resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                resp.getWriter().println(Commons.gson.toJson(new Answer("Error: wrong action or no action "+ action, null)));

        }
    }






}