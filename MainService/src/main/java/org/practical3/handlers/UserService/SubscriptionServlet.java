package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SubscriptionServlet extends HttpServlet {

    //саня вкручивает костыль:  получение подписок гетом, потом адаптируешб
    //?user=логин_или_id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            String user = req.getParameterMap().get("user")[0];
            Collection<Integer> userids = new ArrayList<>();

            userids = UserServiceAPI.getSubscriptions(user);

            HttpClientManager.sendOk(new Answer("OK", userids), resp);
        }));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            resp.setContentType("application/json");

            String reqStr = IOUtils.toString(req.getInputStream());
            SubscriptionRequest[] subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest[].class);

            int affectedRows = UserServiceAPI.subscribe(subscriptionRequest);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            resp.setContentType("application/json");

            String reqStr = IOUtils.toString(req.getInputStream());
            SubscriptionRequest[] subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest[].class);


            int affectedRows = UserServiceAPI.unsubscribe(subscriptionRequest);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));
    }
}