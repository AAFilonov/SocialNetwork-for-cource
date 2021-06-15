package org.practical3.api;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.ServiceException;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UserServiceAPI {
    private static final String url = PropertyManager.getPropertyAsString("service.users.addr", "http://localhost:8080");
    private static Answer answer = new Answer();

    public static String getStatus() {
        return answer.Status;
    }

    /* UserService */
    public static int register(User[] users) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendPost(url + "/users", users);

        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static int update(User[] users) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendPut(url + "/users", users);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static Collection<User> getUsers(String user_ids) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendGet(url + "/users", "?user_ids=" + user_ids);
        answer = ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);

        User[] users = StaticGson.fromJson((String) answer.Data, User[].class);
        return new ArrayList<User>(Arrays.asList(users));
    }

    public static int delete(String user_ids) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendDelete(url + "/users", user_ids);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    /* SubscriptionService */
    public static int subscribe(SubscriptionRequest[] subscriptions) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendPost(url + "/subscriptions", subscriptions);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static int unsubscribe(SubscriptionRequest[] subscriptions) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendDelete(url + "/subscriptions", subscriptions);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static int follow(SubscriptionRequest[] subscriptions) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendPost("/followers", subscriptions);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static int unfollow(SubscriptionRequest[] subscriptions) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendDelete(url + "/followers", subscriptions);
        answer =  ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        return answer.AffectedRows;
    }

    public static Collection<Integer> getSubscriptions(String user_ids) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendGet(url + "/subscriptions", "?user_ids=" + user_ids);
        answer = ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        Collection<Integer> ids = new ArrayList<>( Arrays.asList(StaticGson.fromJson((String) answer.Data, Integer[].class)));
        return ids;
    }

    public static Collection<Integer> getFollowers(String user_ids) throws IOException, ServiceException {
        HttpResponse response = HttpClientManager.sendGet(url + "/followers", "?user_ids=" + user_ids);

        answer = ExceptionHandler.checkResponse(response, ResponseReader::getAnswer);
        Collection<Integer> ids = new ArrayList<>( Arrays.asList(StaticGson.fromJson((String) answer.Data, Integer[].class)));
        return ids;
    }

    //саня: обертка вокруг getUsers для получения только id
    public static ArrayList<Integer> getUsersIds(String username) throws IOException, ClassNotFoundException, ServiceException {
        Collection<User> users = getUsers (username);
        if(users.isEmpty()) throw new ClassNotFoundException();
        ArrayList<Integer> user_ids = new ArrayList<>();
        for (User user:users) {
            user_ids.add(user.userid);
        }
        return user_ids;
    }



}
