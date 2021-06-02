package com.github.michael_sharko.handlers;

import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.models.table.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/get")
public class GetServlet extends HttpServlet
{
    private void addResultsToListFromTableBySearch(List<UserTable> result, String[] params, String whatToSearch, String equalTo) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT " + ((params == null) ? ('*') : (String.join(",", params))) + " FROM users WHERE " + whatToSearch + " = '" + equalTo + '\'';
        ResultSet resultSet = statement.executeQuery(sql);

        boolean showId = false;
        boolean showLogin = false;
        boolean showBirthday = false;

        boolean showFullname = false;
        boolean showSex = false;

        boolean showCountry = false;
        boolean showCity = false;
        boolean showSchool = false;
        boolean showUniversity = false;

        boolean showAll = true;

        if (params != null)
        {
            Arrays.sort(params);

            showId = Arrays.binarySearch(params, "id") >= 0;
            showLogin = Arrays.binarySearch(params, "login") >= 0;
            showBirthday = Arrays.binarySearch(params, "birthday") >= 0;
            showFullname = Arrays.binarySearch(params, "fullname") >= 0;
            showSex = Arrays.binarySearch(params, "sex") >= 0;
            showCountry = Arrays.binarySearch(params, "country") >= 0;
            showCity = Arrays.binarySearch(params, "city") >= 0;
            showSchool = Arrays.binarySearch(params, "school") >= 0;
            showUniversity = Arrays.binarySearch(params, "university") >= 0;

            showAll = false;
        }

        while (resultSet.next())
        {
            UserTable user = new UserTable();

            if (showAll || showId)
                user.id = resultSet.getInt("id");

            if (showAll || showLogin)
                user.login = resultSet.getString("login");

            if (showAll || showBirthday)
                user.birthday = resultSet.getDate("birthday");

            if (showAll || showFullname)
                user.fullname = resultSet.getString("fullname");

            if (showAll || showSex)
                user.sex = resultSet.getInt("sex");

            if (showAll || showCountry)
                user.country = resultSet.getString("country");

            if (showAll || showCity)
                user.city = resultSet.getString("city");

            if (showAll || showSchool)
                user.school = resultSet.getString("school");

            if (showAll || showUniversity)
                user.university = resultSet.getString("university");

            result.add(user);
        }
        statement.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String[] ids = req.getParameterValues("id");
        String[] logins = req.getParameterValues("login");

        if (ids == null && logins == null)
            return;

        String[] params = req.getParameterValues("params");

        try
        {
            List<UserTable> result = new ArrayList<>();

            if (ids != null)
                for (String id : ids)
                    addResultsToListFromTableBySearch(result, params, "id", id);

            if (logins != null)
                for (String login : logins)
                    addResultsToListFromTableBySearch(result, params, "login", login);

            GsonBuilder gsonBuilder = new GsonBuilder();
            // gsonBuilder.serializeNulls(); // to show null-elements

            Gson gson = gsonBuilder.create();
            resp.getWriter().write(gson.toJson(result));
        }
        catch (SQLException throwables) {
            System.out.println("[PSQL:Error]: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
