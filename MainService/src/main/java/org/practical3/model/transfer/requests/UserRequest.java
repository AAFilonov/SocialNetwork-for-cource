package org.practical3.model.transfer.requests;

public class UserRequest {
    public Integer userid;
    public String username;

    public UserRequest(Integer userid) {
        this.userid = userid;
    }

    public UserRequest(String username) {
        this.username = username;
    }
}
