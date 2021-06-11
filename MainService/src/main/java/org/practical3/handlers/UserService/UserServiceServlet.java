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

@WebServlet("/users")
public class UserServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String reqStr = IOUtils.toString(req.getInputStream());
        User user = StaticGson.fromJson(reqStr, User.class);

        UserServiceAPI.register(user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Collection<User> users = UserServiceAPI.getUsers(req.getParameter("user_ids"));

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(StaticGson.toJson(users));
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