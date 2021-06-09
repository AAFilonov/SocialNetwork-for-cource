package com.github.michael_sharko.models;

import java.io.Serializable;

public class User implements Serializable {
    public Integer userid;

    public String username;
    public String password;

    public Integer[] subscriptions;
    public Integer[] followers;

    public User() {}

    public User(Integer userid)
    {
        this.userid = userid;
    }
}