package org.practical3.model.userService.tables;

public class UserTable
{
    public Integer id;

    public String login;
    public String password;
    public java.sql.Date birthday;

    public String fullname;
    public Integer sex = 0;

    public String country ;
    public String city = null;
    public String school = null;
    public String university = null;
}