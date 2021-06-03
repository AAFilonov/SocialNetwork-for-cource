package org.practical3.utils;


import java.sql.*;

public class DataBaseManager {

    Connection Connection;
    public java.sql.Connection getConnection(){
        return Connection;
    }

    public DataBaseManager(  String DB_URL,  String DB_Name,  String User,  String Password) throws SQLException {
                    this.Connection = DriverManager.getConnection(DB_URL+DB_Name
                    ,User,Password);
            System.out.println("DB connection successfully opened");

    }
    public void close(){
        try {
            if (Connection != null) {
                Connection.close();
                System.out.println("DB connection successfully closed");
            }
        }
        catch (Exception e){
            System.out.println("Error while close connection: \n"+e.getMessage());
        }
    }

    ResultSet execute(String querry) throws SQLException {
        Statement statement = Connection.createStatement();
        return  statement.executeQuery(querry);
    }

    int executeUpdate(String querry) throws SQLException {
        Statement statement = Connection.createStatement();
        return  statement.executeUpdate(querry);
    }

}