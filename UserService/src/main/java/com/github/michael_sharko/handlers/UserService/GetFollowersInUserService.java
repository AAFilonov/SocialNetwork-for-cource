package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.UserRequest;
import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.StaticGson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GetFollowersInUserService extends UserService {
    UserRequest userRequest;

    public GetFollowersInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String reqStr = IOUtils.toString(req.getInputStream());
        if (StringUtils.isBlank(reqStr)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(StaticGson.gson.toJson(new Answer("The request cannot be empty!", null)));

            throw new InvalidParameterException("The request cannot be empty!");
        }

        userRequest = StaticGson.gson.fromJson(reqStr, UserRequest.class);
        this.req = req;
        this.resp = resp;
    }

    String generateQuery() {
        return "SELECT unnest((SELECT followers FROM users WHERE " +
                (userRequest.userid != null ? ("userid = " + userRequest.userid) :
                        (userRequest.username != null ? "username = '" + userRequest.username + "'" : "") +
                                ")) AS userid");
    }

    @Override
    public void execute() throws IOException {
        ArrayList<Integer> userids = DatabaseManager.executeQueryToArrayList(generateQuery(), new Integer(0), "userid");
        sendMessage(HttpServletResponse.SC_OK, new Answer("Success", userids));
    }
}