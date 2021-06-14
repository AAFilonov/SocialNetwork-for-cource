package org.practical3.api;


import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.ServiceException;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.http.ResponseReader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class PostServiceAPI {

    static String getBaseURL() {
        return PropertyManager.getPropertyAsString("service.posts.addr", "http://localhost:8027");
    }

    private static Answer sendRequest(String url, Object request) throws Exception {
        HttpResponse response = HttpClientManager.sendPost(url, request);
        return isSuccessful(response) ? ResponseReader.getAnswer(response) : null;
    }

    static boolean isSuccessful(HttpResponse response) throws Exception {
        switch (response.getStatusLine().getStatusCode()) {
            case HttpServletResponse.SC_OK:
                return true;

            case HttpServletResponse.SC_NOT_FOUND:
                return false;
            case HttpServletResponse.SC_NOT_IMPLEMENTED:
            case HttpServletResponse.SC_BAD_REQUEST:
            default:
                System.out.println("[POST SERVICE ERROR]: "
                        + ResponseReader.getAnswer(response).Status);
                throw new Exception();
        }
    }


    public static ArrayList<Post> getPosts(PostsRequest postsRequest) throws Exception {
        String url = String.format("%s/posts?action=getPosts", getBaseURL());

        HttpResponse response = HttpClientManager.sendPost(url, postsRequest);
        return (ArrayList<Post>)checkResponse(response, ResponseReader::getPostsCollection);
    }

    public static Collection<Post> getWall(WallRequest wallRequest) throws Exception {

        String url = String.format("%s/posts?action=getWall", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, wallRequest);
        return checkResponse(response, ResponseReader::getPostsCollection);
    }

    public static int insertPosts(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts?action=insertPosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, posts);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int deletePosts(Collection<Integer> post_ids) throws Exception {
        String url = String.format("%s/posts?action=deletePosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, post_ids);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int updatePosts(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts?action=updatePosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, posts);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int removePosts(Collection<Integer> post_ids) throws Exception {
        ArrayList<Post> postsToUpdateRemovedField = new ArrayList<Post>();

        for (int id : post_ids) {
            Post post = new Post();
            post.PostId = id;
            post.IsRemoved = true;
            postsToUpdateRemovedField.add(post);
        }

        String url = String.format("%s/posts?action=updatePosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, postsToUpdateRemovedField);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int restorePosts(Collection<Integer> post_ids) throws Exception {
        ArrayList<Post> postsToUpdateRemovedField = new ArrayList<Post>();

        for (int id : post_ids) {
            Post post = new Post();
            post.PostId = id;
            post.IsRemoved = false;
            postsToUpdateRemovedField.add(post);
        }

        String url = String.format("%s/posts?action=updatePosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, postsToUpdateRemovedField);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static Object doRepost(Integer post_id, Integer user_id) throws Exception {
        String url = String.format("%s/posts?action=doRepost&post_id=%s&user_id=%s",
                getBaseURL(), post_id.toString(), user_id.toString());
        HttpResponse response = HttpClientManager.sendPost(url, null);
        return  checkResponse(response, (response1)-> {
            return ResponseReader.getPostsCollection(response1);
        });
    }

    public static Answer dolike(Integer post_id) throws Exception {
        String url = String.format("%s/posts?action=doLike&post_id=%s",
                getBaseURL(), post_id.toString());
        HttpResponse response = HttpClientManager.sendPost(url, null);
        return checkResponse(response, (response1)-> {return new Answer("OK", null);});
    }

    public static Collection<Post> searchPosts(SearchPostRequest request) throws Exception {
        String url = String.format("%s/posts?action=searchPosts",
                getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, request);
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
