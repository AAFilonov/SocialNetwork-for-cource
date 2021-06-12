package org.practical3.api;

import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.http.ResponseReader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

public class MainServiceAPI {

    static String getBaseURL(){
        return "http://localhost:" + PropertyManager.getPropertyAsString("server.port", "8026");
    }
    static boolean isSuccessful(HttpResponse response) throws Exception {
        switch (response.getStatusLine().getStatusCode()) {
            case HttpServletResponse.SC_OK:
            default:
                System.out.println("[POST SERVICE ERROR]: "
                        + ResponseReader.getResponseBody(response).Status);
                return false;
        }
    }


    public static Collection<User> getUsers(String user_ids) throws IOException {
        String url = String.format("%s/users", getBaseURL());
        String params= "?user_ids="+user_ids;
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


    public static Collection<Post> getPosts(String post_ids, Integer count, Integer offset) throws IOException {
        String url = String.format("%s/posts", getBaseURL());
        String params= String.format("?post_ids=%s&count=%s&offset=%s",post_ids,count.toString(),offset.toString());
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<Post> posts = ResponseReader.getPostsCollection(response);
        return posts;
    }

    public static int insertPosts(Collection<Post> posts) throws IOException {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response =  HttpClientManager.sendPost(url, posts);
        Answer answer =  ResponseReader.getResponseBody(response);
        return (answer!=null)?  answer.AffectedRows: 0;
    }

    public static int deletePosts(Collection<Integer> ids) throws IOException {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response =  HttpClientManager.sendDelete(url, ids);
        Answer answer =  ResponseReader.getResponseBody(response);
        return (answer!=null)?  answer.AffectedRows: 0;
    }


    public static int updatePosts(Collection<Post> posts) throws IOException {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response =  HttpClientManager.sendPut(url, posts);
        Answer answer =  ResponseReader.getResponseBody(response);
        return (answer!=null)?  answer.AffectedRows: 0;
    }

    public static Collection<Post> getFeed(String user_login, Instant before, Instant after, Integer count, Integer offset ) throws IOException {
        String url = String.format("%s/feed", getBaseURL());
        String params= String.format(
                "?user_login=%s&" +
                "before=%s&" +
                "after=%s&" +
                "count=%s&" +
                "offset=%s"
                ,user_login, before.toString(), after.toString(),  count.toString(),offset.toString());
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<Post> posts = ResponseReader.getPostsCollection(response);
        return posts;
    }


}



