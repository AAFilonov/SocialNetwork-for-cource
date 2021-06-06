package org.practical3.model.transfer;

import java.util.ArrayList;
import java.util.Collection;

public class Answer {
    public Object Data;
    public String Status;
    public Answer( String status, Object data) {
        Data = data;
        Status = status;

    }
    public Integer getDataAsInteger(){
        return ( (ArrayList<Integer>)Data).get(0);
    }


}
