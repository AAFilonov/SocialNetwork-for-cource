package org.practical3.common;


import org.practical3.utils.PropertyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    Connection Connection;
    public java.sql.Connection getConnection(){
        return Connection;
    }

    public DataBaseManager() throws SQLException {

            String DB_URL =   PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
            String DB_Name =   PropertyManager.getPropertyAsString("database.database", "JavaPractice");
            String User =   PropertyManager.getPropertyAsString("database.user", "postgres");
            String Password =   PropertyManager.getPropertyAsString("database.password", "1");


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

}
