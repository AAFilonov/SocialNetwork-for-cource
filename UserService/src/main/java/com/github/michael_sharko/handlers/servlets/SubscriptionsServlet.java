package com.github.michael_sharko.handlers.servlets;

import com.github.michael_sharko.handlers.ExceptionHandler;
import com.github.michael_sharko.handlers.servlets.UserService.SubscriptionService;
import com.github.michael_sharko.utils.StaticGson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/subscriptions")
public class SubscriptionsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::subscribe);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::getSubscriptions);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, SubscriptionService::unsubscribe);
    }
}