package org.practical3.handlers;

import org.practical3.api.PostServiceAPI;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.Commons;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DoLikeServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, (req1, resp1)->{
            Integer post_id = new Integer( req1.getParameterMap().get("post_id")[0]);
            Answer answer =  PostServiceAPI.dolike(post_id);
            HttpClientManager.sendOk(answer, resp1);
        });
    }




}