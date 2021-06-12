package org.practical3.api;

import org.apache.http.HttpResponse;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.http.HttpManagerForUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UserServiceAPI {
    public static void register(User user) throws IOException {
        HttpManagerForUserService.sendPost("/users", user);
    }

    public static void update(User user) throws IOException {
        HttpManagerForUserService.sendPut("/users", user);
    }

    public static Collection<User> getUsers(String users) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendGet("/users?user_ids=", users);
        return (ArrayList<User>) (HttpManagerForUserService.readResponse(response).Data);
    }

    public static void delete(Integer userid) throws IOException {
        HttpManagerForUserService.sendDelete("/users", new UserRequest(userid));
    }

    public static void delete(String username) throws IOException {
        HttpManagerForUserService.sendDelete("/users", new UserRequest(username));
    }

    public static Collection<Integer> getSubscriptions(Integer userid) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/getsubscriptions", new UserRequest(userid));
        Collection<Double> doubleCollection = (ArrayList<Double>) HttpManagerForUserService.readResponse(response).Data;
        return Arrays.asList(doubleCollection.stream().map(Double::intValue).toArray(Integer[]::new));
        // return Arrays.asList(Arrays.stream(values.stream().mapToInt(ValuesFromUnnest::getUserId).toArray()).boxed().toArray(Integer[]::new));
    }

    public static Collection<Integer> getSubscriptions(String username) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/getsubscriptions", new UserRequest(username));
        Collection<Double> doubleCollection = (ArrayList<Double>) HttpManagerForUserService.readResponse(response).Data;
        return Arrays.asList(doubleCollection.stream().map(Double::intValue).toArray(Integer[]::new));
        // return Arrays.asList(Arrays.stream(values.stream().mapToInt(ValuesFromUnnest::getUserId).toArray()).boxed().toArray(Integer[]::new));
    }

    public static Collection<Integer> getFollowers(Integer userid) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/getfollowers", new UserRequest(userid));
        Collection<Double> doubleCollection = (ArrayList<Double>) HttpManagerForUserService.readResponse(response).Data;
        return Arrays.asList(doubleCollection.stream().map(Double::intValue).toArray(Integer[]::new));
    }

    public static Collection<Integer> getFollowers(String username) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/getfollowers", new UserRequest(username));
        Collection<Double> doubleCollection = (ArrayList<Double>) HttpManagerForUserService.readResponse(response).Data;
        return Arrays.asList(doubleCollection.stream().map(Double::intValue).toArray(Integer[]::new));
    }

    public static void subscribe(SubscriptionRequest subscription) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/subscriptions", subscription);
    }

    public static void unsubscribe(SubscriptionRequest subscription) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendDelete("/subscriptions", subscription);
    }
}
