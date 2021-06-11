package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.UserServiceAPI;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.StaticGson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@WebServlet("/subscriptions")
public class SubscriptionsUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        SubscriptionRequest subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest.class);

        UserServiceAPI.subscribe(subscriptionRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        SubscriptionRequest subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest.class);

        UserServiceAPI.unsubscribe(subscriptionRequest);
    }
}