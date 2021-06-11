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
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;


public class WallServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, this::getWall);
    }






    private void getWall(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, String[]> args = req.getParameterMap();

        Integer user_id = new Integer(args.get("user_id")[0]);


        WallRequest request = new WallRequest(
                Arrays.asList(user_id)
                , Instant.parse(args.get("after")[0])
                , Instant.parse(args.get("before")[0])
                , new Integer(args.get("count")[0])
                , new Integer(args.get("offset")[0])
        );
        Collection<Post> posts = PostServiceAPI.getWall(request);

        Commons.sendOk(posts, resp);
    }


}