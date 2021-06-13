package org.practical3.handlers;

import org.practical3.api.PostServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;


public class DoRepostServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, (req1,resp1)->{
            Integer post_id = new Integer( req1.getParameterMap().get("post_id")[0]);
            String username = req1.getParameterMap().get("username")[0];
            Integer user_id = UserServiceAPI.getUsersIds(username).get(0);
            Object result = PostServiceAPI.doRepost(post_id,user_id);
            HttpClientManager.sendOk(result, resp1);
        });
    }



}