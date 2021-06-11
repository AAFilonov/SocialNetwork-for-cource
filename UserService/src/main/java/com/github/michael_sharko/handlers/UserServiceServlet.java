package com.github.michael_sharko.handlers;

import com.github.michael_sharko.handlers.UserService.FindUserByIdAndNameInUserService;
import com.github.michael_sharko.handlers.UserService.RegistrationInUserService;
import com.github.michael_sharko.handlers.UserService.RemoveInUserService;
import com.github.michael_sharko.handlers.UserService.UpdateInUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new RegistrationInUserService(req, resp).execute();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new FindUserByIdAndNameInUserService(req, resp).execute();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new UpdateInUserService(req, resp).execute();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new RemoveInUserService(req, resp).execute();
    }
}