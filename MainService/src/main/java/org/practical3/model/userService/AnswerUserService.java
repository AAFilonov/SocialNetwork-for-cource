package org.practical3.model.userService;

import java.util.ArrayList;
import java.util.List;

public class AnswerUserService<Table>
{
    private final String status;
    private String message;
    private List<Table> list;

    public AnswerUserService(String status, List<Table> list)
    {
        this.status = status;
        this.list = list;
    }

    public AnswerUserService(String status, Table user)
    {
        this.status = status;
        this.list = new ArrayList<>();

        list.add(user);
    }

    public AnswerUserService(String status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public List<Table> getList()
    {
        return list;
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
