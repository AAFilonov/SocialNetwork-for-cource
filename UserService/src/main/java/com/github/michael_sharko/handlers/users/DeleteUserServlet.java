package com.github.michael_sharko.handlers.users;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.tables.SubscribesTable;
import com.github.michael_sharko.models.tables.UserTable;
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

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet
{
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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
            UserTable user = gson.fromJson(reqStr, UserTable.class);
            DatabaseManager.deleteUser(user.id);
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
