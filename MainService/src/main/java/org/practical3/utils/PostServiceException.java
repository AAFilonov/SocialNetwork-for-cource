package org.practical3.utils;

import org.practical3.model.transfer.Answer;

public class PostServiceException extends  Exception{
    Answer PostServiceAnswer;
    Integer FailureStatusCode;
    public PostServiceException(Answer answer, Integer stausCode){
        super();
        PostServiceAnswer = answer;
        FailureStatusCode = stausCode;
    }
}
