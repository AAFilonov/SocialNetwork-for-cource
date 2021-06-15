package com.github.michael_sharko.handlers.servlets;

import com.github.michael_sharko.handlers.ExceptionHandler;
import com.github.michael_sharko.handlers.servlets.UserService.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FollowersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::becomeFollower);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::getFollowers);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::stopBeingFollower);
    }
}
