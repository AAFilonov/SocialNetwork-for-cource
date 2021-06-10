package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.User;
import com.github.michael_sharko.utils.StaticGson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

public abstract class UserService {
    protected User user = null;

    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    protected UserService() {
    }

    public UserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String reqStr = IOUtils.toString(req.getInputStream());
        if (StringUtils.isBlank(reqStr)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(StaticGson.gson.toJson(new Answer("The request cannot be empty!", null)));

            throw new InvalidParameterException("The request cannot be empty!");
        }

        user = StaticGson.gson.fromJson(reqStr, User.class);
        this.req = req;
        this.resp = resp;
    }

    public abstract void execute() throws IOException;

    protected void sendMessage(int statusCode, Answer answer) throws IOException {
        resp.setStatus(statusCode);
        resp.getWriter().write(StaticGson.gson.toJson(answer));
    }
}
