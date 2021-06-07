package org.practical3.handlers;

import org.practical3.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.Commons;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import static org.practical3.UserServiceAPI.getSubscriptions;


public class WallServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.processAndReply(req, resp, this::getWall);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.processAndReply(req, resp, this::insertPosts);
    }




    private void getWall(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, String[]> args = req.getParameterMap();

        String username = args.get("user")[0];



        Collection<Integer> subscriptions  =  getSubscriptions(username);

        WallRequest request = new WallRequest(
                subscriptions
                , Instant.parse(args.get("after")[0])
                , Instant.parse(args.get("before")[0])
                , new Integer(args.get("count")[0])
                , new Integer(args.get("offset")[0])
        );
        Collection<Post> posts = PostServiceAPI.getWall(request);

        Commons.sendOk(posts, resp);
    }
    private void insertPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Collection<Post> posts =  Commons.getCollectionFromRequest(req);
        PostServiceAPI. insertPosts(posts);
    }

}