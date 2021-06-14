package org.practical3.utils;

import org.practical3.model.transfer.Answer;

public class ServiceException extends  Exception{
    Answer ServiceAnswer;
    Integer FailureStatusCode;
    public ServiceException(Answer answer, Integer stausCode){
        super();
        ServiceAnswer = answer;
        FailureStatusCode = stausCode;
    }
}
