package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.StaticGson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

public class GetSubscriptionsUserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        UserRequest userRequest = StaticGson.gson.fromJson(reqStr, UserRequest.class);

        Collection<Integer> userids = null;

        if (userRequest.userid != null)
            userids = UserServiceAPI.getSubscriptions(userRequest.userid);
        else if (userRequest.username != null)
            userids = UserServiceAPI.getSubscriptions(userRequest.username);
        else
            throw new InvalidParameterException("Set userid or username");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(StaticGson.toJson(userids));
    }

    //саня вкручивает костыль:  получение подписок гетом, потом адаптируешб
    //?user=логин_или_id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Commons.executeAndCatchExceptions(req,resp,((req1, resp1) -> {
            String user =req.getParameterMap().get("user")[0];
            Collection<Integer> userids = new ArrayList<>();
            try {
                Integer id = new Integer(user);
                userids =    UserServiceAPI.getSubscriptions(id);

            } catch (NumberFormatException e) {
                userids = UserServiceAPI.getSubscriptions(user);
            }
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(StaticGson.toJson(userids));
        }));
    }




}