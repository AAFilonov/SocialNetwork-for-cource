package com.github.michael_sharko.handlers;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.tables.UserTable;
import com.github.michael_sharko.utils.DatabaseManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/get")
public class GetUserServlet extends HttpServlet
{
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");

        String[] ids = null;
        String[] logins = null;
        String[] params = null;

        if (req.getParameter("id") != null)
            ids = req.getParameter("id").split(",");

        if (req.getParameter("login") != null)
            logins = req.getParameter("login").split(",");

        if (ids == null && logins == null)
            return;

        if (req.getParameter("params") != null)
            params = req.getParameter("params").split(",");

        try
        {
            List<UserTable> result = new ArrayList<>();

            if (ids != null)
                for (String id : ids)
                    DatabaseManager.selectUsers(result, params, "WHERE id = '" + id + '\'');

            if (logins != null)
                for (String login : logins)
                    DatabaseManager.selectUsers(result, params, "WHERE login = '" + login + '\'');

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(new Answer("OK", result)));
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
