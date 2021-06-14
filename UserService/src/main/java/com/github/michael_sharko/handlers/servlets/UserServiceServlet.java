package com.github.michael_sharko.handlers.servlets;

import com.github.michael_sharko.handlers.ExceptionHandler;
import com.github.michael_sharko.handlers.servlets.UserService.UserService;

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
        ExceptionHandler.execute(req, resp, UserService::register);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, UserService::getUsers);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, UserService::update);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, UserService::remove);
    }
}