package com.github.michael_sharko.models;

import com.github.michael_sharko.models.tables.UserTable;

import java.util.ArrayList;
import java.util.List;

public class Answer
{
    private String status;
    private String message;
    private List<UserTable> users;

    public Answer(String status, List<UserTable> users)
    {
        this.status = status;
        this.users = users;
    }

    public Answer(String status, UserTable user)
    {
        this.status = status;
        this.users = new ArrayList<>();

        users.add(user);
    }

    public Answer(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public List<UserTable> getUsers()
    {
        return users;
    }

    public String getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }
}
