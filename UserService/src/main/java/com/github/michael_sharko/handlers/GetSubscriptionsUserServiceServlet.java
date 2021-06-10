package com.github.michael_sharko.handlers;

import com.github.michael_sharko.handlers.UserService.GetSubscriptionsInUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getsubscriptions")
public class GetSubscriptionsUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new GetSubscriptionsInUserService(req, resp).execute();
    }
}