package org.practical3.common;

import org.practical3.model.ConfigData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    Connection Connection;
    public java.sql.Connection getConnection(){
        return Connection;
    }

    public DataBaseManager(ConfigData configData ) throws SQLException {
            this.Connection = DriverManager.getConnection(configData.DB_URL+configData.DB_Name
                    ,configData.User,configData.Password);
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
