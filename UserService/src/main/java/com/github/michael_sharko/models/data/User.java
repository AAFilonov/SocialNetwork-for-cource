package com.github.michael_sharko.models.data;

public class User {
    public Integer userid;

    public String username;
    public String password;

    public java.sql.Date birthday;

    public String fullname;
    public Integer sex = 0;

    public String country;
    public String city = null;
    public String school = null;
    public String university = null;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasUserId() {
        return userid != null;
    }

    public boolean hasUsername() {
        return username != null;
    }

    public boolean hasPassword() {
        return password != null;
    }
}