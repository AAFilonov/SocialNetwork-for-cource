package org.practical3.handlers.UserService;

import com.google.common.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.StaticGson;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.RequestReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static org.practical3.utils.http.RequestReader.getArgAsString;

public class SubscriptionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            String user = getArgAsString(req.getParameterMap(), "user");
            Collection<Integer> userids = UserServiceAPI.getSubscriptions(user);

            if (userids.isEmpty())
                throw new ClassNotFoundException();

            HttpClientManager.sendOk(userids, resp);
        }));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            SubscriptionRequest[] subscriptionRequest =  RequestReader.getBodyAsObject(req,SubscriptionRequest[].class );
            int affectedRows = UserServiceAPI.subscribe(subscriptionRequest);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, ((req1, resp1) -> {
            SubscriptionRequest[] subscriptionRequest =  RequestReader.getBodyAsObject(req,SubscriptionRequest[].class );
            int affectedRows = UserServiceAPI.unsubscribe(subscriptionRequest);
            HttpClientManager.sendOk(new Answer("OK", null, affectedRows), resp);
        }));
    }
}