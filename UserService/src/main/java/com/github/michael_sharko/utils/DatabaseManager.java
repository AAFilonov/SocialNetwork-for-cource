package com.github.michael_sharko.utils;

import com.github.michael_sharko.models.tables.SubscribesTable;
import com.github.michael_sharko.models.tables.UserTable;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager
{
    private static Connection connection;

    public static void connectTo(String url, String user, String password)
    {
        try
        {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null)
                System.out.println("[PSQL:Info]: Connected to PostgreSQL!");
        }
        catch (SQLException troubles)
        {
            System.out.println("[PSQL:Error]: " + troubles.getMessage());
            troubles.printStackTrace();
        }
    }

    public static boolean isConnected()
    {
        return connection != null;
    }

    public static Connection getConnection() throws SQLNonTransientConnectionException {
        if (connection == null)
            throw new SQLNonTransientConnectionException("[PSQL:Error]: Database not connected!");

        return connection;
    }

    public static void registerUser(UserTable newUser) throws SQLException
    {
        String insertIntoUserTableSql = "INSERT INTO users" +
                " (login, password, birthday, fullname, sex, country, city, school, university)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";

        PreparedStatement prepareStatement = connection.prepareStatement(insertIntoUserTableSql);

        prepareStatement.setString(1, newUser.login);
        prepareStatement.setString(2, newUser.password);
        prepareStatement.setDate(3, newUser.birthday);
        prepareStatement.setString(4, newUser.fullname);
        prepareStatement.setInt(5, newUser.sex);
        prepareStatement.setString(6, newUser.country);
        prepareStatement.setString(7, newUser.city);
        prepareStatement.setString(8, newUser.school);
        prepareStatement.setString(9, newUser.university);

        prepareStatement.executeUpdate();
        prepareStatement.close();
    }

    public static void registerSubscribe(SubscribesTable subscribe) throws SQLException
    {
        String insertIntoSubscribesTableSql = "INSERT INTO subscribes" +
                " (ownerid, subscriberid)" +
                " VALUES (?, ?) ON CONFLICT DO NOTHING";

        PreparedStatement prepareStatement = connection.prepareStatement(insertIntoSubscribesTableSql);

        prepareStatement.setInt(1, subscribe.ownerid);
        prepareStatement.setInt(2, subscribe.subscriberid);

        prepareStatement.executeUpdate();
        prepareStatement.close();
    }

    public static void selectUsers(List<UserTable> result, String[] params, String condition) throws SQLException
    {
        Statement statement = connection.createStatement();

        String sql = "SELECT " + ((params == null) ? ('*') : (String.join(",", params))) + " FROM users " + condition;
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

    public static void selectSubscribes(List<SubscribesTable> result, String condition) throws SQLException
    {
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM subscribes " + condition;
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next())
        {
            SubscribesTable subscribe = new SubscribesTable();

            subscribe.ownerid = resultSet.getInt("ownerid");
            subscribe.subscriberid = resultSet.getInt("subscriberid");

            result.add(subscribe);
        }

        statement.close();
    }

    public static void deleteSubscribe(SubscribesTable subscribe) throws SQLException
    {
        if (subscribe.ownerid == null || subscribe.subscriberid == null)
            return;

        Statement statement = connection.createStatement();

        String sql = "DELETE FROM subscribes WHERE ownerid = " + subscribe.ownerid + " AND subscriberid = " + subscribe.subscriberid;
        statement.executeQuery(sql);

        statement.close();
    }

    public static void disconnect()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
                System.out.println("[PSQL:Info]: Connected to PostgreSQL!");
            }
        }
        catch (SQLException troubles)
        {
            System.out.println("[PSQL:Error]: " + troubles.getMessage());
            troubles.printStackTrace();
        }
    }
}
