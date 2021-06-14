package org.practical3.handlers;

import org.practical3.logic.Actions;

import org.practical3.model.transfer.Answer;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.practical3.utils.StaticGson.toJson;


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
            resp.getWriter().println(toJson(new Answer("Data already exists",null)));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(toJson(new Answer("Failed to connect to DataBase",null)));
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(toJson(new Answer("No data found ",null)));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(toJson(new Answer("Internal server error - " + e.getMessage(),null)));

        }

    }

    private void doAction(String action, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ClassNotFoundException, SQLException, NumberFormatException   {
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
            case "deletePosts":
                Actions.deletePosts(req, resp);
                break;
            case "doLike":
                Actions.doLike(req, resp);
                break;
            case "doRepost":
                Actions.doRepost(req, resp);
                break;
            case "searchPosts":
               Actions.searchPosts(req, resp);
                break;

            default:

                resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                resp.getWriter().println(toJson(new Answer("Error: wrong action or no action "+ action, null)));

        }
    }






}