package org.practical3;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.Subscription;
import com.github.michael_sharko.models.User;
import com.github.michael_sharko.utils.PropertyManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

public class UserServiceAPI {
    private static class HttpManager {
        static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
            public static final String METHOD_NAME = "DELETE";

            public HttpDeleteWithBody(final String uri) {
                super();
                setURI(URI.create(uri));
            }

            public HttpDeleteWithBody(final URI uri) {
                super();
                setURI(uri);
            }

            public HttpDeleteWithBody() {
                super();
            }

            public String getMethod() {
                return METHOD_NAME;
            }
        }

        static GsonBuilder builder = new GsonBuilder();
        static Gson gson = builder.create();

        static HttpClient client = HttpClientBuilder.create().build();
        static URI uri = PropertyManager.getURI();

        static HttpResponse sendPost(String path, Object data) throws IOException {
            HttpPost request = new HttpPost(uri.getHost() + path);
            StringEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        static HttpResponse sendPut(String path, Object data) throws IOException {
            HttpPut request = new HttpPut(uri.getHost() + path);
            StringEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        static HttpResponse sendGet(String path, String parameters) throws IOException {
            HttpGet request = new HttpGet(uri.getHost() + path + parameters);
            return client.execute(request);
        }

        static HttpResponse sendDelete(String path, Object data) throws IOException {
            HttpDeleteWithBody request = new HttpDeleteWithBody(uri.getHost() + path);
            HttpEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        public static Answer readResponse(HttpResponse response) throws IOException {
            HttpEntity resp  = response.getEntity();
            String respStr = EntityUtils.toString(resp);
            return HttpManager.gson.fromJson(respStr, Answer.class);
        }
    }

    public static void register(Collection<User> users) throws IOException {
        HttpManager.sendPost("users", users);
    }

    public static void update(Collection<User> users) throws IOException {
        HttpManager.sendPut("users", users);
    }

    public static Collection<User> getUsers(String parametres) throws IOException {
        HttpResponse response = HttpManager.sendGet("users", parametres);
        return (Collection<User>) HttpManager.readResponse(response).getData();
    }

    public static void delete(Collection<User> users) throws IOException {
        HttpManager.sendDelete("users", users);
    }

    public static Collection<Subscription> getSubscriptions(String username) throws IOException {
        HttpResponse response = HttpManager.sendPost("getsubscriptions", username);
        return (Collection<Subscription>) HttpManager.readResponse(response).getData();
    }

    public static Collection<Subscription> getFollowers(String username) throws IOException {
        HttpResponse response = HttpManager.sendPost("getfollowers", username);
        return (Collection<Subscription>) HttpManager.readResponse(response).getData();
=======
import org.apache.http.HttpResponse;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.HttpManagerForUserService;

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
>>>>>>> Stashed changes
    }
}
