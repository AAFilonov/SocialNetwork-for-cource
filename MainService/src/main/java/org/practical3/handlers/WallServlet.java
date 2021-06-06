package org.practical3.handlers;

import com.google.common.reflect.TypeToken;
import org.practical3.PostServiceAPI;
import org.practical3.model.data.Post;

import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.Commons;
import org.apache.commons.io.IOUtils;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


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

        String UserName = args.get("user")[0];
        //получить по юзернейму id

        //получить по id подписки
        //получить по id подписок посты

        WallRequest request = new WallRequest(
                args.get("post_ids")[0]
                , Instant.parse(args.get("after")[0])
                , Instant.parse(args.get("before")[0])
                , new Integer(args.get("count")[0])
                , new Integer(args.get("offset")[0])
        );
        Collection<Post> posts = PostServiceAPI.getWall(request);

        Commons.sendOk(posts, resp);
    }
    private void insertPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Collection<Post> posts =  Commons.getCollectionFromRequest(req,Post.class);
        PostServiceAPI. insertPosts(posts);
    }

}