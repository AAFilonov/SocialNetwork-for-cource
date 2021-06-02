package com.github.michael_sharko.utils;

import org.postgresql.jdbc.PgConnection;

import java.sql.*;

public class DatabaseManager
{
    private static Connection connection;

    public void connect(String url, String user, String password)
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

    public final Connection getConnection()
    {
        return connection;
    }

    public void disconnect()
    {
        try
        {
            if (connection != null)
                connection.close();
        }
        catch (SQLException troubles)
        {
            System.out.println("[PSQL:Error]: " + troubles.getMessage());
            troubles.printStackTrace();
        }
    }
}
