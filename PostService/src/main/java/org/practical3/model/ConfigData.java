package org.practical3.model;

public class ConfigData {
    public int ServerPort;
    public String ServerPath ;
    public String DB_URL;
    public String DB_Name ;
    public String User;
    public String Password ;

    public ConfigData(String serverPort,String serverPath,String db_url, String db_name, String user, String password) {
        ServerPort = new Integer( serverPort);
        ServerPath = serverPath;
        DB_URL = db_url;
        DB_Name = db_name;
        User = user;
        Password = password;
    }
}
