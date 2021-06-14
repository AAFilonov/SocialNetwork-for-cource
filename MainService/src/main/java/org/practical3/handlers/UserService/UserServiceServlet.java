package org.practical3.handlers.UserService;

import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.UserRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Collection;


public class UserServiceServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[INFO]: Get request "+request.getMethod() +request.getRequestURI());
        super.service(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Commons.executeAndCatchExceptions(req,resp,((req1, resp1) -> {
            resp1.setContentType("application/json");

            String reqStr = IOUtils.toString(req1.getInputStream());
            User user = StaticGson.fromJson(reqStr, User.class);

            UserServiceAPI.register(user);
            HttpClientManager.sendOk(null, resp1);

        }));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Commons.executeAndCatchExceptions(req,resp,((req1, resp1) -> {
            resp.setContentType("application/json");
            Collection<User> users = UserServiceAPI.getUsers(req1.getParameter("user_ids"));

            resp1.setStatus(HttpServletResponse.SC_OK);
            resp1.getWriter().write(StaticGson.toJson(users));
        }));

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        User user = StaticGson.fromJson(reqStr, User.class);

        UserServiceAPI.update(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        UserRequest userRequest = StaticGson.gson.fromJson(reqStr, UserRequest.class);

        if (userRequest.userid != null)
            UserServiceAPI.delete(userRequest.userid);
        else if (userRequest.username != null)
            UserServiceAPI.delete(userRequest.username);
        else
            throw new InvalidParameterException("Set userid or username");
    }
}