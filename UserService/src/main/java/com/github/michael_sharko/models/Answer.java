package com.github.michael_sharko.models;

public class Answer {
    public String Status;
    public Object Data;
    public Integer AffectedRows;

    public Answer(String status, Object data) {
        this.Status = status;
        this.Data = data;
    }

    public String getStatus() {
        return this.Status;
    }

    public Object getData() {
        return this.Data;
    }
}

/*
public class Answer {
    private final String status;
    private final Object data;

    public Answer(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public Object getData() {
        return this.data;
    }
}
 */