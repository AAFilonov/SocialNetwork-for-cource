package com.github.michael_sharko.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

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
