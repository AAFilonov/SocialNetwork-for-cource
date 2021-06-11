package com.github.michael_sharko.handlers;

import com.github.michael_sharko.handlers.UserService.SubscriptionsUserService.RegistrationInSubscriptionsUserService;
import com.github.michael_sharko.handlers.UserService.SubscriptionsUserService.RemoveInSubscriptionsUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/subscriptions")
public class SubscriptionsUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new RegistrationInSubscriptionsUserService(req, resp).execute();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new RemoveInSubscriptionsUserService(req, resp).execute();
    }
}