package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.StaticGson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
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


            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(StaticGson.toJson(userids));
        }));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        SubscriptionRequest[] subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest[].class);

        UserServiceAPI.subscribe( subscriptionRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        SubscriptionRequest[] subscriptionRequest = StaticGson.gson.fromJson(reqStr, SubscriptionRequest[].class);

        UserServiceAPI.unsubscribe(subscriptionRequest);
    }
}