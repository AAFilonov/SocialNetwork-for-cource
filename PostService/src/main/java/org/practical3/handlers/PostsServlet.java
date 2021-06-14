package org.practical3.handlers;

import org.practical3.logic.Actions;

import org.practical3.logic.ExceptionHandler;
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
        ExceptionHandler.execute(req, resp, Actions::insertPosts);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, Actions::getPosts);

    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, Actions::updatePosts);

    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, Actions::deletePosts);

    }






}