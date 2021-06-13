package org.practical3.model.transfer.requests;

public class SearchPostRequest {
    public String Content;
    public String Username;
    public Integer UserId;
    public Integer Count =10;
    public Integer Offset =0;

    public SearchPostRequest(String content ){
        Content =content;

    }
    public SearchPostRequest(String content, Integer userId, String username) {
        Content =content;
        UserId = userId;
        Username = username;
    }

}
