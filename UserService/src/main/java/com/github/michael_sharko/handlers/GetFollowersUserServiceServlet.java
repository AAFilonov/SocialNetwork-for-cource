package com.github.michael_sharko.handlers;

import com.github.michael_sharko.handlers.UserService.GetFollowersInUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getfollowers")
public class GetFollowersUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new GetFollowersInUserService(req, resp).execute();
    }
}