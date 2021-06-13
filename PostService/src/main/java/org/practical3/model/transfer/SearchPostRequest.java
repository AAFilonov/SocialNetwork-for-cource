package org.practical3.model.transfer;

public class SearchPostRequest {
    public String Content;
    public Integer OwnerId;
    public Integer Count =10;
    public Integer Offset =0;

    public SearchPostRequest(String content, Integer ownerId){
        Content =content;
        OwnerId =ownerId;
    }

}
