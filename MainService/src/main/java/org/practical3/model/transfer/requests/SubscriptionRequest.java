package org.practical3.model.transfer.requests;

public class SubscriptionRequest {
    public Integer UserId;
    public Integer FollowerId;

    //саня воткнул конструктор для тестов
    public SubscriptionRequest(Integer userid, Integer followerId){
        UserId =userid;
        FollowerId = followerId;
    }
}
