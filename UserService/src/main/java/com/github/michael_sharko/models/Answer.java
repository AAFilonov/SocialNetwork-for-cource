package com.github.michael_sharko.models;

public class Answer {
    public String Status;
    public Object Data;
    public Integer AffectedRows;

    public Answer(String status, Object data) {
        this.Status = status;
        this.Data = data;
    }

    public Answer(String status, Object data, Integer affectedRows) {
        this.Status = status;
        this.Data = data;
        this.AffectedRows = affectedRows;
    }
}

/*
// todo: согласовать с Александром и Андреем класс Answer.
public class Answer {
    private final String status;
    private final Object data;
    private final Integer AffectedRows;

    public Answer(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Answer(String status, Object data, Integer affectedRows) {
        this.status = status;
        this.data = data;
        this.affectedRows = affectedRows;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public Integer getAffectedRows()
    {
        return affectedRows;
    }
}
*/