package org.practical3.handlers;

import org.practical3.api.PostServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class GetOwnerServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, (req1,resp1)->{
            Integer post_id = new Integer( req1.getParameterMap().get("post_id")[0]);
            Post post = PostServiceAPI.getPosts(new PostsRequest(post_id.toString(),1,0)).get(0);

            ArrayList< User> users = ( ArrayList< User>)UserServiceAPI.getUsers(post.OwnerId.toString());

            HttpClientManager.sendOk(users.get(0), resp1);
        });
    }



}