package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.StaticGson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Collection;

public class GetFollowersUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        UserRequest userRequest = StaticGson.gson.fromJson(reqStr, UserRequest.class);

        Collection<Integer> userids = null;

        if (userRequest.userid != null)
            userids = UserServiceAPI.getFollowers(userRequest.userid);
        else if (userRequest.username != null)
            userids = UserServiceAPI.getFollowers(userRequest.username);
        else
            throw new InvalidParameterException("Set userid or username");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(StaticGson.toJson(userids));
    }
}