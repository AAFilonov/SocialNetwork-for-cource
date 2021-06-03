package com.github.michael_sharko.utils;

import com.github.michael_sharko.handlers.SubscribesServlet;
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
                " (username, password, birthday, fullname, sex, country, city, school, university)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";

        PreparedStatement prepareStatement = connection.prepareStatement(insertIntoUserTableSql);

        prepareStatement.setString(1, newUser.username);
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

    public static void selectUsers(List<UserTable> result, String[] params, String condition) throws SQLException
    {
        Statement statement = connection.createStatement();

        String sql = "SELECT " + ((params == null) ? ('*') : (String.join(",", params))) + " FROM users " + condition;
        ResultSet resultSet = statement.executeQuery(sql);

        boolean showId = false;
        boolean showUsername = false;
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
            showUsername = Arrays.binarySearch(params, "username") >= 0;
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

            if (showAll || showUsername)
                user.username = resultSet.getString("username");

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

    public static void updateUser(UserTable updatedUser) throws SQLException
    {
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM users WHERE id = " + updatedUser.id;
        ResultSet resultSet = statement.executeQuery(sql);

        UserTable user = new UserTable();
        resultSet.next();

        user.username = resultSet.getString("username");
        user.password = resultSet.getString("password");
        user.birthday = resultSet.getDate("birthday");

        user.fullname = resultSet.getString("fullname");
        user.sex = resultSet.getInt("sex");

        user.country = resultSet.getString("country");
        user.city = resultSet.getString("city");
        user.school = resultSet.getString("school");
        user.university = resultSet.getString("university");

        String updateUserSql = "UPDATE users SET username = ?, password = ?, birthday = ?, fullname = ?, sex = ?, country = ?, city = ?, school = ?, university = ? WHERE id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(updateUserSql);

        prepareStatement.setString(1, updatedUser.username == null ? user.username : updatedUser.username);
        prepareStatement.setString(2, updatedUser.password == null ? user.password : updatedUser.password);
        prepareStatement.setDate(3, updatedUser.birthday == null ? user.birthday : updatedUser.birthday);

        prepareStatement.setString(4, updatedUser.fullname == null ? user.fullname : updatedUser.fullname);
        prepareStatement.setInt(5, updatedUser.sex == null ? user.sex : updatedUser.sex);

        prepareStatement.setString(6, updatedUser.country == null ? user.country : updatedUser.country);
        prepareStatement.setString(7, updatedUser.city == null ? user.city : updatedUser.city);
        prepareStatement.setString(8, updatedUser.school == null ? user.school : updatedUser.school);
        prepareStatement.setString(9, updatedUser.university == null ? user.university : updatedUser.university);
        prepareStatement.setInt(10, updatedUser.id);

        prepareStatement.executeUpdate();
        prepareStatement.close();
    }

    public static void deleteUser(Integer id) throws SQLException
    {
        if (id == null)
            return;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT ownerid FROM subscribes WHERE ownerid = " + id);
        if (resultSet.next())
            statement.executeQuery("DELETE FROM subscribes WHERE ownerid = " + id);

        statement.executeQuery("DELETE FROM users WHERE id = " + id);
        statement.close();
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
