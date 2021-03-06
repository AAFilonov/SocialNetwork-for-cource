package org.practical3.utils.testing;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.PostsRequest;
import org.practical3.model.transfer.SearchPostRequest;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.ResponseReader;
import org.practical3.utils.testing.HttpClientManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class InnerAPI {

    static String getBaseURL() {
        return PropertyManager.getPropertyAsString("service.posts.addr", "http://localhost:8027");
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

        HttpResponse response = HttpClientManager.sendPost(url, postsRequest);

        Collection<Post> posts = HttpClientManager.getPostsCollection(response);
        return  posts;
    }

    public static Collection<Post> getWall(WallRequest wallRequest) throws Exception {
        String url = String.format("%s/posts?action=getWall", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, wallRequest);
        Collection<Post> posts = HttpClientManager.getPostsCollection(response);
        return  posts;
    }

    public static int insertPosts(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts?action=insertPosts", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, posts);
        Answer postServiceAnswer  =processResponce(response);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int deletePosts(Collection<Integer> post_ids) throws Exception {
        String url = String.format("%s/posts?action=deletePosts", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, post_ids);
        Answer postServiceAnswer  =processResponce(response);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int updatePost(Collection<Post> posts) throws Exception {
        String url = String.format("%s/posts?action=updatePosts", getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url, posts);
        Answer postServiceAnswer  =processResponce(response);
        return (postServiceAnswer != null) ? postServiceAnswer.AffectedRows : 0;
    }

    public static int removePosts(Collection<Integer> post_ids) throws Exception {
        throw new NotImplementedException();
    }

    public static int restorePosts(Collection<Integer> post_ids) throws Exception {
        throw new NotImplementedException();
    }


    public static Collection<Post> searchPosts(SearchPostRequest request) throws Exception {
        String url = String.format("%s/posts?action=searchPosts",getBaseURL());
        HttpResponse response = HttpClientManager.sendPost(url,request);
        Collection<Post> posts =null;
        if(response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK)
            posts = ResponseReader.getPostsCollection(response);
        return posts;
    }

    public static boolean doRepost(Integer post_id, Integer user_id)throws Exception {


        String url = String.format("%s/posts?action=doRepost&post_id=%s&user_id=%s",
                getBaseURL(), post_id.toString(), user_id.toString());
        HttpResponse response = HttpClientManager.sendPost(url,null);
        return  response.getStatusLine().getStatusCode() ==HttpServletResponse.SC_OK;
    }
    public static boolean dolike(Integer post_id)throws Exception {
        String url = String.format("%s/posts?action=doLike&post_id=%s",
                getBaseURL(), post_id.toString());
        HttpResponse response = HttpClientManager.sendPost(url,null);
        return  response.getStatusLine().getStatusCode() ==HttpServletResponse.SC_OK;
    }


}
