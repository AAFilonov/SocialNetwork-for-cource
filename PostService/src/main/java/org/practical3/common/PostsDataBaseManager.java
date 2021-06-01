package org.practical3.common;

import org.practical3.model.ConfigData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostsDataBase extends DataBase {
    public PostsDataBase(ConfigData configData) throws SQLException {
        super(configData);
    }

    public String  getData() throws SQLException {

        String str = super.Connection.nativeSQL("select * from db.posts");

        Statement statement = super.Connection.createStatement();
        ResultSet result = statement.executeQuery(str);
        String output = "";
        while (result.next()) {
            output += result.getString("content") + "\n";
        }
        return  output;
    }

    

}
