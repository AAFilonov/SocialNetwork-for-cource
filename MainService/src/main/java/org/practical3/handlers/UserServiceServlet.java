package org.practical3.handlers;

import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


public class UserServiceServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[INFO]: Get request " + request.getMethod() + request.getRequestURI());
        super.service(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            User user = RequestReader.getBodyAsObject(req1, User.class);
            User[] arrayArg = new User[]{user};
            UserServiceAPI.register(arrayArg);
            HttpClientManager.sendOk(null, resp1);

        }));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            String user_ids = RequestReader.getArgAsString(req1.getParameterMap(), "user_ids");
            Collection<User> users = UserServiceAPI.getUsers(user_ids);

            resp1.setStatus(HttpServletResponse.SC_OK);
            resp1.getWriter().write(StaticGson.toJson(users));
        }));

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            User user = RequestReader.getBodyAsObject(req1, User.class);
            User[] arrayArg = new User[]{user};
            int affectedRows = UserServiceAPI.update(arrayArg);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            String reqStr = IOUtils.toString(req.getInputStream());

            int affectedRows = UserServiceAPI.delete(reqStr);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));

    }
}