package org.practical3.handlers.PostService;

import org.practical3.api.PostServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.ExceptionHandler;
import org.practical3.utils.http.HttpClientManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.practical3.utils.http.RequestReader.getArgAsInt;
import static org.practical3.utils.http.RequestReader.getArgAsString;

public class RemoveServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExceptionHandler.execute(req, resp, (req1, resp1) -> {
            String post_ids = getArgAsString(req.getParameterMap(), "post_ids");
            Collection<Integer> ids = Commons.parseIds(post_ids);
            Answer a = PostServiceAPI.removePosts(ids);

            HttpClientManager.sendOk(a, resp1);
        });
    }

}
