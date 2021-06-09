package com.github.michael_sharko.handlers.UserService.SubscriptionsUserService;

import com.github.michael_sharko.handlers.UserService.UserService;
import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.Subscription;
import com.github.michael_sharko.utils.StaticGson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

public class SubscriptionsUserService extends UserService {
    Subscription subscription;

    SubscriptionsUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String reqStr = IOUtils.toString(req.getInputStream());
        if (StringUtils.isBlank(reqStr)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(StaticGson.gson.toJson(new Answer("The request cannot be empty!", null)));

            throw new InvalidParameterException("The request cannot be empty!");
        }

        subscription = StaticGson.gson.fromJson(reqStr, Subscription.class);
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void execute() throws IOException {

    }
}
