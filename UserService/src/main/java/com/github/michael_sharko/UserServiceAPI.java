package com.github.michael_sharko;

import com.github.michael_sharko.models.data.SubscriptionRequest;
import com.github.michael_sharko.models.data.User;
import com.github.michael_sharko.utils.HttpManager;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Collection;

public class UserServiceAPI {
    public static void register(User user) throws IOException {
        HttpManager.sendPost("/users", user);
    }

    public static void update(User user) throws IOException {
        HttpManager.sendPut("/users", user);
    }

    public static Collection<User> getUsers(String users) throws IOException {
        HttpResponse response = HttpManager.sendGet("/users?user_ids=", users);
        return (Collection<User>) HttpManager.readResponse(response).getData();
    }

    public static void delete(User user) throws IOException {
        HttpManager.sendDelete("/users", user);
    }
    public static void delete(Integer userid) throws IOException {
        HttpManager.sendDelete("/users", new User(userid));
    }

    public static Collection<SubscriptionRequest> getSubscriptions(User user) throws IOException {
        HttpResponse response = HttpManager.sendPost("/getsubscriptions", user);
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }
    public static Collection<SubscriptionRequest> getSubscriptions(Integer userid) throws IOException {
        HttpResponse response = HttpManager.sendPost("/getsubscriptions", new User(userid));
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }

    public static Collection<SubscriptionRequest> getFollowers(User user) throws IOException {
        HttpResponse response = HttpManager.sendPost("/getfollowers", user);
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }
    public static Collection<SubscriptionRequest> getFollowers(Integer userid) throws IOException {
        HttpResponse response = HttpManager.sendPost("/getfollowers", new User(userid));
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }

    public static Collection<SubscriptionRequest> subscribe(User user) throws IOException {
        HttpResponse response = HttpManager.sendPost("/subscriptions", user);
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }
    public static Collection<SubscriptionRequest> subscribe(Integer userid) throws IOException {
        HttpResponse response = HttpManager.sendPost("/subscriptions", new User(userid));
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }

    public static Collection<SubscriptionRequest> unsubscribe(User user) throws IOException {
        HttpResponse response = HttpManager.sendDelete("/subscriptions", user);
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }
    public static Collection<SubscriptionRequest> unsubscribe(Integer userid) throws IOException {
        HttpResponse response = HttpManager.sendDelete("/subscriptions", new User(userid));
        return (Collection<SubscriptionRequest>) HttpManager.readResponse(response).getData();
    }
}
