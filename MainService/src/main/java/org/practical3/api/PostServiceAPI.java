package org.practical3.api;


import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.ServiceException;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.http.ResponseReader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static org.practical3.utils.http.HttpClientManager.*;

public class PostServiceAPI {

    static String getBaseURL() {
        return PropertyManager.getPropertyAsString("service.posts.addr", "http://localhost:8027");
    }





    public static ArrayList<Post> getPosts(PostsRequest postsRequest) throws Exception {
        String url = String.format("%s/posts/get", getBaseURL());
        HttpResponse response = sendPost(url, postsRequest);
        return (ArrayList<Post>)checkResponse(response, ResponseReader::getPostsCollection);
    }

    public static Collection<Post> getWall(WallRequest wallRequest) throws Exception {

        String url = String.format("%s/posts/wall", getBaseURL());
        HttpResponse response = sendPost(url, wallRequest);
        return checkResponse(response, ResponseReader::getPostsCollection);
    }

    public static Answer insertPosts(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response = sendPost(url, posts);
        return checkResponse(response, ResponseReader::getAnswer);
    }

    public static Answer deletePosts(Collection<Integer> post_ids) throws Exception {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response = sendDelete(url, post_ids);
        return checkResponse(response, ResponseReader::getAnswer );
    }

    public static Answer updatePosts(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response = sendPut(url, posts);
        return checkResponse(response, ResponseReader::getAnswer);
    }

    public static Answer removePosts(Collection<Integer> post_ids) throws Exception {
        ArrayList<Post> posts = new ArrayList<Post>();

        for (int id : post_ids) {
            Post post = new Post();
            post.PostId = id;
            post.IsRemoved = true;
            posts.add(post);
        }

        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response = sendPut(url, posts);
        return checkResponse(response, ResponseReader::getAnswer );
    }

    public static Answer restorePosts(Collection<Integer> post_ids) throws Exception {
        ArrayList<Post> posts = new ArrayList<Post>();

        for (int id : post_ids) {
            Post post = new Post();
            post.PostId = id;
            post.IsRemoved = false;
            posts.add(post);
        }

        String url = String.format("%s/posts", getBaseURL());
        HttpResponse response = sendPut(url, posts);
        return checkResponse(response, ResponseReader::getAnswer);
    }

    public static Object doRepost(Integer post_id, Integer user_id) throws Exception {
        String url = String.format("%s/posts/repost&post_id=%s&user_id=%s",
                getBaseURL(), post_id.toString(), user_id.toString());
        HttpResponse response = sendPost(url, null);
        return  checkResponse(response, ResponseReader::getPostsCollection);
    }

    public static Answer dolike(Integer post_id) throws Exception {
        String url = String.format("%s/posts/like?post_id=%s",
                getBaseURL(), post_id.toString());
        HttpResponse response = sendPost(url, null);
        return checkResponse(response, (response1)-> {return new Answer("OK", null);});
    }

    public static Collection<Post> searchPosts(SearchPostRequest request) throws Exception {
        String url = String.format("%s/posts/search",
                getBaseURL());
        HttpResponse response = sendPost(url, request);
        return checkResponse(response, ResponseReader::getPostsCollection);


    }

    public static   <T> T checkResponse(HttpResponse response, Function<HttpResponse,T> returnIfOk) throws IOException, ServiceException {

        if( response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK)
            return returnIfOk.apply(response);
        else {
            pullExceptionByResponseCode(response);
            return null;
        }
    }

    private static void pullExceptionByResponseCode(HttpResponse response) throws IOException, ServiceException {
        Answer postServiceAnswer = ResponseReader.getAnswer(response);
        System.out.println("[POST SERVICE ERROR]: "+ postServiceAnswer.Status);

        throw  new ServiceException(postServiceAnswer,
                response.getStatusLine().getStatusCode());
    }
}
