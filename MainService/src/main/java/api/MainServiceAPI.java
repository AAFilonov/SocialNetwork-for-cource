package api;

import org.apache.http.HttpResponse;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.HttpClientManager;
import org.practical3.utils.PropertyManager;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
                        + HttpClientManager.getResponseBody(response).Status);
                return false;
        }
    }


    public static Collection<User> getUsers(String user_ids) throws IOException {
        String url = String.format("%s/users", getBaseURL());
        String params= "?user_ids="+user_ids;
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<User> users = HttpClientManager.getUsersCollection(response);
        return users;
    }

    public static Collection<Post> getPosts(String post_ids, Integer count, Integer offset) throws IOException {
        String url = String.format("%s/posts", getBaseURL());
        String params= String.format("?post_ids=%s&count=%s&offset=%s",post_ids,count.toString(),offset.toString());
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<Post> posts = HttpClientManager.getPostsCollection(response);
        return posts;
    }


}
