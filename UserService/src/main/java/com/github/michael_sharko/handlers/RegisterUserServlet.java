package com.github.michael_sharko.handlers;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.tables.UserTable;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet
{
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        if(StringUtils.isBlank(reqStr))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(
                    new Answer<UserTable>("Error", "to register, you must specify at least a username and password")
            ));
            return;
        }

        try
        {
            UserTable newUser = gson.fromJson(reqStr, UserTable.class);
            DatabaseManager.registerUser(newUser);
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
