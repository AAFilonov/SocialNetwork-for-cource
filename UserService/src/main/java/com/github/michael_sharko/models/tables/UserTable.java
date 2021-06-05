package com.github.michael_sharko.models.tables;

public class UserTable
{
    public Integer id;

    public String username;
    public String password;
    public java.sql.Date birthday;

    public String fullname;
    public Integer sex = 0;

    public String country ;
    public String city = null;
    public String school = null;
    public String university = null;
}