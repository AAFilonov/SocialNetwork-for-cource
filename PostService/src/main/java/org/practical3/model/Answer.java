package org.practical3.model;

import java.util.Collection;

public class Answer {
    public Object Data;
    public String Status;
    public Integer AffectedRows;
    public Answer( String status, Object data) {
        Data = data;
        Status = status;

    }
    public Answer( String status, Object data,Integer affectedRows) {
        Data = data;
        Status = status;
        AffectedRows = affectedRows;
    }


}
