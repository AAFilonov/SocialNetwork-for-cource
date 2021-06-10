package org.practical3.utils;

import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.PostsRequest;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.testing.HttpClientManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

public class InnerAPI {

    static String getBaseURL() {
        return PropertyManager.getPropertyAsString("service.posts.addr", "http://localhost:8027");
    }

    private static Answer sendRequest(String url, Object request) throws Exception {
        HttpResponse response = HttpClientManager.sendPost(url, request);
        return processResponce(response);
    }

    static Answer processResponce(HttpResponse response) throws Exception {
        int code = response.getStatusLine().getStatusCode();
        switch (code) {
            case HttpServletResponse.SC_OK:
                return HttpClientManager.getResponseBody(response);

            case HttpServletResponse.SC_NOT_FOUND:
                return null;
            case HttpServletResponse.SC_NOT_IMPLEMENTED:
            case HttpServletResponse.SC_BAD_REQUEST:
            default:
                System.out.println("[POST SERVICE ERROR]: " + HttpClientManager.getResponseBody(response).Status);
                return  null;


        }
    }


    public static Collection<Post> getPosts(PostsRequest postsRequest) throws Exception {
        String url = String.format("%s/posts?action=getPosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, postsRequest);
        return (postServiceAnswer != null) ? (Collection<Post>) postServiceAnswer.Data : new ArrayList<Post>();
    }

    public static Collection<Post> getWall(WallRequest wallRequest) throws Exception {
        String url = String.format("%s/posts?action=getWall", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, wallRequest);
        return (postServiceAnswer != null) ? (Collection<Post>) postServiceAnswer.Data : new ArrayList<Post>();
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

    public static int updatePost(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts?action=updatePosts", getBaseURL());
        Answer postServiceAnswer = sendRequest(url, posts);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int removePosts(Collection<Integer> post_ids) throws Exception {
        throw new NotImplementedException();
    }

    public static int restorePosts(Collection<Integer> post_ids) throws Exception {
        throw new NotImplementedException();
    }


    public static int searchPosts() throws Exception {
        throw new NotImplementedException();
    }


}
