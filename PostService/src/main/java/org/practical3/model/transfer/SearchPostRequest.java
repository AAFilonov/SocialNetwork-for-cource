package org.practical3.model.transfer;

public class SearchPostRequest {
    public String Content;
    public String Username;
    public Integer UserId;
    public Integer Count =10;
    public Integer Offset =0;

    public SearchPostRequest(String content, Integer userId){
        Content =content;
        UserId =userId;
    }

}
