package org.practical3.handlers;


import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.RequestReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.practical3.utils.http.RequestReader.getArgAsInstant;
import static org.practical3.utils.http.RequestReader.getArgAsInt;


public class WallServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, this::getWall);
    }


    private void getWall(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, String[]> args = req.getParameterMap();

        Integer user_id = RequestReader.getArgAsInt(args, "user_id");


        WallRequest request = new WallRequest(
                Arrays.asList(user_id)
                , getArgAsInstant(args,"after")
                , getArgAsInstant(args,"before")
                , getArgAsInt(args,"count")
                , getArgAsInt(args,"offset")

        );
        Collection<Post> posts = PostServiceAPI.getWall(request);

        HttpClientManager.sendOk(posts, resp);
    }


}