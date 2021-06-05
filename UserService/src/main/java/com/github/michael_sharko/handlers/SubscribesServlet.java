package com.github.michael_sharko.handlers;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.tables.SubscribesTable;
import com.github.michael_sharko.utils.DatabaseManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/subscribes")
public class SubscribesServlet extends HttpServlet
{
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        if(StringUtils.isBlank(reqStr))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(
                    new Answer<>("Error", "to subscribes set ownerid and subscriberid")
            ));
            return;
        }

        try
        {
            SubscribesTable subscribe = gson.fromJson(reqStr, SubscribesTable.class);
            DatabaseManager.registerSubscribe(subscribe);
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");
        String ownerid = req.getParameter("id");

        if (ownerid == null)
            return;

        try
        {
            List<SubscribesTable> result = new ArrayList<>();
            DatabaseManager.selectSubscribes(result, "WHERE ownerid = " + ownerid);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(new Answer<SubscribesTable>("OK", result)));
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String reqStr = IOUtils.toString(req.getInputStream());
        if(StringUtils.isBlank(reqStr))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(
                    new Answer<>("Error", "to delete subscribe set ownerid and subscriberid")
            ));
            return;
        }

        try
        {
            SubscribesTable subscribe = gson.fromJson(reqStr, SubscribesTable.class);
            DatabaseManager.deleteSubscribe(subscribe);
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}