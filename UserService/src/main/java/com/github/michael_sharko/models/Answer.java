package com.github.michael_sharko.models;

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
