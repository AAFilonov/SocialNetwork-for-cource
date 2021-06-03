package org.practical3.model.postService;

import java.util.Collection;

public class AnswerPostService {
    public Object Data;
    public String Status;
    public int Code;
    public AnswerPostService(Object data, String status, int code) {
        Data = data;
        Status = status;
        Code = code;
    }
}

