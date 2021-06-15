package org.practical3.api;

import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.http.ResponseReader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

public class MainServiceAPI {

    static String getBaseURL() {
        return "http://localhost:" + PropertyManager.getPropertyAsString("server.port", "8026");
    }


    public static Collection<User> getUsers(String user_ids) throws IOException {
        String url = String.format("%s/users", getBaseURL());
        String params = "?user_ids=" + user_ids;
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<User> users = ResponseReader.getUsersCollection(response);
        return users;
    }


    public static int registerUser(User user) throws IOException {
        String url = String.format("%s/users", getBaseURL());

        HttpResponse response = HttpClientManager.sendPost(url, user);
        //нужно чтоб вернул что нибудь для проверки
        return 0;
    }


    public static int deleteUser(String username) throws IOException {
        String url = String.format("%s/users", getBaseURL());

        HttpResponse response = HttpClientManager.sendDelete(url, username);
        //нужно чтоб вернул что нибудь для проверки
        return 0;
    }

    public static Collection<Integer> getSubscriptions(String user) throws IOException {
        String url = String.format("%s/getsubscriptions", getBaseURL());
        String params = String.format("?user=%s", user);

        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<Integer> ids = ResponseReader.getIntegerCollection(response);
        return ids;

    }


    public static Collection<Post> getPosts(String post_ids, Integer count, Integer offset) throws IOException {
        String url = String.format("%s/posts/", getBaseURL());
        String params = String.format("?post_ids=%s&count=%s&offset=%s", post_ids, count.toString(), offset.toString());
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<Post> posts = ResponseReader.getPostsCollection(response);
        return posts;
    }

    public static int insertPosts(Collection<Post> posts) throws IOException {
        String url = String.format("%s/posts/", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, posts);
        Answer answer = ResponseReader.getAnswer(response);
        return (answer != null) ? answer.AffectedRows : 0;
    }

    public static int deletePosts(Collection<Integer> ids) throws IOException {
        String url = String.format("%s/posts/", getBaseURL());
        HttpResponse response = HttpClientManager.sendDelete(url, ids);
        Answer answer = ResponseReader.getAnswer(response);
        return (answer != null) ? answer.AffectedRows : 0;
    }


    public static int updatePosts(Collection<Post> posts) throws IOException {
        String url = String.format("%s/posts/", getBaseURL());
        HttpResponse response = HttpClientManager.sendPut(url, posts);
        Answer answer = ResponseReader.getAnswer(response);
        return (answer != null) ? answer.AffectedRows : 0;
    }



    public static boolean subscribeUser(SubscriptionRequest request) throws IOException {
        String url = String.format("%s/subscriptions", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, request);
        return response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK;

    }

    public static boolean unsubscribeUser(SubscriptionRequest request) throws IOException {
        String url = String.format("%s/subscriptions", getBaseURL());
        HttpResponse response = HttpClientManager.sendDelete(url, request);
        return response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK;
    }


    public static User getOwner(Integer post_id) throws IOException {
        String url = String.format("%s/posts/get_owner", getBaseURL());
        String params = String.format("?post_id=%s", post_id.toString());
        HttpResponse response = HttpClientManager.sendGet(url, params);
        if (response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK)
            return  ResponseReader.getBodyAsObject(response, User.class);
        else return null;
    }

    public static boolean doLike(Integer post_id) throws IOException {
        String url = String.format("%s/posts/like", getBaseURL());
        String params = String.format("?post_id=%s", post_id.toString());
        HttpResponse response = HttpClientManager.sendPost(url + params, null);
        return response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK;
    }

    public static HttpResponse doRepost(String username, Integer post_id) throws IOException {
        String url = String.format("%s/posts/repost", getBaseURL());
        String params = String.format("?post_id=%s&username=%s", post_id.toString(), username);

        return HttpClientManager.sendPost(url + params, null);
    }

    public static Object searchPost(SearchPostRequest request) throws IOException {
        String url = String.format("%s/posts/search", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, request);
        if (response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK)
            return ResponseReader.getPostsCollection(response);
        else
            return ResponseReader.getAnswer(response);
    }
}




