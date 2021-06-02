package com.github.michael_sharko.handlers;

import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.PropertyManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/html");

        String loginFromRequest = req.getParameter("login");
        String passwordFromRequest = req.getParameter("password");

        try
        {
            String InsertIntoUsersSql = PropertyManager.getPropertyAsString("database.table.users.insert", "");
            if ("".equals(InsertIntoUsersSql))
                throw new PropertyManager.PropertyNotFindException("database.table.users.insert not found");

            PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement(InsertIntoUsersSql);

            prepareStatement.setString(1, loginFromRequest);
            prepareStatement.setString(2, passwordFromRequest);

            prepareStatement.executeUpdate();
            prepareStatement.close();

            resp.getWriter().write("You are welcome, " + loginFromRequest + "!");
        }
        catch (SQLException throwables)
        {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
