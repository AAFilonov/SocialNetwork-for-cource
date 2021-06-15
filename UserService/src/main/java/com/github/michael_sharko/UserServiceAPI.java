package com.github.michael_sharko;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.data.SubscriptionRequest;
import com.github.michael_sharko.models.data.User;
import com.github.michael_sharko.utils.HttpManagerForUserService;
import com.github.michael_sharko.utils.PropertyManager;
import com.github.michael_sharko.utils.StaticGson;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class UserServiceAPI {
    private static final String url = PropertyManager.getPropertyAsString("service.users.addr", "http://localhost:8080");
    private static Answer answer = new Answer();

    public static String getStatus() {
        return answer.Status;
    }

    /* UserService */
    public static int register(User[] users) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost(url + "/users", users);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static int update(User[] users) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPut(url + "/users", users);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static Collection<User> getUsers(String user_ids) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendGet(url + "/users", "user_ids=" + user_ids);
        answer = HttpManagerForUserService.readResponse(response);
        return Arrays.asList(StaticGson.fromJson((String) answer.Data, User[].class));
    }

    public static int delete(String user_ids) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendDelete(url + "/users", user_ids);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    /* SubscriptionService */
    public static int subscribe(SubscriptionRequest[] subscriptions) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost(url + "/subscriptions", subscriptions);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static int unsubscribe(SubscriptionRequest[] subscriptions) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendDelete(url + "/subscriptions", subscriptions);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static int becomeFollower(SubscriptionRequest[] subscriptions) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendPost("/followers", subscriptions);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static int stopBeingFollower(SubscriptionRequest[] subscriptions) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendDelete(url + "/followers", subscriptions);
        answer = HttpManagerForUserService.readResponse(response);
        return answer.AffectedRows;
    }

    public static Collection<Integer> getSubscriptions(String user_ids) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendGet(url + "/subscriptions", "user_ids=" + user_ids);
        answer = HttpManagerForUserService.readResponse(response);
        return Arrays.asList(StaticGson.fromJson((String) answer.Data, Integer[].class));
    }

    public static Collection<Integer> getFollowers(String user_ids) throws IOException {
        HttpResponse response = HttpManagerForUserService.sendGet(url + "/followers", "user_ids=" + user_ids);
        answer = HttpManagerForUserService.readResponse(response);
        return Arrays.asList(StaticGson.fromJson((String) answer.Data, Integer[].class));
    }
}
