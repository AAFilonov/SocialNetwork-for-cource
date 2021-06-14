package org.practical3.handlers;


import org.practical3.logic.Actions;
import org.practical3.logic.ExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetPostsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, Actions::getPosts);
    }


}