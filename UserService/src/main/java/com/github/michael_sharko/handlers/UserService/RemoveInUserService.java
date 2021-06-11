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

public class RemoveInUserService extends UserService {
    UserRequest userRequest;

    public RemoveInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    private static String generateQuery(UserRequest userRequest) {
        String query = "DELETE FROM users WHERE %s %s %s";
        query = String.format(query,
                userRequest.userid != null ? ("userid = " + userRequest.userid) : "",
                userRequest.userid != null && userRequest.username != null ? "AND" : "",
                userRequest.username != null ? ("userid = " + userRequest.username) : "");
        return query;
    }

    @Override
    public void execute() throws IOException {
        if (DatabaseManager.executeSimpleUpdate(generateQuery(userRequest)) > 0)
            sendMessage(HttpServletResponse.SC_OK, new Answer("Success: User was deleted successfully", null));
    }
}
